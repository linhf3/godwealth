package com.godwealth.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.godwealth.dao.StockCodeMapper;
import com.godwealth.entity.StockCode;
import com.godwealth.service.SinaFuturesService;
import com.godwealth.utils.Constant;
import com.godwealth.utils.HttpUtils;
import com.godwealth.utils.RedisUtils;
import com.godwealth.utils.SortUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * 期货
 *
 * @author sie_linhongfei
 * @createDate 2022/07/09 10:27
 */
@Repository
@Slf4j
public class SinaFuturesServiceImpl implements SinaFuturesService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private StockCodeMapper stockCodeMapper;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public Map<String, Object> sFuturesData() throws IOException {

        //1.查询有效配置
        Map<String, Object> resultMap = new HashMap<>();
        Object futuresEffectiveList = redisUtils.get("sfuturesEffectiveList");
        List<StockCode> stockCodes = null;
        if (StringUtils.isBlank((CharSequence) futuresEffectiveList)) {
            StockCode stockCode = new StockCode();
            stockCode.setCategory("2");
            stockCode.setSinaExchangeCode("恭喜发财");
            stockCodes = stockCodeMapper.selectByCondition(stockCode);
            if (!CollectionUtils.isEmpty(stockCodes)) {
                redisUtils.set("sfuturesEffectiveList", JSON.toJSONString(stockCodes));
            }
        } else {
            stockCodes = JSONObject.parseArray((String) futuresEffectiveList, StockCode.class);
        }
        //2.循环爬数据并计算封装到list中
        List<Map<String, Object>> list = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        if (!CollectionUtils.isEmpty(stockCodes)) {
            List<CompletableFuture<Map<String,Object>>> collect = stockCodes.stream().map(stockCodeF -> CompletableFuture.supplyAsync(() ->
                    //调用别的方法
                    {
                        try {
                            return getData(stockCodeF);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }, threadPoolExecutor)
            ).collect(Collectors.toList());
            //获取返回值
            list = collect.stream().map(CompletableFuture::join).collect(Collectors.toList());
        }
        resultMap.put("resultList", list);
        long endTime = System.currentTimeMillis();
        log.debug("执行时长：{}", endTime - startTime);
        //log.debug("期货：{}", resultMap);
        //数据集
        return resultMap;
    }

    public Map<String,Object> getData(StockCode stockCodeF) throws IOException {
        String sinaExchangeCode = stockCodeF.getSinaExchangeCode();
        if (StringUtils.isBlank(sinaExchangeCode)) {
            return null;
        }
        //1、爬东方财富当日数据
        //拼接地址
        Map urlMap = new HashMap<>();
        urlMap.put("futuresUrl", stockCodeF.getExchangeCode());
        //发送http请求
        String rxd = HttpUtils.doGet(new StrSubstitutor(urlMap).replace(Constant.futuresUrl), null);
        Map noded = (Map) JSON.parse(rxd);
        //数据集
        Map mapd = (Map) noded.get("data");
        //转换集合
        List trendsM = transformationCollection((List) mapd.get("trends"));
        if (trendsM.size() == 0){
            return null;
        }
        if (StringUtils.isBlank(stockCodeF.getSinaFiveMinuteChartData())){
            return null;
        }
        List sfuturesEffectiveListNode = (List) JSON.parseArray(stockCodeF.getSinaFiveMinuteChartData());
        sfuturesEffectiveListNode.remove(4);

        sfuturesEffectiveListNode.add(trendsM);
        //3、进行计算获取数据
        Map<String, Object> map = calculateData(sfuturesEffectiveListNode, stockCodeF);
        map.put("name", stockCodeF.getName());
        String proportion = (String) map.get("proportion");
        Double proportionDouble = Double.valueOf(proportion.replace("+", "").replace("%", ""));
        if (null == stockCodeF.getDownwardDeviation() || 0 == stockCodeF.getDownwardDeviation()) {
            stockCodeF.setDownwardDeviation(-100);
        }
        if (null == stockCodeF.getDeviation() || 0 == stockCodeF.getDeviation()) {
            stockCodeF.setDeviation(100);
        }
        if (proportionDouble <= stockCodeF.getDownwardDeviation()) {
            map.put("positiveNegativeFlag", -1);
        } else if (proportionDouble >= stockCodeF.getDeviation()) {
            map.put("positiveNegativeFlag", 1);
        } else {
            map.put("positiveNegativeFlag", 0);
        }
        return map;
    }

    @Override
    public void getSinaData() throws IOException, InterruptedException {
        StockCode quStockCode = new StockCode();
        quStockCode.setCategory("2");
        quStockCode.setSinaExchangeCode("发财，发大财");
        List<StockCode> stockCodes = stockCodeMapper.selectByCondition(quStockCode);
        for (int i = 0; i < stockCodes.size(); i++) {
            StockCode stockCode = stockCodes.get(i);
            if (StringUtils.isBlank(stockCode.getSinaExchangeCode())) {
                continue;
            }
            log.debug("爬取五日数据");
            //2、直接爬五日数据（包含有当日数据）
            Map vMap = new HashMap<>();
            vMap.put("variety", stockCode.getSinaExchangeCode());
            //发送http请求
            String rx = HttpUtils.sendGet(new StrSubstitutor(vMap).replace(Constant.SINA_FOUR_DAYS_LINE), null);
            stockCode.setSinaFiveMinuteChartData(rx.substring(rx.indexOf("["), rx.lastIndexOf("]") + 1));
            //休眠2秒
            Thread.sleep(3000);
            //爬日线数据
            log.debug("爬取日k数据");
            //发送http请求
            String rxd = HttpUtils.sendGet(new StrSubstitutor(vMap).replace(Constant.SINA_DAILY_KLINE), null);
            List list = (List) JSON.parseArray(rxd.substring(rx.indexOf("["), rxd.lastIndexOf("]") + 1));
            List dayList = list.subList(list.size() - 13, list.size());
            stockCode.setSinaDailyData(JSON.toJSONString(dayList));
            //休眠2秒
            Thread.sleep(4000);
        }
        //保存
        for (int i = 0; i < stockCodes.size(); i++) {
            stockCodeMapper.updateByPrimaryKeySelective(stockCodes.get(i));
        }
    }



    private List transformationCollection(List list) {
        LinkedList<List> lists = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            String s = (String) list.get(i);
            String[] split = s.split(",");
            LinkedList<String> l = new LinkedList<>();
            l.add(split[0]);
            l.add(split[1]);
            l.add(split[4]);
            l.add(split[2]);
            l.add(split[3]);
            lists.add(l);
        }
        return lists;
    }

    /**
     * 将数据进行计算
     *
     * @param list
     * @return
     */
    private Map<String, Object> calculateData(List list, StockCode stockCodeF) throws IOException {
        //1、获取一日偏离和最高最低价
        List l = (List) list.get(4);
        //获取一日偏离，高低价，日差
        Map todateData = getTodateData(l);
        //获取五日偏离，五日差
        Map fiveData = getFiveteData(list);
        //获取wr
        Map<String, Object> wr = getWr(todateData, stockCodeF);
        todateData.putAll(wr);
        todateData.put("fProportion", fiveData.get("proportion"));
        todateData.put("fiveDailySpread", fiveData.get("fiveDailySpread"));
        return todateData;

    }

    private Map<String, Object> getWr(Map todateData, StockCode stockCodeF) throws IOException {
        Map<String, Object> reMap = new HashMap<>();
        List dayList = (List) JSON.parseArray(stockCodeF.getSinaDailyData());
        List<Double> eightDays = new ArrayList<>();
        List<Double> fourteenDays = new ArrayList<>();
        double toMin = (double) todateData.get("min");
        double toMax = (double) todateData.get("max");
        eightDays.add(toMin);
        eightDays.add(toMax);
        fourteenDays.add(toMin);
        fourteenDays.add(toMax);
        for (int i = dayList.size() - 1; i >= 0; i--) {
            Map dayL = (Map) dayList.get(i);
            String h = (String) dayL.get("h");
            String l = (String) dayL.get("l");
            Double dh = Double.valueOf(h);
            Double dl = Double.valueOf(l);
            if (i >= 6) {
                eightDays.add(dh);
                eightDays.add(dl);
            }
            fourteenDays.add(dh);
            fourteenDays.add(dl);
        }
        Collections.sort(eightDays);
        Collections.sort(fourteenDays);
        double price = (double) todateData.get("price");
        double d8 = 100 * (eightDays.get(15) - price) / (eightDays.get(15) - eightDays.get(0));
        double d14 = 100 * (fourteenDays.get(27) - price) / (fourteenDays.get(27) - fourteenDays.get(0));
        reMap.put("d8", Constant.format.format(d8));
        reMap.put("d14", Constant.format.format(d14));
        return reMap;
    }

    private Map getFiveteData(List list) {
        Map<String, Object> reMap = new HashMap<>();
        //存放正数数据
        Set plus = new HashSet<>();
        //存放负数数据
        Set negative = new HashSet();
        int curentNum = 0;//当前价格的值*100
        boolean curentType = true;//当前是正还是负偏离，匹配数组
        double v = 0.00;
        for (int j = 0; j < list.size(); j++) {
            List<Double> price = new ArrayList<>();
            List l = (List) list.get(j);
            for (int i = 0; i < l.size(); i++) {
                List split = (List) l.get(i);
                //当前
                String sh = (String) split.get(1);
                Double s1 = Double.valueOf(sh);
                price.add(s1);
                String sf = (String) split.get(2);
                Double s2 = Double.valueOf(sf);
                BigDecimal s3 = new BigDecimal((s1 - s2) * 100);
                int f1 = (int) s3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                if (s1 >= s2) {
                    plus.add(f1);
                } else {

                    negative.add(Math.abs(f1));
                }
                if (j == list.size() - 1 && i == l.size() - 1) {
                    curentNum = Math.abs(f1);
                    reMap.put("price", s1);
                    if (s1 < s2) {
                        curentType = false;
                    }
                }
            }
            //找出最大最小值
            double d = Collections.max(price) - Collections.min(price);
            v += d;
        }
        if (!CollectionUtils.isEmpty(plus) && !CollectionUtils.isEmpty(negative)) {
            plus.add(0);
            negative.add(0);
        }
        Integer[] pluss = (Integer[]) plus.toArray(new Integer[plus.size()]);
        Integer[] negatives = (Integer[]) negative.toArray(new Integer[negative.size()]);
        SortUtils.shellInserSort(pluss);
        SortUtils.shellInserSort(negatives);
        double index = 0.0d;
        double proportion = 0.0d;
        if (curentType) {
            index = SortUtils.binarySearch(pluss, 0, pluss.length, curentNum);
            proportion = (index / pluss.length) * 100;
        } else {
            index = SortUtils.binarySearch(negatives, 0, negatives.length, curentNum);
            proportion = -(index / negatives.length) * 100;
        }
        reMap.put("fiveDailySpread", Constant.format.format(v / 5));
        reMap.put("proportion", proportion > 0 ? "+" + Math.round(proportion) + "%" : Math.round(proportion) + "%");
        return reMap;
    }

    /**
     * 获取当日
     *
     * @param list
     * @return
     */
    private Map<String, Object> getTodateData(List<List> list) {
        Map<String, Object> reMap = new HashMap<>();
        //存放正数数据
        Set plus = new HashSet<>();
        //存放负数数据
        Set negative = new HashSet();
        int curentNum = 0;//当前价格的值*100
        boolean curentType = true;//当前是正还是负偏离，匹配数组
        List<Double> price = new ArrayList<>();
        //设置价格是否存在上下波动，即存在0的情况，计算存在误差
        for (int j = 0; j < list.size(); j++) {
            List split = list.get(j);
            //当前
            String sh = (String) split.get(1);
            Double s1 = Double.valueOf(sh);
            price.add(s1);
            //平均
            String sf = (String) split.get(2);
            Double s2 = Double.valueOf(sf);
            BigDecimal s3 = new BigDecimal((s1 - s2) * 100);
            int f1 = (int) s3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            if (s1 >= s2) {
                plus.add(f1);
            } else {

                negative.add(Math.abs(f1));
            }
            if (j == list.size() - 1) {
                curentNum = Math.abs(f1);
                reMap.put("price", s1);
                if (s1 < s2) {
                    curentType = false;
                }
            }
        }
        if (!CollectionUtils.isEmpty(plus) && !CollectionUtils.isEmpty(negative)) {
            plus.add(0);
            negative.add(0);
        }
        Integer[] pluss = (Integer[]) plus.toArray(new Integer[plus.size()]);
        Integer[] negatives = (Integer[]) negative.toArray(new Integer[negative.size()]);
        SortUtils.shellInserSort(pluss);
        SortUtils.shellInserSort(negatives);
        double index = 0.0d;
        double proportion = 0.0d;
        if (curentType) {
            index = SortUtils.binarySearch(pluss, 0, pluss.length, curentNum);
            proportion = (index / pluss.length) * 100;
        } else {
            index = SortUtils.binarySearch(negatives, 0, negatives.length, curentNum);
            proportion = -(index / negatives.length) * 100;
        }
        //找出最大最小值
        Double max = Collections.max(price);
        Double min = Collections.min(price);
        reMap.put("max", max);
        reMap.put("min", min);
        reMap.put("dailySpread", max - min);
        reMap.put("proportion", proportion > 0 ? "+" + Math.round(proportion) + "%" : Math.round(proportion) + "%");
        return reMap;
    }
}
