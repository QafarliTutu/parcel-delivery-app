package com.example.msdelivery.service.courier;

import com.example.msdelivery.model.request.courier.CreateCourierReq;
import com.example.msdelivery.model.request.PageableReq;
import com.example.msdelivery.model.request.courier.UpdateCourierReq;
import com.example.msdelivery.repository.entity.Courier;
import org.springframework.data.domain.Page;

public interface CourierService {

    void createCourier(CreateCourierReq createCourierReq);

    Page<Courier> getAllCouriers(PageableReq pageableReq);

    void updateCourier(UpdateCourierReq updateCourierReq,
                       Long courierId);
}
