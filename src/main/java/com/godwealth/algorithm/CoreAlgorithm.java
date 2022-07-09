package com.godwealth.algorithm;

import java.util.List;
import java.util.Map;

/**
 * 股票期货策略接口
 * @author sie_linhongfei
 * @createDate 2022/07/09 10:27
 */
public interface CoreAlgorithm {

    /**
     * 获取当日偏离数据
     * @param map
     * @return Map<String, Object>
     */
    Map<String, Object> deviationTheDayRate(Map map);

    /**
     * 获取多日偏离
     * @param list
     * @return double
     */
    String deviationManyDaysRate(List<List> list);


}
