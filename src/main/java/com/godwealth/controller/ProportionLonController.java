package com.godwealth.controller;


import com.godwealth.entity.ProportionLog;
import com.godwealth.service.ProportionLogService;
import com.godwealth.utils.CommonResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 往日偏离
 * @author sie_linhongfei
 * @createDate 2022/07/09 10:27
 */
@RestController
@RequestMapping("/proportionLog")
public class ProportionLonController {


    @Autowired
    private  ProportionLogService proportionLogService;

    @PostMapping("/getStockDeviationLogs")
    public CommonResult getStockDeviationLogs(@RequestBody ProportionLog proportionLog){
        return new CommonResult(200,"成功",proportionLogService.selectByCondition(proportionLog));
    }

}
