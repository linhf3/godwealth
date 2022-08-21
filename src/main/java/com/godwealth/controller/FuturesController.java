package com.godwealth.controller;


import com.godwealth.service.FuturesService;
import com.godwealth.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
@RequestMapping("/futures")
public class FuturesController {

    @Autowired
    private FuturesService futuresService;

    /**
     *
     * @author sie_linhongfei
     * @createDate 2022/07/09 10:27
     * 期货当日偏离--速度版
     */
    @GetMapping("/futuresData")
    public CommonResult futuresData() throws IOException {
        return new CommonResult(200,"成功",futuresService.futuresData());
    }

    /**
     *
     * @author sie_linhongfei
     * @createDate 2022/07/09 10:27
     * 期货当日偏离--复杂版
     */
    @GetMapping("/futuresDataComplex")
    public CommonResult futuresDataComplex() throws IOException {
        return new CommonResult(200,"成功",futuresService.futuresDataComplex());
    }

    /**
     *
     * @author sie_linhongfei
     * @createDate 2022/07/09 10:27
     * 期货当日偏离，五日偏离
     */
    @GetMapping("/futuresfiveData")
    public CommonResult futuresfiveData() throws IOException {
        return new CommonResult(200,"成功",futuresService.futuresfiveData());
    }

}
