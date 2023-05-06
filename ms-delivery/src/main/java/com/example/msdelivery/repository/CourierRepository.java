package com.example.msdelivery.repository;

import com.example.msdelivery.repository.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, Long> {
}
