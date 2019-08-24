package com.open.cloud.helloserverapi.service;

import com.open.cloud.helloserverapi.dto.User;
import org.springframework.web.bind.annotation.*;

/**
 * @author CHEN-KE-CHAO
 * @date 2019-08-23-下午3:06
 */
@RequestMapping("/refactor")
public interface HelloService {

    @GetMapping("/hello4")
    String hello(@RequestParam(value = "name") String name);

    @GetMapping("/hello5")
    User hello(@RequestParam(value = "name") String name, @RequestParam("age") Integer age);

    @PostMapping("/hello6")
    String hello(@RequestBody User user);
}
