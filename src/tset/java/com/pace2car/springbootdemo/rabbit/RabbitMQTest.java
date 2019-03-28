package com.pace2car.springbootdemo.rabbit;

import com.pace2car.springbootdemo.shiro.entity.UUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Pace2Car
 * @date 2019/3/28 10:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQTest {

    @Autowired
    private HelloSender helloSender;

    @Autowired
    private HelloSender2 helloSender2;

    @Autowired
    private TopicSender topicSender;

    @Autowired
    private FanoutSender fanoutSender;

    @Test
    public void hello() {
        helloSender.send();
    }

    @Test
    public void oneToMany() throws Exception {
        for (int i = 0; i < 20; i++) {
            helloSender.send(i);
            Thread.sleep(50);
        }
    }

    @Test
    public void manyToMany() throws Exception {
        for (int i = 0; i < 20; i++) {
            helloSender.send(i);
            helloSender2.send(i);
            Thread.sleep(50);
        }
    }

    @Test
    public void sendObject() throws Exception {
        helloSender.send(new UUser().setUsername("aaa"));
        helloSender.send(new UUser().setUsername("bbb"));
    }

    @Test
    public void topicTest() throws Exception {
        topicSender.send1();
        topicSender.send2();
    }

    @Test
    public void fanoutTest() throws Exception {
        fanoutSender.send();
        fanoutSender.send();
    }

}
