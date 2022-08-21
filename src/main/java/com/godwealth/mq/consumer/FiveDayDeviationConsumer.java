package com.godwealth.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.godwealth.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@Slf4j
public class FiveDayDeviationConsumer extends MqAbstractConsumer {

//    public void fiveDayDeviation(String exchangeCode, List trendsList) {
//
//    }
    @Autowired
    private RedisUtils redisUtils;



    @Override
    void deal(Message message) {
        //Map<String,Object> map = (Map<String, Object>) JSON.parse(message.getBody());
        //log.debug("消息map：{}",map);
        //String service = (String) map.get("service");

        //        String data = (String) redisUtils.get(exchangeCode);
//        if (StringUtils.isEmpty(data)) {
//            FuturesData futuresData = futuresDataMapper.selectByExchangeCode(exchangeCode);
//            if (null == futuresData) {
//                return;
//            }
//            redisUtils.set(exchangeCode, futuresData.getData());
//            data = futuresData.getData();
//            if (StringUtils.isEmpty(data)) {
//                return;
//            }
//        }
//        List<List> fiveList = JSON.parseArray(data, List.class);
//        fiveList.add(trendsList);
//        String fProportion = futuresCoreAlgorithm.deviationManyDaysRate(fiveList);
//        redisUtils.set(
//                new StringBuilder(exchangeCode).append("_").append("fProportion").toString(), fProportion);
    }
}
