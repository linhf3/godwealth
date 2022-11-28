package com.godwealth.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.godwealth.algorithm.CoreAlgorithmContet;
import com.godwealth.dao.StockCodeMapper;
import com.godwealth.entity.StockCode;
import com.godwealth.service.ForeignExchangeService;
import com.godwealth.utils.Constant;
import com.godwealth.utils.HttpUtils;
import com.godwealth.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * 外汇
 * @author sie_linhongfei
 * @createDate 2022/08/07 22:27
 */
@Repository
@Slf4j
public class ForeignExchangeServiceImpl implements ForeignExchangeService {
    @Autowired
    private StockCodeMapper stockCodeMapper;
    @Autowired
    private CoreAlgorithmContet coreAlgorithmContet;
    //@Autowired
    //private RedisUtils redisUtils;
    @Override
    public Map<String, Object> foreignExchangeData() throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        StringBuilder sb = new StringBuilder("");
        //1.查询有效配置
        Object foreignExchangeList = null;//redisUtils.get("foreignExchangeList");
        List<StockCode> stockCodes = null;
        if (StringUtils.isBlank((CharSequence) foreignExchangeList)) {
            StockCode stockCode = new StockCode();
            stockCode.setCategory("3");
            stockCode.setSwEffective("有效");
            stockCodes = stockCodeMapper.selectByCondition(stockCode);
            if (!CollectionUtils.isEmpty(stockCodes)) {
                //redisUtils.set("foreignExchangeList", JSON.toJSONString(stockCodes));
            }
        } else {
            stockCodes = JSONObject.parseArray((String) foreignExchangeList, StockCode.class);
        }

        //2.循环爬数据并计算封装到list中
        LinkedList<Map<String, Object>> list = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        if (!CollectionUtils.isEmpty(stockCodes)) {
            for (int i = 0; i < stockCodes.size(); i++) {
                StockCode stockCodeF = stockCodes.get(i);
                //拼接地址
                Map urlMap = new HashMap<>();
                urlMap.put("place", stockCodeF.getExchangeCode());
                //发送http请求
                String rx = HttpUtils.doGet(new StrSubstitutor(urlMap).replace(Constant.FOREIGN_EXCHANGE_URL), null);
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
}
