package com.example.msorder.service.order.impl;

import static com.example.msorder.util.enums.status.OrderStatus.ASSIGNED;
import static com.example.msorder.util.enums.status.OrderStatus.WAITING;
import static com.example.msorder.util.enums.type.RoleType.ADMIN;
import static com.example.msorder.util.enums.type.RoleType.USER;
import static com.example.msorder.util.response.ResponseCode.ALREADY_HAVE_STATUS;

import com.example.msorder.exception.CustomException;
import com.example.msorder.exception.OrderNotFoundException;
import com.example.msorder.model.request.CreateDeliveryReq;
import com.example.msorder.model.request.CreateOrderReq;
import com.example.msorder.model.request.PageableReq;
import com.example.msorder.model.request.UpdateByAdminReq;
import com.example.msorder.model.request.UpdateByUserReq;
import com.example.msorder.model.request.UpdateDeliveryReq;
import com.example.msorder.model.request.UpdateOrderReq;
import com.example.msorder.model.response.OrderResp;
import com.example.msorder.rabbit.RabbitMessage;
import com.example.msorder.rabbit.RabbitProducerService;
import com.example.msorder.repository.OrderRepository;
import com.example.msorder.repository.entity.Order;
import com.example.msorder.service.order.OrderService;
import com.example.msorder.util.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RabbitProducerService producerService;

    @Override
    public void createOrder(CreateOrderReq createOrderReq, Long userID) {
        var order = Order
                .builder()
                .userId(userID)
                .orderStatus(WAITING)
                .statusUpdatedBy(USER)
                .addressUpdatedBy(USER)
                .parcelId(createOrderReq.getParcelId())
                .deliveryType(createOrderReq.getDeliveryType())
                .deliveryAddress(createOrderReq.getDeliveryAddress())
                .build();
        var savedOrder = orderRepository.save(order);
        log.info("Created new order: {}", savedOrder);
    }

    @Override
    public void updateOrder(UpdateOrderReq updateOrderReq){
        var order = orderRepository
                .findById(updateOrderReq.getOrderId())
                .orElseThrow(OrderNotFoundException::new);

        order.setOrderStatus(updateOrderReq.getOrderStatus());
        order.setStatusUpdatedBy(updateOrderReq.getStatusUpdatedBy());

        orderRepository.save(order);
    }

    @Override
    public void updateOrderByUser(
            UpdateByUserReq updateByUserReq,
            Long orderId,
            Long userId) {

        var order = orderRepository
                .findByUserIdAndId(userId, orderId)
                .orElseThrow(OrderNotFoundException::new);

        var currentStatus = order.getOrderStatus();

        if (updateByUserReq.getOrderStatus() != null){
            order.setOrderStatus(updateByUserReq.getOrderStatus());
            order.setStatusUpdatedBy(USER);
        }
        if (!StringUtils.isBlank(updateByUserReq.getDeliveryAddress())){
            order.setDeliveryAddress(updateByUserReq.getDeliveryAddress());
            order.setAddressUpdatedBy(USER);
        }
        orderRepository.save(order);

        if (currentStatus == ASSIGNED) {
            producerService.sendMessage(RabbitMessage
                    .builder()
                    .operation(RabbitMessage.Operation.UPDATE_DELIVERY)
                    .payload(UpdateDeliveryReq
                            .builder()
                            .deliveryId(orderId)
                            .statusUpdatedBy(USER)
                            .addressUpdatedBy(USER)
                            .orderStatus(order.getOrderStatus())
                            .deliveryAddress(order.getDeliveryAddress())
                            .build())
                    .build());
        }
    }

    @Override
    public void updateOrderByAdmin(UpdateByAdminReq updateByAdminReq, Long orderId) {
        var order = orderRepository
                .findById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        if (order.getOrderStatus() == updateByAdminReq.getOrderStatus())
            throw new CustomException(ALREADY_HAVE_STATUS);

        order.setOrderStatus(updateByAdminReq.getOrderStatus());
        order.setStatusUpdatedBy(ADMIN);
        orderRepository.save(order);

        if (updateByAdminReq.getOrderStatus() == ASSIGNED) {
            producerService.sendMessage(RabbitMessage
                    .builder()
                    .operation(RabbitMessage.Operation.CREATE_DELIVERY)
                    .payload(CreateDeliveryReq
                            .builder()
                            .orderId(orderId)
                            .deliveryType(order.getDeliveryType())
                            .courierId(updateByAdminReq.getCourierId())
                            .deliveryAddress(order.getDeliveryAddress())
                            .build())
                    .build());
        }
        else {
            producerService.sendMessage(RabbitMessage
                    .builder()
                    .operation(RabbitMessage.Operation.UPDATE_DELIVERY)
                    .payload(UpdateDeliveryReq
                            .builder()
                            .deliveryId(orderId)
                            .statusUpdatedBy(ADMIN)
                            .orderStatus(updateByAdminReq.getOrderStatus())
                            .build())
                    .build());
        }
    }

    @Override
    public OrderResp getUserOrder(Long userID, Long orderId) {
        var order = orderRepository
                .findByUserIdAndId(userID, orderId)
                .orElseThrow(OrderNotFoundException::new);
        return OrderResp
                .builder()
                .id(order.getId())
                .parcelId(order.getParcelId())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .orderStatus(order.getOrderStatus())
                .deliveryType(order.getDeliveryType())
                .deliveryAddress(order.getDeliveryAddress())
                .build();
    }

    @Override
    public Page<OrderResp> getAllUserOrders(PageableReq pageableReq, Long userID) {
        return orderRepository
                .findAllByUserId(
                        PageRequest.of(
                                pageableReq.getPage(),
                                pageableReq.getSize()),
                        userID)
                .map(order -> OrderResp
                        .builder()
                        .id(order.getId())
                        .orderStatus(order.getOrderStatus())
                        .deliveryType(order.getDeliveryType())
                        .deliveryAddress(order.getDeliveryAddress())
                        .build());
    }

    @Override
    public Page<Order> getAllOrders(PageableReq pageableReq) {
        return orderRepository
                .findAll(
                        PageRequest.of(
                                pageableReq.getPage(),
                                pageableReq.getSize()));
    }
}
