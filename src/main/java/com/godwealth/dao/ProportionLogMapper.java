package com.godwealth.dao;

import com.godwealth.entity.ProportionLog;

import java.util.List;

public interface ProportionLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProportionLog record);

    int insertSelective(ProportionLog record);

    ProportionLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProportionLog record);

    int updateByPrimaryKey(ProportionLog record);

    int insertList(List<ProportionLog> record);

    List<ProportionLog> selectByCondition(ProportionLog proportionLog);
}