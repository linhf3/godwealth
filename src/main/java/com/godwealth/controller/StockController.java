package com.godwealth.controller;

import com.godwealth.entity.StockCode;
import com.godwealth.service.StockService;
import com.godwealth.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * 股票
 * @author sie_linhongfei
 * @createDate 2022/07/09 10:27
 */
@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    /**
     *
     * @author sie_linhongfei
     * @createDate 2022/07/09 10:27
     * 股票当日偏离，多日偏离
     */
    @GetMapping("/stockData")
    public CommonResult stockData() throws IOException, GeneralSecurityException, MessagingException {
        return new CommonResult(200,"成功",stockService.stockData());
    }

    @PostMapping("/insertSelective")
    public CommonResult insertSelective(@RequestBody StockCode stockCode){
        return new CommonResult(200,"成功",stockService.insertSelective(stockCode));
    }

    @GetMapping("/delete/{code}")
    public CommonResult delete(@PathVariable("code") String code){
        stockService.delete(code);
        return new CommonResult(200,"成功");
    }

    @GetMapping("/query")
    public void query(){
        stockService.query();
    }

    @GetMapping("/insert/{code}")
    public CommonResult insert(@PathVariable("code") String code){
        stockService.insert(code);
        return new CommonResult(200,"成功");
    }

    @GetMapping("/querySockCodeList/{vars}")
    public CommonResult querySockCodeList(@PathVariable("vars")String vars){
        return new CommonResult(200,"成功",stockService.querySockCodeList(vars));
    }

    @PostMapping("/updateStockCode")
    public CommonResult updateStockCode(@RequestBody StockCode stockCode){
        return new CommonResult(200,"成功",stockService.updateByStockCode(stockCode));
    }





}
