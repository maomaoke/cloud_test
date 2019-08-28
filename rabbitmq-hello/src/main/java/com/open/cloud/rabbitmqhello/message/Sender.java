package com.open.cloud.rabbitmqhello.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author CHEN-KE-CHAO
 * @date 2019-08-27-下午4:39
 */
@Slf4j
@Component
public class Sender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send() {
        String content = "hello " + new Date();
        log.info("content = [{}]",content);
        // hello is routing key
        amqpTemplate.convertAndSend("hello", content);
    }
}
