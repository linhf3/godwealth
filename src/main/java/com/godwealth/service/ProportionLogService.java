package com.godwealth.service;

import com.godwealth.entity.ProportionLog;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 *
 * @author sie_linhongfei
 * @createDate 2022/04/12 22:23
 */
@Service
public interface ProportionLogService {
    //周一到周五每天下午三点只执行一次采集
    void collectStockDeviationLogs();

    //查询数据
    Map<String, Object> selectByCondition(ProportionLog proportionLog);

}
