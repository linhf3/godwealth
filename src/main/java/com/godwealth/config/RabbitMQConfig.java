//package com.godwealth.config;
//
//import org.springframework.amqp.core.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMQConfig {
//    /**
//     * topic 五日偏离推送对应的队列
//     */
//    public static final String TOPIC_NAME = "queue_topic";
//    /**
//     * topic 五日偏离推送对应的交换机
//     */
//    public static final String TOPIC_EXCHANGE = "topic_exchange";
//    /**
//     * 定义对列
//     */
//    @Bean("queue")
//    public Queue queue() {
//        return QueueBuilder.durable(TOPIC_NAME).build();
//    }
//
//    /**
//     * 定义交换机
//     */
//    @Bean("exchange")
//    public Exchange exchange() {
//        //durable 是否持久化默认为true
//        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE).durable(true).build();
//    }
//
//    /**
//     * 对列和交换机绑定
//     */
//    @Bean
//    public Binding bindQueueExchange(Exchange exchange, Queue queue) {
//        return BindingBuilder.bind(queue).to(exchange).with("five_routing_key").noargs();
//    }
//
//}
