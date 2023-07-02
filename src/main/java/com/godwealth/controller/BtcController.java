package com.godwealth.controller;

import com.godwealth.entity.BtcEntity;
import com.godwealth.service.BtcAndFuturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 比特币
 * @author sie_linhongfei
 * @createDate 2022/08/07 22:27
 */
@RestController
@RequestMapping("/btc")
public class BtcController {
    @Autowired
    private BtcAndFuturesService btcAndFuturesService;

    @GetMapping("/getBtc")
    public Map<String,Object> getBtc(){
        return btcAndFuturesService.getBtc();
    }

    @GetMapping("/getFutures")
    public List<BtcEntity> getFutures() throws IOException {
        return btcAndFuturesService.getFutures();
    }
}
