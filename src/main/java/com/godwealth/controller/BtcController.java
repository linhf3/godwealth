package com.godwealth.controller;

import com.godwealth.service.BtcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private BtcService btcService;

    @GetMapping("/getBtc")
    public Map<String,Object> getBtc(){
        return btcService.getBtc();
    }
}
