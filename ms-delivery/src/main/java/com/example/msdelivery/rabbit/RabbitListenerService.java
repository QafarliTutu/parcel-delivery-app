package com.example.msdelivery.rabbit;

import static com.example.msdelivery.config.RabbitMQConfig.QUEUE_NAME;
import static com.example.msdelivery.util.response.ResponseCode.SYSTEM_ERROR;

import com.example.msdelivery.exception.CustomException;
import com.example.msdelivery.model.request.courier.CreateCourierReq;
import com.example.msdelivery.model.request.delivery.CreateDeliveryReq;
import com.example.msdelivery.model.request.delivery.UpdateDeliveryReq;
import com.example.msdelivery.service.courier.CourierService;
import com.example.msdelivery.service.delivery.DeliveryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitListenerService {

    private final DeliveryService deliveryService;
    private final CourierService courierService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = QUEUE_NAME, containerFactory = "rabbitListenerContainerFactory")
    public void onMessage(Message message, Channel channel) throws IOException {
        try {
            var microserviceName = (String) message
                    .getMessageProperties()
                    .getHeaders()
                    .get("microserviceName");

            if (microserviceName.equals("DELIVERY")) {

                var rabbitMessage = objectMapper.readValue(
                        message.getBody(),
                        RabbitMessage.class);
                log.info("Received message: {}", rabbitMessage);

                if (rabbitMessage.getOperation() == RabbitMessage.Operation.CREATE_COURIER) {
                    var request = objectMapper.convertValue(
                            rabbitMessage.getPayload(),
                            CreateCourierReq.class);
                    courierService.createCourier(request);
                }
                if (rabbitMessage.getOperation() == RabbitMessage.Operation.CREATE_DELIVERY) {
                    var request = objectMapper.convertValue(
                            rabbitMessage.getPayload(),
                            CreateDeliveryReq.class);
                    deliveryService.createDelivery(request);
                }
                if (rabbitMessage.getOperation() == RabbitMessage.Operation.UPDATE_DELIVERY) {
                    var request = objectMapper.convertValue(
                            rabbitMessage.getPayload(),
                            UpdateDeliveryReq.class);
                    deliveryService.updateDelivery(request);
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
