package com.godwealth.service;

import org.springframework.stereotype.Service;

import java.util.Map;
/**
 *
 * @author sie_linhongfei
 * @createDate 2022/04/12 22:23
 */
@Service
public interface BtcService {
    Map<String,Object> getBtc();
}
