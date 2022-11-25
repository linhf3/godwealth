package com.godwealth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 * @author sie_linhongfei
 * @createDate 2022/07/09 10:27
 */
@Configuration
public class TaskExecutePool {
    /**
     * 线程池配置bean
     * @return Executor
     */
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(40, 120,
                600, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(2000));
        return executor;
    }

}
