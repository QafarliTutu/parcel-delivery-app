package com.example.msdelivery.service.delivery.impl;

import static com.example.msdelivery.util.enums.status.CourierStatus.AVAILABLE;
import static com.example.msdelivery.util.enums.status.CourierStatus.BUSY;
import static com.example.msdelivery.util.enums.status.OrderStatus.ASSIGNED;
import static com.example.msdelivery.util.enums.status.OrderStatus.CANCELLED;
import static com.example.msdelivery.util.enums.status.OrderStatus.DELIVERED;
import static com.example.msdelivery.util.enums.type.RoleType.ADMIN;
import static com.example.msdelivery.util.enums.type.RoleType.COURIER;
import static com.example.msdelivery.util.enums.type.RoleType.USER;

import com.example.msdelivery.exception.DeliveryNotFoundException;
import com.example.msdelivery.model.request.delivery.CreateDeliveryReq;
import com.example.msdelivery.model.request.PageableReq;
import com.example.msdelivery.model.request.delivery.UpdateDeliveryByAdminReq;
import com.example.msdelivery.model.request.delivery.UpdateDeliveryByCourierReq;
import com.example.msdelivery.model.request.courier.UpdateCourierReq;
import com.example.msdelivery.model.request.delivery.UpdateDeliveryReq;
import com.example.msdelivery.model.request.order.UpdateOrderReq;
import com.example.msdelivery.model.response.DeliveryResp;
import com.example.msdelivery.rabbit.RabbitMessage;
import com.example.msdelivery.rabbit.RabbitProducerService;
import com.example.msdelivery.repository.DeliveryRepository;
import com.example.msdelivery.repository.entity.Delivery;
import com.example.msdelivery.service.courier.CourierService;
import com.example.msdelivery.service.delivery.DeliveryService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final CourierService courierService;

    private final RabbitProducerService producerService;

    @Override
    public void createDelivery(CreateDeliveryReq request) {
        var estimatedDelivery = LocalDateTime.now()
                .plusDays(request.getDeliveryType().getDays());

        var delivery = Delivery.builder()
                .orderStatus(ASSIGNED)
                .statusUpdatedBy(ADMIN)
                .addressUpdatedBy(USER)
                .id(request.getOrderId())
                .courierId(request.getCourierId())
                .estimatedDelivery(estimatedDelivery)
                .deliveryType(request.getDeliveryType())
                .deliveryAddress(request.getDeliveryAddress())
                .build();
        var savedDelivery = deliveryRepository.save(delivery);
        log.info("Created new delivery request: {}", savedDelivery);

        courierService.updateCourier(
                UpdateCourierReq
                        .builder()
                        .courierStatus(BUSY)
                        .build(),
                request.getCourierId());
    }

    @Override
    public void updateDelivery(UpdateDeliveryReq request) {
        var delivery = deliveryRepository
                .findById(request.getDeliveryId())
                .orElseThrow(DeliveryNotFoundException::new);

        if (request.getDeliveryAddress() != null && request.getAddressUpdatedBy() != null) {
            delivery.setDeliveryAddress(request.getDeliveryAddress());
            delivery.setAddressUpdatedBy(request.getAddressUpdatedBy());
        }
        if (request.getOrderStatus() != null && request.getStatusUpdatedBy() != null) {
            delivery.setOrderStatus(request.getOrderStatus());
            delivery.setStatusUpdatedBy(request.getStatusUpdatedBy());
        }
        deliveryRepository.save(delivery);
    }

    @Override
    public void updateDeliveryByCourier(
            UpdateDeliveryByCourierReq request,
            Long courierId,
            Long deliveryId) {

        var delivery = deliveryRepository
                .findByCourierIdAndId(courierId, deliveryId)
                .orElseThrow(DeliveryNotFoundException::new);

        delivery.setOrderStatus(request.getOrderStatus());
        delivery.setStatusUpdatedBy(COURIER);

        deliveryRepository.save(delivery);

        producerService.sendMessage(RabbitMessage
                .builder()
                .operation(RabbitMessage.Operation.UPDATE_ORDER)
                .payload(UpdateOrderReq
                        .builder()
                        .orderId(deliveryId)
                        .orderStatus(delivery.getOrderStatus())
                        .statusUpdatedBy(delivery.getStatusUpdatedBy())
                        .build())
                .build());

        if (request.getOrderStatus() == DELIVERED) {
            courierService.updateCourier(
                    UpdateCourierReq
                            .builder()
                            .courierStatus(AVAILABLE)
                            .build(),
                    delivery.getCourierId());
        }
    }

    @Override
    public void updateDeliveryByAdmin(UpdateDeliveryByAdminReq request, Long deliveryId) {
        var delivery = deliveryRepository
                .findById(deliveryId)
                .orElseThrow(DeliveryNotFoundException::new);

        delivery.setOrderStatus(request.getOrderStatus());
        delivery.setStatusUpdatedBy(ADMIN);

        deliveryRepository.save(delivery);

        producerService.sendMessage(RabbitMessage
                .builder()
                .operation(RabbitMessage.Operation.UPDATE_ORDER)
                .payload(UpdateOrderReq
                        .builder()
                        .orderId(deliveryId)
                        .orderStatus(delivery.getOrderStatus())
                        .statusUpdatedBy(delivery.getStatusUpdatedBy())
                        .build())
                .build());

        if (request.getOrderStatus() == CANCELLED || request.getOrderStatus() == DELIVERED) {
            courierService.updateCourier(
                    UpdateCourierReq
                            .builder()
                            .courierStatus(AVAILABLE)
                            .build(),
                    delivery.getCourierId());
        }
    }

    @Override
    public DeliveryResp getCourierDelivery(Long courierId, Long deliveryId) {
        var delivery = deliveryRepository
                .findByCourierIdAndId(courierId, deliveryId)
                .orElseThrow(DeliveryNotFoundException::new);
        return DeliveryResp
                .builder()
                .id(delivery.getId())
                .createdAt(delivery.getCreatedAt())
                .updatedAt(delivery.getUpdatedAt())
                .orderStatus(delivery.getOrderStatus())
                .deliveryType(delivery.getDeliveryType())
                .deliveryAddress(delivery.getDeliveryAddress())
                .estimatedDelivery(delivery.getEstimatedDelivery())
                .build();
    }

    @Override
    public Page<DeliveryResp> getAllCourierDeliveries(PageableReq request, Long courierId) {
        return deliveryRepository
                .findAllByCourierId(
                        PageRequest.of(
                                request.getPage(),
                                request.getSize()),
                        courierId)
                .map(delivery -> DeliveryResp
                        .builder()
                        .id(delivery.getId())
                        .orderStatus(delivery.getOrderStatus())
                        .deliveryType(delivery.getDeliveryType())
                        .deliveryAddress(delivery.getDeliveryAddress())
                        .build());
    }

    @Override
    public Page<Delivery> getAllDeliveries(PageableReq request) {
        return deliveryRepository
                .findAll(
                        PageRequest.of(
                                request.getPage(),
                                request.getSize()));
    }
}
