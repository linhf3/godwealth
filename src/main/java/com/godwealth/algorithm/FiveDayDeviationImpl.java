package com.godwealth.algorithm;

import com.alibaba.fastjson.JSON;
import com.godwealth.dao.FuturesDataMapper;
import com.godwealth.entity.FuturesData;
import com.godwealth.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
@Slf4j
@EnableAsync
public class FiveDayDeviationImpl{

    @Autowired
    private FuturesDataMapper futuresDataMapper;

    @Autowired
    private CoreAlgorithm futuresCoreAlgorithm;

    @Autowired
    private RedisUtils redisUtils;

    public void fiveDayDeviation(String exchangeCode, List trendsList) {
        String data = (String) redisUtils.get(exchangeCode);
        if (StringUtils.isEmpty(data)) {
            FuturesData futuresData = futuresDataMapper.selectByExchangeCode(exchangeCode);
            if (null == futuresData) {
                return;
            }
            redisUtils.set(exchangeCode, futuresData.getData());
            data = futuresData.getData();
            if (StringUtils.isEmpty(data)) {
                return;
            }
        }
        List<List> fiveList = JSON.parseArray(data, List.class);
        fiveList.add(trendsList);
        String fProportion = futuresCoreAlgorithm.deviationManyDaysRate(fiveList);
        redisUtils.set(
                new StringBuilder(exchangeCode).append("_").append("fProportion").toString(), fProportion);
    }
}
