package com.open.cloud.helloserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author chenkechao
 * @date 2019-08-22 22:47
 */
@Slf4j
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        log.info("date = {}", new Date().toString());
        return "try hard !";
    }
}
