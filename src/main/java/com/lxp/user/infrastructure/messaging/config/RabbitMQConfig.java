package com.lxp.user.infrastructure.messaging.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    /*
    public static final String EXCHANGE_NAME = "user.events";
    public static final String QUEUE_NAME = "user.events.queue";

    @Bean
    public TopicExchange userEventsExchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue userEventsQueue() {
        return QueueBuilder.durable(QUEUE_NAME)
            .build();
    }

    @Bean
    public Binding userEventsBinding(Queue userEventsQueue, TopicExchange userEventsExchange) {
        return BindingBuilder.bind(userEventsQueue)
            .to(userEventsExchange)
            .with("#"); // 모든 routing key를 받음
    }
    */

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
