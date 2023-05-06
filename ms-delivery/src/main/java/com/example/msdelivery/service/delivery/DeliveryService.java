package com.example.msdelivery.service.delivery;

import com.example.msdelivery.model.request.delivery.CreateDeliveryReq;
import com.example.msdelivery.model.request.PageableReq;
import com.example.msdelivery.model.request.delivery.UpdateDeliveryByAdminReq;
import com.example.msdelivery.model.request.delivery.UpdateDeliveryByCourierReq;
import com.example.msdelivery.model.request.delivery.UpdateDeliveryReq;
import com.example.msdelivery.model.response.DeliveryResp;
import com.example.msdelivery.repository.entity.Delivery;
import org.springframework.data.domain.Page;

public interface DeliveryService {

    void createDelivery(CreateDeliveryReq createDeliveryReq);

    void updateDelivery(UpdateDeliveryReq updateDeliveryReq);

    void updateDeliveryByCourier(UpdateDeliveryByCourierReq updateDeliveryByCourierReq,
                                 Long courierId,
                                 Long deliveryId);

    void updateDeliveryByAdmin(UpdateDeliveryByAdminReq updateDeliveryByAdminReq,
                               Long deliveryId);

    DeliveryResp getCourierDelivery(Long courierId,
                                    Long deliveryId);

    Page<DeliveryResp> getAllCourierDeliveries(PageableReq pageableReq,
                                               Long courierId);

    Page<Delivery> getAllDeliveries(PageableReq pageableReq);
}
