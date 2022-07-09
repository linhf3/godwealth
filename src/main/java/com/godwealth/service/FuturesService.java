package com.godwealth.service;


import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 期货接口
 * @author sie_linhongfei
 * @createDate 2022/07/09 10:27
 */
@Service
public interface FuturesService {

    List<Map<String,Object>> futuresData() throws IOException;

    /**
     * 获取期货数据，处理好存入数据库（最多4日数据）
     */
    void updateFuturesData();

}
