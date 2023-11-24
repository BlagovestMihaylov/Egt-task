package com.egt.currencygateway.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig
{
    @Bean
    public TopicExchange topic(@Value("${spring.rabbitmq.template.exchange}") final String exchangeName)
    {
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    public Queue queue(@Value("${spring.rabbitmq.template.default-receive-queue}") final String defaultQueueName)
    {
        return new Queue(defaultQueueName, true);
    }

    @Bean
    public Binding binding(final TopicExchange topicExchange,
                           final Queue queue,
                           @Value("${spring.rabbitmq.template.routing-key}") final String routingKey)
    {
        return BindingBuilder.bind(queue)
                             .to(topicExchange)
                             .with(routingKey);
    }
}