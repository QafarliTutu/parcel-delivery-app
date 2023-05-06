package com.example.msorder.rabbit;

import static com.example.msorder.config.RabbitMQConfig.QUEUE_NAME;
import static com.example.msorder.util.response.ResponseCode.SYSTEM_ERROR;

import com.example.msorder.exception.CustomException;
import com.example.msorder.model.request.UpdateOrderReq;
import com.example.msorder.service.order.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitListenerService {

    private final OrderService orderService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = QUEUE_NAME, containerFactory = "rabbitListenerContainerFactory")
    public void onMessage(Message message, Channel channel) throws IOException {
        try {
            var microserviceName = (String) message
                    .getMessageProperties()
                    .getHeaders()
                    .get("microserviceName");

            if (microserviceName.equals("ORDER")) {
                var rabbitMessage = objectMapper.readValue(
                        message.getBody(),
                        RabbitMessage.class);

                log.info("Received message: {}", rabbitMessage);

                if (rabbitMessage.getOperation() == RabbitMessage.Operation.UPDATE_ORDER) {
                    var request = objectMapper.convertValue(
                            rabbitMessage.getPayload(),
                            UpdateOrderReq.class);
                    orderService.updateOrder(request);
                }
                channel.basicAck(
                        message.getMessageProperties().getDeliveryTag(),
                        false);
            }
            else {
                channel.basicReject(
                        message.getMessageProperties().getDeliveryTag(),
                        true);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(SYSTEM_ERROR);
        }
    }

}
