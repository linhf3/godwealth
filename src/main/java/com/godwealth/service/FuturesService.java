package com.godwealth.service;


import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * 期货接口
 * @author sie_linhongfei
 * @createDate 2022/07/09 10:27
 */
@Service
public interface FuturesService {

    Map<String,Object> futuresData() throws IOException;

    /**
     * 获取期货数据，处理好存入数据库（最多4日数据）
     */
    void updateFuturesData();

    /**
     * 更新期货源数据（待以后数据异常使用）
     */
    void updateFuturesSourceData();

}
