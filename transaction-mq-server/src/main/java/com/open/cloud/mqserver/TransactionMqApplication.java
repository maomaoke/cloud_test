package com.open.cloud.mqserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author CHEN-KE-CHAO
 * @date 2019-08-28-下午4:30
 */
@EnableEurekaClient
@SpringBootApplication
public class TransactionMqApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransactionMqApplication.class, args);
    }
}
