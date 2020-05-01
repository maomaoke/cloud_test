package com.open.cloud.rabbitmqhello.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author CHEN-KE-CHAO
 * @date 2019-08-27-下午4:42
 */
@Slf4j
@Component
public class Receiver {

    @RabbitListener(queues = {"hello"})
    public void process(Object p) {
        log.info("receiver is [{}]", p);
    }
}
