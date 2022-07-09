package com.godwealth.dao;

import com.godwealth.entity.DeviationStrategy;

public interface DeviationStrategyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviationStrategy record);

    int insertSelective(DeviationStrategy record);

    DeviationStrategy selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviationStrategy record);

    int updateByPrimaryKey(DeviationStrategy record);
}