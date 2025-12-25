package com.lxp.user.infrastructure.messaging.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Exchanges
    @Bean
    public TopicExchange authEventsExchange() {
        return new TopicExchange("auth.events", true, false);
    }

    @Bean
    public TopicExchange userEventsExchange() {
        return new TopicExchange("user.events", true, false);
    }

    // Queues
    @Bean
    public Queue authEventsQueue() {
        return new Queue("auth-events.user-registered", true);
    }

    @Bean
    public Queue userEventsQueue() {
        return new Queue("user-events.user-creation-failed", true);
    }

    // Bindings
    @Bean
    public Binding authEventsBinding(Queue authEventsQueue, TopicExchange authEventsExchange) {
        return BindingBuilder.bind(authEventsQueue)
                .to(authEventsExchange)
                .with("UserRegisteredIntegrationEvent");
    }

    @Bean
    public Binding userEventsBinding(Queue userEventsQueue, TopicExchange userEventsExchange) {
        return BindingBuilder.bind(userEventsQueue)
                .to(userEventsExchange)
                .with("UserCreationFailedIntegrationEvent");
    }
}
