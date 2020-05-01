package com.open.cloud.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author CHEN-KE-CHAO
 * @date 2019-08-27-下午5:06
 */
@RestController
public class TestController {

    @Value("${from}")
    private String fromValue;

    @GetMapping("/test")
    public String test() {
        return fromValue + new Date();
    }
}
