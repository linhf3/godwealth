package com.godwealth.controller;

import com.godwealth.service.SinaFuturesService;
import com.godwealth.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 期货
 * @author sie_linhongfei
 * @createDate 2022/07/09 10:27
 */
@RestController
@RequestMapping("/sfutures")
public class SinaFuturesController {

    @Autowired
    private SinaFuturesService sFuturesData;

    @GetMapping("/sfuturesData")
    public CommonResult futuresData() throws IOException {
        return new CommonResult(200,"成功",sFuturesData.sFuturesData());
    }

    @GetMapping("/sfuturesDatas")
    public CommonResult sfuturesDatas() throws IOException {
        return new CommonResult(200,"成功",sFuturesData.sFuturesDatas());
    }







}
