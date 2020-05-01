package com.open.cloud.rabbitmqhello;

import com.open.cloud.rabbitmqhello.message.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author CHEN-KE-CHAO
 * @date 2019-08-27-下午4:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitHelloApplicationTests {

    @Autowired
    private Sender sender;

    @Test
    public void test() {
        sender.send();
    }
}
