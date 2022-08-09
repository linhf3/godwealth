package com.godwealth.scheduletask;

import com.godwealth.service.FuturesService;
import com.godwealth.service.ProportionLogService;
import com.godwealth.utils.HolidaysUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 *
 * @author sie_linhongfei
 * @createDate 2021/11/8 22:29
 */
@Component
public class DataProcessTask {

    @Autowired
    ProportionLogService proportionLogService;

    @Autowired
    private FuturesService futuresService;

    //周一至周五的上午15:10触发
    @Scheduled(cron = "0 10 15 ? * MON-FRI")
    public void collectStockDeviationLogs(){
        if (HolidaysUtils.whetherToWork()){
            proportionLogService.collectStockDeviationLogs();
        }
    }

    //周一至周五的上午15:10触发
    @Scheduled(cron = "0 10 15 ? * MON-FRI")
    public void execute(){
        if (HolidaysUtils.whetherToWork()){
            futuresService.updateFuturesData();
        }
    }

    //周一至周五的上午15:10触发设置四天差值
    @Scheduled(cron = "0 */1 9-23 ? * *")
    public void setFiveDayTotal() throws IOException {
        if (HolidaysUtils.whetherToWork()){
            futuresService.setFiveDayTotal();
        }
    }


}
