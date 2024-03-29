package com.godwealth.algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 股票期货策略接口环境类
 * @author sie_linhongfei
 * @createDate 2022/07/09 10:27
 */
@Service
public class CoreAlgorithmContet {

    @Autowired
    private Map<String, CoreAlgorithm> coreAlgorithmMap;

    public Map<String, Object> deviationTheDayRate(String beanName, Map map) {
        return coreAlgorithmMap.get(beanName).deviationTheDayRate(map);
    };

    public String deviationRateCore(String beanName, List<List> list) {
        return coreAlgorithmMap.get(beanName).deviationManyDaysRate(list);
    };

    public Map<String, Object> deviationRateCore(String beanName, String code) {
        return coreAlgorithmMap.get(beanName).deviationRateCore(code);
    };
}
