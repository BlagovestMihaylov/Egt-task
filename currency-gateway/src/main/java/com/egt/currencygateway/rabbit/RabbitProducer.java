package com.egt.currencygateway.rabbit;

import com.egt.currencygateway.api.model.RequestDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitProducer
{
    private final String exchangeName;
    private final String routingKey;
    private final RabbitTemplate rabbitTemplate;

    public RabbitProducer(final RabbitTemplate rabbitTemplate,
                          @Value("${spring.rabbitmq.template.exchange}") final String exchangeName,
                          @Value("${spring.rabbitmq.template.routing-key}") final String routingKey)
    {
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;


        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }


    public void send(final RequestDTO message)
    {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }
}
