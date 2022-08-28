package com.godwealth.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * 新浪期货接口
 * @author sie_linhongfei
 * @createDate 2022/07/09 10:27
 */
@Service
public interface SinaFuturesService {


    Map<String, Object> sFuturesData() throws IOException;

}
