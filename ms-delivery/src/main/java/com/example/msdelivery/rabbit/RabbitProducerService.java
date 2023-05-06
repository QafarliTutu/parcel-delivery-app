package com.example.msdelivery.rabbit;

import static com.example.msdelivery.util.response.ResponseCode.SYSTEM_ERROR;

import com.example.msdelivery.config.RabbitMQConfig;
import com.example.msdelivery.exception.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitProducerService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(Object message) {
        try {
            var messageProperties = new MessageProperties();
            messageProperties.setHeader("microserviceName", "ORDER");

            var value = objectMapper.writeValueAsString(message);

            Message rabbitMessage = rabbitTemplate
                    .getMessageConverter()
                    .toMessage(value, messageProperties);

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_NAME,
                    RabbitMQConfig.QUEUE_NAME,
                    rabbitMessage);

            log.info("Message published in rabbitMQ: {}", rabbitMessage);

        } catch (Exception e) {
            throw new CustomException(SYSTEM_ERROR);
        }
    }

}
