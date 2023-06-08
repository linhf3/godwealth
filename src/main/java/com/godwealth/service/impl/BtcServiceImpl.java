package com.godwealth.service.impl;

import com.alibaba.fastjson.JSON;
import com.godwealth.entity.ResultEntity;
import com.godwealth.service.BtcService;
import com.godwealth.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BtcServiceImpl implements BtcService {

    //yyyy-MM-dd HH:mm:ss 转换的时间格式  可以自定义
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat df= new DecimalFormat("#.00");
    DecimalFormat df1= new DecimalFormat("#.0");
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
            List<ResultEntity> resultList = new ArrayList<>();
            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setName("B");
            resultEntity.setPrice(prcie);
            resultEntity.setZhangfu(df.format(zhangfu));
            resultEntity.setZf(df.format((z2-z1)/z1*100));
            //当前价格振幅
            double dzf1 = (prcie-z1)/z1*100;
            double dzf2 = Math.abs((prcie-z2)/z2*100);
            double d1 = dzf1>=dzf2?dzf1:dzf2;
            //振幅
            resultEntity.setSzf(new StringBuilder("+").append(df.format(d1)).toString());
            resultEntity.setXzf(new StringBuilder("-").append(df.format(dzf2)).toString());
            resultEntity.setDc(df1.format(z2-z1));
            resultList.add(resultEntity);
            resultMap.put("resultList", resultList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.debug("当前价格：{}",resultMap);
        return resultMap;
    }

//    public static void main(String[] args) throws InterruptedException {
//        while (true){
//            new BtcServiceImpl().getBtc();
//            Thread.sleep(6000);
//        }
//    }

}
