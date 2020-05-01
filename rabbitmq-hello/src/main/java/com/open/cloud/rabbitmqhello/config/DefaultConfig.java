package com.open.cloud.rabbitmqhello.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author CHEN-KE-CHAO
 * @date 2019-08-27-下午4:44
 */
@Configuration
public class DefaultConfig {

    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }
}
