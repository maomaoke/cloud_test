package com.open.cloud.feignconsumer.controller;

import com.open.cloud.feignconsumer.client.RefactorHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CHEN-KE-CHAO
 * @date 2019-08-23-下午2:31
 */
@RestController
public class ConsumerController {


    @Autowired
    private RefactorHelloService refactorHelloService;

    @GetMapping("/feign-consumer")
    public String helloConsumer3() {
        return refactorHelloService.hello("小明", 12).toString();
    }
}
