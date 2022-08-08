package com.godwealth.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * 外汇
 * @author sie_linhongfei
 * @createDate 2022/08/07 22:27
 */
@Service
public interface ForeignExchangeService {
    Map<String,Object> foreignExchangeData() throws IOException;
}
