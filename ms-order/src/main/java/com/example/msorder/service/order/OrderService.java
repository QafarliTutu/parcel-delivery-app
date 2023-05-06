package com.example.msorder.service.order;

import com.example.msorder.model.request.CreateOrderReq;
import com.example.msorder.model.request.PageableReq;
import com.example.msorder.model.request.UpdateByAdminReq;
import com.example.msorder.model.request.UpdateByUserReq;
import com.example.msorder.model.request.UpdateOrderReq;
import com.example.msorder.model.response.OrderResp;
import com.example.msorder.repository.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderService {

    void createOrder(CreateOrderReq createOrderReq, Long userID);

    void updateOrder(UpdateOrderReq updateOrderReq);

    void updateOrderByUser(UpdateByUserReq updateByUserReq, Long orderId, Long userId);

    void updateOrderByAdmin(UpdateByAdminReq updateByAdminReq, Long orderId);

    OrderResp getUserOrder(Long userID, Long orderId);

    Page<OrderResp> getAllUserOrders(PageableReq pageableReq, Long userID);

    Page<Order> getAllOrders(PageableReq pageableReq);
}
