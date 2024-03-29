package com.godwealth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * 例：股票期货通
 *
 * @author sie_linhongfei
 * @createDate 2021/11/3 16:38
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("com.godwealth.dao")
public class GodWealthApplication {

    public static void main(String[] args) {
        SpringApplication.run(GodWealthApplication.class, args);
    }

   @Bean
    public RestTemplate restTemplate() {
        // 默认的RestTemplate，底层是走JDK的URLConnection方式。
        return new RestTemplate();
    }


}
