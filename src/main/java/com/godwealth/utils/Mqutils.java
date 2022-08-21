package com.godwealth.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Mqutils {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendStr(String exchange,String routingKey, String msg){
        log.debug("exchange：{},routingKey:{},msg:{}",exchange,routingKey,msg);
        rabbitTemplate.convertAndSend(exchange, routingKey, msg);
    }


}
