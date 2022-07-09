package com.godwealth.dao;

import com.godwealth.entity.StockCode;

import java.util.List;

public interface StockCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockCode record);

    int insertSelective(StockCode record);

    StockCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockCode record);

    int updateByPrimaryKey(StockCode record);

    int deleteBystockCode(String code);

    List<StockCode> selectByCondition(StockCode record);

    int insertList(List<StockCode> record);
}