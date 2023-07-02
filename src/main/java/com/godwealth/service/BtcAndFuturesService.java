package com.godwealth.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
/**
 *
 * @author sie_linhongfei
 * @createDate 2022/04/12 22:23
 */
@Service
public interface BtcAndFuturesService {

    Map<String,Object> getBtc();

    Map<String,Object> getFutures() throws IOException;
}
