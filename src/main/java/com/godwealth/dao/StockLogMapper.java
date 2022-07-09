package com.godwealth.dao;

import com.godwealth.entity.StockLog;

import java.util.List;

public interface StockLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockLog record);

    int insertSelective(StockLog record);

    StockLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockLog record);

    int updateByPrimaryKey(StockLog record);

    List<StockLog> selectByCondition(StockLog record);

    List<StockLog> getTodayToSellstockLog(StockLog record);

    List<StockLog> getTodaysBuystockLog(StockLog stockLog);
}