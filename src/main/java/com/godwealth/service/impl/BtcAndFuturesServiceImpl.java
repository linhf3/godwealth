package com.godwealth.service.impl;

import com.alibaba.fastjson.JSON;
import com.godwealth.dao.FuturesDataMapper;
import com.godwealth.entity.BtcEntity;
import com.godwealth.entity.FuturesData;
import com.godwealth.entity.ResultEntity;
import com.godwealth.service.BtcAndFuturesService;
import com.godwealth.utils.Constant;
import com.godwealth.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BtcAndFuturesServiceImpl implements BtcAndFuturesService {

    private LinkedList<FuturesData> linkedList = new LinkedList<>();
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private FuturesDataMapper futuresDataMapper;

    //yyyy-MM-dd HH:mm:ss 转换的时间格式  可以自定义
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat df= new DecimalFormat("#.00");
    public Map<String,Object> getBtc(){
        Map<String, Object> resultMap = new HashMap<>();
        Date date = new Date();
        String d = sdf.format(date);
        String rx = null;
        double ydPrcie = 0.00;
        try {
            rx = HttpUtils.doGet("https://www.yirongxinfuguanli.com/priapi/v5/market/candles?instId=BTC-USDT-SWAP&after=&before=&limit=1441&t=1685274443169", null);
            //log.info(rx);
            Map node = (Map) JSON.parse(rx);
            List<List<String>> list = (List<List<String>>) node.get("data");
            List<Double> priceLinkList = new LinkedList<Double>();
            List<Double> priceLinkListAll = new LinkedList<Double>();
            for (int i = 0; i < list.size(); i++) {
                List<String> strings = list.get(i);
                if (d.equals(sdf.format(new Date(Long.valueOf(strings.get(0)))))){
                    Double b = Double.valueOf(strings.get(4));
                    priceLinkList.add(b);
                    priceLinkListAll.add(b);
                    priceLinkListAll.add(Double.valueOf(strings.get(1)));
                    priceLinkListAll.add(Double.valueOf(strings.get(2)));
                    continue;
                }
                //昨日收盘价
                ydPrcie = Double.valueOf(strings.get(4));
                break;
            }
            double prcie = Double.valueOf(list.get(0).get(4));
            //去重、排序
            priceLinkList = priceLinkList.stream().distinct().collect(Collectors.toList());
            priceLinkListAll = priceLinkListAll.stream().distinct().collect(Collectors.toList());
            Collections.sort(priceLinkList);
            Collections.sort(priceLinkListAll);
            //涨幅
            double zhangfu = (prcie-ydPrcie)/ydPrcie*100;
            Double z1 = priceLinkListAll.get(0);
            Double z2 = priceLinkListAll.get(priceLinkListAll.size()-1);
            log.debug("当前最低价格：{}，最高价格：{}",z1,z2);
            List<ResultEntity> resultList = new ArrayList<>();
            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setName("B");
            resultEntity.setPrice(prcie);
            resultEntity.setZhangfu(df.format(zhangfu));
            resultEntity.setZf(df.format((z2-z1)/z1*100));
            //当前价格振幅
            double dzf1 = (prcie-z1)/z1*100;
            double dzf2 = Math.abs((prcie-z2)/z2*100);
            //振幅
            resultEntity.setSzf(new StringBuilder("+").append(df.format(dzf1)).toString());
            resultEntity.setXzf(new StringBuilder("-").append(df.format(dzf2)).toString());
            resultEntity.setDc(df.format(z2-z1));
            resultList.add(resultEntity);
            resultMap.put("resultList", resultList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.debug("当前价格：{}",resultMap);
        return resultMap;
    }

    @Override
    public List<BtcEntity> getFutures() throws IOException {
        Map<String, Object> map = new HashMap<>();
        //获取数据库数据
        long startTime = System.currentTimeMillis();
        log.debug("开始时间：{}",startTime);
        //FuturesData futuresData1 = new FuturesData();
        //futuresData1.setExchangeCode("114.pm");
        //linkedList.add(futuresData1);
        if (CollectionUtils.isEmpty(linkedList)){
            linkedList = futuresDataMapper.selectSts();
        }
        if (CollectionUtils.isEmpty(linkedList)){
            return null;
        }
        List<BtcEntity> list = new LinkedList<>();
        List<CompletableFuture<BtcEntity>> collect = linkedList.stream().map(futuresData -> CompletableFuture.supplyAsync(() ->
                        //调用方法
                {
                    try {
                        return getData(futuresData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }, threadPoolExecutor)
        ).collect(Collectors.toList());
        //获取返回值
        list = collect.stream().map(CompletableFuture::join).collect(Collectors.toList());

        //1、爬东方财富当日数据
        /*for (int i = 0; i < linkedList.size(); i++) {
            List<Double> priceLinkList = new LinkedList<Double>();
            List<Double> priceLinkListAll = new LinkedList<Double>();
            FuturesData futuresData = linkedList.get(i);
            //拼接地址
            Map urlMap = new HashMap<>();
            urlMap.put("secid", futuresData.getExchangeCode());
            //发送http请求
            long startTime1 = System.currentTimeMillis();
            String rxd = HttpUtils.doGet(new StrSubstitutor(urlMap).replace(Constant.URL), null);
            long endTime1 = System.currentTimeMillis();
            log.debug("执行时长：{}", endTime1 - startTime1);
            Map noded = (Map) JSON.parse(rxd);
            //数据集
            Map mapd = (Map) noded.get("data");
            List<Map<String,Object>> hisPrePrices = (List) mapd.get("hisPrePrices");
            String preClose = String.valueOf(hisPrePrices.get(0).get("prePrice"));
            //前一天收盘价
            double pre = Double.valueOf(preClose);
            //当前价格
            String price =  transformationCollection((List) mapd.get("trends"),priceLinkListAll);
            priceLinkListAll = priceLinkListAll.stream().distinct().collect(Collectors.toList());
            Collections.sort(priceLinkListAll);
            //涨幅
            Double p = Double.valueOf(price);
            double zhangfu = (p-pre)/pre*100;
            map.put("zhangfu",new StringBuilder(df.format(zhangfu)).append("%"));
            //当前价格
            map.put("price",price);
            //波动
            Double maxPrice = priceLinkListAll.get(priceLinkListAll.size() - 1);
            Double minPrice = priceLinkListAll.get(0);
            map.put("bodong",maxPrice - minPrice);
            //上振
            Double sz = (p-minPrice)/minPrice*100;
            map.put("sz",new StringBuilder(df.format(sz)).append("%"));
            //下振
            Double xz = (p-maxPrice)/maxPrice*100;
            map.put("xz",new StringBuilder(df.format(xz)).append("%"));
            //振幅
            map.put("zf",new StringBuilder(df.format(sz-xz)).append("%"));
            long endTime = System.currentTimeMillis();
            log.debug("执行时长：{}", endTime - startTime);*/
        log.debug("list:{}",list);
        Collections.sort(list);
            return list;
        }

    private BtcEntity getData(FuturesData futuresData) throws IOException {
        BtcEntity btcEntity = new BtcEntity();
        List<Double> priceLinkList = new LinkedList<Double>();
        List<Double> priceLinkListAll = new LinkedList<Double>();
        //拼接地址
        Map urlMap = new HashMap<>();
        urlMap.put("secid", futuresData.getExchangeCode());
        //发送http请求
        long startTime1 = System.currentTimeMillis();
        String rxd = HttpUtils.doGet(new StrSubstitutor(urlMap).replace(Constant.URL), null);
        long endTime1 = System.currentTimeMillis();
        log.debug("执行时长：{}", endTime1 - startTime1);
        Map noded = (Map) JSON.parse(rxd);
        //数据集
        Map mapd = (Map) noded.get("data");
        List<Map<String,Object>> hisPrePrices = (List) mapd.get("hisPrePrices");
        String preClose = String.valueOf(hisPrePrices.get(0).get("prePrice"));
        //前一天收盘价
        double pre = Double.valueOf(preClose);
        //当前价格
        String price =  transformationCollection((List) mapd.get("trends"),priceLinkListAll);
        priceLinkListAll = priceLinkListAll.stream().distinct().collect(Collectors.toList());
        Collections.sort(priceLinkListAll);
        //涨幅
        Double p = Double.valueOf(price);
        double zhangfu = (p-pre)/pre*100;
        btcEntity.setZhangfu(new StringBuilder(df.format(zhangfu)).append("%").toString());
        //当前价格
        btcEntity.setPrice(Double.valueOf(price));
        //波动
        Double maxPrice = priceLinkListAll.get(priceLinkListAll.size() - 1);
        Double minPrice = priceLinkListAll.get(0);
        btcEntity.setDc(maxPrice - minPrice);
        //上振
        Double sz = (p-minPrice)/minPrice*100;
        btcEntity.setSz(new StringBuilder(df.format(sz)).append("%").toString());
        //下振
        Double xz = (p-maxPrice)/maxPrice*100;
        btcEntity.setXz(new StringBuilder(df.format(xz)).append("%").toString());
        //振幅
        btcEntity.setSort(sz-xz);
        btcEntity.setZf(new StringBuilder(df.format(sz-xz)).append("%").toString());
        btcEntity.setName(futuresData.getName().substring(0,1));
        return btcEntity;
    }

    private String transformationCollection(List trends, List<Double> priceLinkListAll) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < trends.size(); i++) {
            String str = (String) trends.get(i);
            String[] split = str.split(",");
            priceLinkListAll.add(Double.valueOf(split[1]));
            priceLinkListAll.add(Double.valueOf(split[2]));
            priceLinkListAll.add(Double.valueOf(split[3]));
            if (i == trends.size()-1){
                return split[1];
            }
        }
        return null;
    }

//    public static void main(String[] args) throws InterruptedException {
//        while (true){
//            new BtcServiceImpl().getBtc();
//            Thread.sleep(6000);
//        }
//    }


}
