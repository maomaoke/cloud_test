package com.open.cloud.helloserver.controller;

import com.open.cloud.helloserverapi.dto.User;
import com.open.cloud.helloserverapi.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Random;

/**
 * @author chenkechao
 * @date 2019-08-22 22:47
 */
@Slf4j
@RestController
public class HelloController implements HelloService {

    @GetMapping("/hello")
    public String hello() throws InterruptedException {

//        int sleepTime = new Random().nextInt(3000);
//
//        log.info("sleepTime = {}", sleepTime);
//
//        Thread.sleep(sleepTime);

        return "try hard !";
    }

    @Override
    public String hello(@RequestParam(value = "name") String name) {
        return "hello" + name;
    }

    @Override
    public User hello(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        return new User(name, age);
    }

    @Override
    public String hello(@RequestBody User user) {
        return "hello name=" + user.getName() + ", age=" + user.getAge();
    }
}
