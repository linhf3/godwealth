package com.godwealth.mq.consumer;
import org.springframework.amqp.core.Message;
public interface MqListenerServer {
    void topicListener(Message message);
}
