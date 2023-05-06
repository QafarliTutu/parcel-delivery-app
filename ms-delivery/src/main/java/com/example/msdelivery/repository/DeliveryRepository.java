package com.example.msdelivery.repository;

import com.example.msdelivery.repository.entity.Delivery;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {


    Optional<Delivery> findByCourierIdAndId(Long courierId, Long deliveryId);

    Page<Delivery> findAllByCourierId(Pageable pageable, Long courierId);
}
