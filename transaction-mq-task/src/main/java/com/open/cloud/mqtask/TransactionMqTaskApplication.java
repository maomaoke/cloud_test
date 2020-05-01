package com.open.cloud.mqtask;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * @author CHEN-KE-CHAO
 * @date 2019-08-29-下午1:41
 */
@Slf4j
@EnableFeignClients(basePackages = "com.open.cloud.mqclient")
@EnableDiscoveryClient
@SpringBootApplication
public class TransactionMqTaskApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TransactionMqTaskApplication.class, args);

        try {
            ProcessMessageTask task = context.getBean(ProcessMessageTask.class);
            task.start();
            new CountDownLatch(1).await();
        } catch (InterruptedException e) {
            log.error("项目启动异常", e);
        }
    }
}
