package com.pace2car.springbootdemo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Topic模式配置
 * @author Pace2Car
 * @date 2019/3/28 10:40
 */
@Configuration
public class TopicRabbitConfig {

    private final static String MESSAGE = "topic.message";
    private final static String MESSAGES = "topic.messages";

    @Bean
    public Queue queueMessage() {
        return new Queue(MESSAGE);
    }

    @Bean
    public Queue queueMessages() {
        return new Queue(MESSAGES);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }

    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }
}
