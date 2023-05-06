package com.example.msdelivery.service.courier.impl;

import static com.example.msdelivery.util.enums.status.CourierStatus.AVAILABLE;

import com.example.msdelivery.exception.CourierNotFoundException;
import com.example.msdelivery.model.request.courier.CreateCourierReq;
import com.example.msdelivery.model.request.PageableReq;
import com.example.msdelivery.model.request.courier.UpdateCourierReq;
import com.example.msdelivery.repository.CourierRepository;
import com.example.msdelivery.repository.entity.Courier;
import com.example.msdelivery.service.courier.CourierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;

    @Override
    public void createCourier(CreateCourierReq request) {
        var courier = Courier
                .builder()
                .id(request.getId())
                .courierStatus(AVAILABLE)
                .username(request.getUsername())
                .isActive(request.getIsActive())
                .courierType(request.getCourierType())
                .build();
        courierRepository.save(courier);
    }

    @Override
    public Page<Courier> getAllCouriers(PageableReq request) {
        return courierRepository
                .findAll(
                        PageRequest.of(
                                request.getPage(),
                                request.getSize()));
    }

    @Override
    public void updateCourier(UpdateCourierReq request, Long courierId) {
        var courier = courierRepository
                .findById(courierId)
                .orElseThrow(CourierNotFoundException::new);

        courier.setCourierStatus(request.getCourierStatus());
        courierRepository.save(courier);
    }

}
