package com.godwealth.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Message;

@Component
@Slf4j
public class MqListenerServerImpl implements MqListenerServer {

    @Autowired
    private MqAbstractConsumer mqAbstractConsumer;

    @RabbitListener(queues = "queue_topic")
    @Override
    public void topicListener(Message message) {
        //log.info("topic接收到消息:{}",message);
        mqAbstractConsumer.handle(message);
    }
}
