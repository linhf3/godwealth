package com.godwealth.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.godwealth.algorithm.CoreAlgorithmContet;
import com.godwealth.dao.FuturesDataMapper;
import com.godwealth.dao.StockCodeMapper;
import com.godwealth.entity.FuturesData;
import com.godwealth.entity.StockCode;
import com.godwealth.service.FuturesService;
import com.godwealth.utils.Constant;
import com.godwealth.utils.HttpUtils;
import com.godwealth.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

/**
 * 期货
 *
 * @author sie_linhongfei
 * @createDate 2022/07/09 10:27
 */
@Repository
@Slf4j
public class FuturesServiceImpl implements FuturesService {

    @Autowired
    private StockCodeMapper stockCodeMapper;

    @Autowired
    private FuturesDataMapper futuresDataMapper;

    @Autowired
    private CoreAlgorithmContet coreAlgorithmContet;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Map<String, Object> futuresData() throws IOException {

        Map<String, Object> resultMap = new HashMap<>();
        StringBuilder sb = new StringBuilder("");
        //1.查询有效配置
        Object futuresEffectiveList = redisUtils.get("futuresEffectiveList");
        List<StockCode> stockCodes = null;
        if (StringUtils.isBlank((CharSequence) futuresEffectiveList)) {
            StockCode stockCode = new StockCode();
            stockCode.setCategory("2");
            stockCode.setSwEffective("有效");
            stockCodes = stockCodeMapper.selectByCondition(stockCode);
            if (!CollectionUtils.isEmpty(stockCodes)) {
                redisUtils.set("futuresEffectiveList", JSON.toJSONString(stockCodes));
            }
        } else {
            stockCodes = JSONObject.parseArray((String) futuresEffectiveList, StockCode.class);
        }

        //2.循环爬数据并计算封装到list中
        LinkedList<Map<String, Object>> list = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        if (!CollectionUtils.isEmpty(stockCodes)) {
            for (int i = 0; i < stockCodes.size(); i++) {
                StockCode stockCodeF = stockCodes.get(i);
                //拼接地址
                Map urlMap = new HashMap<>();
                urlMap.put("futuresUrl", stockCodeF.getExchangeCode());
                //发送http请求
                String rx = HttpUtils.doGet(new StrSubstitutor(urlMap).replace(Constant.futuresUrl), null);
                Map node = (Map) JSON.parse(rx);
                //数据集
                Map map = (Map) node.get("data");
                List trendsM = (List) map.get("trends");
                //白天开盘，无数据的情况（晚上）
                if (CollectionUtils.isEmpty(trendsM)) {
                    continue;
                }
                Map<String, Object> reMap = coreAlgorithmContet.deviationTheDayRate("futuresCoreAlgorithm", map);
                reMap.put("name",stockCodeF.getName());
                String proportion = (String) reMap.get("proportion");
                Double proportionDouble = Double.valueOf(proportion.replace("+", "").replace("%", ""));
                if (null == stockCodeF.getDownwardDeviation() || 0 == stockCodeF.getDownwardDeviation()) {
                    stockCodeF.setDownwardDeviation(-100);
                }
                if (null == stockCodeF.getDeviation() || 0 == stockCodeF.getDeviation()) {
                    stockCodeF.setDeviation(100);
                }
                if (proportionDouble <= stockCodeF.getDownwardDeviation()) {
                    reMap.put("positiveNegativeFlag", -1);
                } else if (proportionDouble >= stockCodeF.getDeviation()) {
                    reMap.put("positiveNegativeFlag", 1);
                } else {
                    reMap.put("positiveNegativeFlag", 0);
                }
                list.add(reMap);
            }
        }
        resultMap.put("resultList", list);
        long endTime = System.currentTimeMillis();
        log.debug("执行时长：{}", endTime - startTime);
        log.debug("期货：{}", resultMap);
        return resultMap;
    }

    @Override
    public Map<String, Object> futuresfiveData() throws IOException {

        Map<String, Object> resultMap = new HashMap<>();
        StringBuilder sb = new StringBuilder("");
        //1.查询有效配置
        Object futuresEffectiveList = redisUtils.get("futuresEffectiveList");
        List<StockCode> stockCodes = null;
        if (StringUtils.isBlank((CharSequence) futuresEffectiveList)) {
            StockCode stockCode = new StockCode();
            stockCode.setCategory("2");
            stockCode.setSwEffective("有效");
            stockCodes = stockCodeMapper.selectByCondition(stockCode);
            if (!CollectionUtils.isEmpty(stockCodes)) {
                redisUtils.set("futuresEffectiveList", JSON.toJSONString(stockCodes));
            }
        } else {
            stockCodes = JSONObject.parseArray((String) futuresEffectiveList, StockCode.class);
        }

        //2.查询库信息
        Object allFuturesDataList = redisUtils.get("allFuturesDataList");
        List<FuturesData> futuresDataList = null;
        if (StringUtils.isBlank((CharSequence) allFuturesDataList)) {
            futuresDataList = futuresDataMapper.selectAll();
            redisUtils.set("allFuturesDataList", JSON.toJSONString(futuresDataList));
        } else {
            futuresDataList = JSONObject.parseArray((String) allFuturesDataList, FuturesData.class);
        }

        //3.循环爬数据并计算封装到list中
        LinkedList<Map<String, Object>> list = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        if (!CollectionUtils.isEmpty(stockCodes)) {
            for (int i = 0; i < stockCodes.size(); i++) {
                StockCode stockCodeF = stockCodes.get(i);
                //拼接地址
                Map urlMap = new HashMap<>();
                urlMap.put("futuresUrl", stockCodeF.getExchangeCode());
                //发送http请求
                String rx = HttpUtils.doGet(new StrSubstitutor(urlMap).replace(Constant.futuresUrl), null);
                Map node = (Map) JSON.parse(rx);
                //数据集
                Map map = (Map) node.get("data");
                List trendsM = (List) map.get("trends");
                //白天开盘，无数据的情况（晚上）
                if (CollectionUtils.isEmpty(trendsM)) {
                    continue;
                }
                Map<String, Object> reMap = coreAlgorithmContet.deviationTheDayRate("futuresCoreAlgorithm", map);
                reMap.put("name",stockCodeF.getName());
                String proportion = (String) reMap.get("proportion");
                Double proportionDouble = Double.valueOf(proportion.replace("+", "").replace("%", ""));
                if (null == stockCodeF.getDownwardDeviation() || 0 == stockCodeF.getDownwardDeviation()) {
                    stockCodeF.setDownwardDeviation(-100);
                }
                if (null == stockCodeF.getDeviation() || 0 == stockCodeF.getDeviation()) {
                    stockCodeF.setDeviation(100);
                }
                if (proportionDouble <= stockCodeF.getDownwardDeviation()) {
                    reMap.put("positiveNegativeFlag", -1);
                } else if (proportionDouble >= stockCodeF.getDeviation()) {
                    reMap.put("positiveNegativeFlag", 1);
                } else {
                    reMap.put("positiveNegativeFlag", 0);
                }
                //获取五日偏离
                try {
                    if (!CollectionUtils.isEmpty(futuresDataList)) {
                        String exchangeCode = stockCodeF.getExchangeCode();
                        for (int j = 0; j < futuresDataList.size(); j++) {
                            FuturesData futuresData = futuresDataList.get(j);
                            if (exchangeCode.equals(futuresData.getExchangeCode())) {
                                List trendsList = (List) map.get("trends");
                                List<List> dataList = JSON.parseArray(futuresData.getData(), List.class);
                                if (!CollectionUtils.isEmpty(dataList) && !CollectionUtils.isEmpty(dataList.get(0))) {
                                    dataList.add(trendsList);
                                    String v = coreAlgorithmContet.deviationRateCore("futuresCoreAlgorithm", dataList);
                                    reMap.put("fProportion", v);
                                }
                                break;
                            }
                        }
                    } else {
                        reMap.put("fProportion", "");
                    }
                } catch (Exception e) {
                    log.debug("错误信息：{}", e);
                }
                list.add(reMap);
            }
        }
        resultMap.put("resultList", list);
        long endTime = System.currentTimeMillis();
        log.debug("执行时长：{}", endTime - startTime);
        log.debug("期货：{}", resultMap);
        return resultMap;
    }

    /**
     * 获取期货数据，处理好存入数据库（最多4日数据）
     */
    @Override
    public void updateFuturesData() {
        //1.查询库信息
        Object allFuturesDataList = redisUtils.get("allFuturesDataList");
        List<FuturesData> futuresDataList = null;
        if (ObjectUtils.isEmpty(allFuturesDataList)) {
            futuresDataList = futuresDataMapper.selectAll();
            redisUtils.set("allFuturesDataList", JSON.toJSONString(futuresDataList));
        } else {
            futuresDataList = JSONObject.parseArray((String) allFuturesDataList, FuturesData.class);
        }

        //2.爬数据
        List<String> linkedList = new LinkedList<>();
        linkedList.add("103");
        linkedList.add("112");
        linkedList.add("113");
        linkedList.add("114");
        linkedList.add("115");
        LinkedList<FuturesData> futuresDataLinkedList = new LinkedList<>();
        List<FuturesData> finalFuturesDataList = futuresDataList;
        linkedList.forEach(s -> {
            Map urlMap = new HashMap<>();
            urlMap.put("place", s);
            //发送http请求
            String rx = null;
            try {
                rx = HttpUtils.doGet(new StrSubstitutor(urlMap).replace(Constant.FUTURESMAINFORCEURL), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Map node = (Map) JSON.parse(rx);
            List<Map<String, Object>> list = (List<Map<String, Object>>) node.get("list");
            //数据集
            list.forEach(map -> {
                String name = (String) map.get("name");
                if("103".equals(s)){
                    name = new StringBuilder(name).append("(美)").toString();
                }
                //if (name.contains("主力") && !name.contains("次")) {
                //发请求查当日明细数据，拼接地址
                Map urlMaps = new HashMap<>();
                String dm = (String) map.get("dm");
                String exchangeCode = new StringBuilder(s).append(".").append(dm).toString();
                urlMaps.put("futuresUrl", exchangeCode);
                //发送http请求
                String rxs = null;
                try {
                    rxs = HttpUtils.doGet(new StrSubstitutor(urlMaps).replace(Constant.futuresUrl), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Map nodes = (Map) JSON.parse(rxs);
                //获取爬取的数据集
                Map dataMap = (Map) nodes.get("data");
                List trendsList = (List) dataMap.get("trends");
                if (CollectionUtils.isEmpty(finalFuturesDataList)) {//需要批量插入
                    List<List> futuresDatasLinkedList = new LinkedList<>();
                    FuturesData futuresData = new FuturesData();
                    futuresData.setName(name);
                    futuresData.setExchangeCode(exchangeCode);
                    futuresDatasLinkedList.add(trendsList);
                    String str = JSON.toJSON(futuresDatasLinkedList).toString();
                    futuresData.setData(str);
                    futuresDataLinkedList.add(futuresData);
                    log.debug(futuresData.toString());
                } else {  //更新/插入
                    for (FuturesData f : finalFuturesDataList) {
                        if (exchangeCode.equals(f.getExchangeCode())) {
                            FuturesData futuresData = new FuturesData();
                            BeanUtils.copyProperties(f, futuresData);
                            String data = futuresData.getData();
                            List<List> dataList = JSON.parseArray(data, List.class);
                            String formatDate = Constant.slf.format(new Date());
                            List listStr = dataList.get(dataList.size() - 1);
                            if (CollectionUtils.isEmpty(listStr) && StringUtils.isNotBlank(f.getData())) {
                                dataList.remove(dataList.size() - 1);
                                dataList.add(trendsList);
                                String dataListString = JSON.toJSON(dataList).toString();
                                futuresData.setData(dataListString);
                                //更新数据
                                futuresDataMapper.updateByPrimaryKeySelective(futuresData);
                                continue;
                            }
                            String str = (String) listStr.get(listStr.size() - 1);
                            String[] split = str.split(",");
                            if (!formatDate.equals(split[0].substring(0, 10)) && dataList.size() == 4) {
                                dataList.remove(0);
                                dataList.add(trendsList);
                                String dataListString = JSON.toJSON(dataList).toString();
                                futuresData.setData(dataListString);
                                //更新数据
                                futuresDataMapper.updateByPrimaryKeySelective(futuresData);
                            } else if (!formatDate.equals(split[0].substring(0, 10)) && dataList.size() < 4) {
                                dataList.add(trendsList);
                                String dataListString = JSON.toJSON(dataList).toString();
                                futuresData.setData(dataListString);
                                //更新数据
                                futuresDataMapper.updateByPrimaryKeySelective(futuresData);
                            } else if (formatDate.equals(split[0].substring(0, 10))) {
                                dataList.remove(dataList.size() - 1);
                                dataList.add(trendsList);
                                String dataListString = JSON.toJSON(dataList).toString();
                                futuresData.setData(dataListString);
                                //更新数据
                                futuresDataMapper.updateByPrimaryKeySelective(futuresData);
                            }
                        }
                    }
                }
                //  }
            });
        });
        if (!CollectionUtils.isEmpty(futuresDataLinkedList)) {
            futuresDataMapper.insertList(futuresDataLinkedList);
        }

    }

    @Override
    public void updateFuturesSourceData() {
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("103");
        linkedList.add("112");
        linkedList.add("113");
        linkedList.add("114");
        linkedList.add("115");
        LinkedList<StockCode> stockCodeLinkedList = new LinkedList<>();
        linkedList.forEach(s -> {
            Map urlMap = new HashMap<>();
            urlMap.put("place", s);
            //发送http请求
            String rx = null;
            try {
                rx = HttpUtils.doGet(new StrSubstitutor(urlMap).replace(Constant.FUTURESMAINFORCEURL), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Map node = (Map) JSON.parse(rx);
            List<Map<String, Object>> list = (List<Map<String, Object>>) node.get("list");
            //数据集
            System.out.println(list.toArray());
            list.forEach(map -> {
                String name = (String) map.get("name");
                //if (name.contains("主力") && !name.contains("次")) {
                StockCode stockCode = new StockCode();
                if("103".equals(s)){
                    name = new StringBuilder(name).append("(美)").toString();
                }
                stockCode.setName(name);
                String dm = (String) map.get("dm");
                stockCode.setStockCode(dm);
                stockCode.setCategory("2");
                stockCode.setSwEffective("无效");
                stockCode.setMemo(name);
                stockCode.setDownwardDeviation(-90);
                stockCode.setDeviation(90);
                stockCode.setExchangeCode(new StringBuilder(s).append(".").append(dm).toString());
                stockCode.setAddUser("admin");
                stockCode.setAddDate(new Date());
                stockCodeLinkedList.add(stockCode);
                // }
            });
        });
        List<StockCode> stockCodes = new LinkedList<>();
        for (int i = 0; i < stockCodeLinkedList.size(); i++) {
            StockCode stockCode = stockCodeLinkedList.get(i);
            StockCode quStockCode = stockCodeMapper.selectByName(stockCode.getName());
            if (null == quStockCode) {
                stockCodes.add(stockCode);
            }
        }
        log.debug("list:{}", stockCodes);
        if (!CollectionUtils.isEmpty(stockCodes)) {
            stockCodeMapper.insertList(stockCodes);
        }


    }
}
