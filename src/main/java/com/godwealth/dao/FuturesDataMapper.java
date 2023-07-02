package com.godwealth.dao;

import com.godwealth.entity.FuturesData;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedList;
import java.util.List;

public interface FuturesDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuturesData record);

    int insertSelective(FuturesData record);

    FuturesData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuturesData record);

    int updateByPrimaryKeyWithBLOBs(FuturesData record);

    int updateByPrimaryKey(FuturesData record);

    List<FuturesData> selectAll();

    int insertList(List<FuturesData> record);

    FuturesData selectByExchangeCode(@Param("exchangeCode") String exchangeCode);

    LinkedList<FuturesData> selectSts();
}