package com.open.cloud.feignconsumer.client;

import com.open.cloud.feignconsumer.config.FeignConfiguration;
import com.open.cloud.helloserverapi.service.HelloService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author CHEN-KE-CHAO
 * @date 2019-08-23-下午3:21
 */
@FeignClient(value = "hello-server", configuration = {FeignConfiguration.class})
public interface RefactorHelloService extends HelloService {

}
