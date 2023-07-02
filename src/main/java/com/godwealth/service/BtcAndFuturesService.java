package com.godwealth.service;

import com.godwealth.entity.BtcEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
/**
 *
 * @author sie_linhongfei
 * @createDate 2022/04/12 22:23
 */
@Service
public interface BtcAndFuturesService {

    Map<String,Object> getBtc();

    List<BtcEntity> getFutures() throws IOException;
}
