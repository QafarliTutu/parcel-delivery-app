package com.example.msorder.repository;

import com.example.msorder.repository.entity.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByUserId(Pageable pageable, Long userId);

    Optional<Order> findByUserIdAndId(Long userId, Long orderId);
}
