package com.godwealth.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public abstract class MqAbstractConsumer {

    @Autowired
    private FiveDayDeviationConsumer fiveDayDeviationConsumer;

    void handle(Message message){
        fiveDayDeviationConsumer.deal(message);
    }

    abstract void deal(Message message);
}
