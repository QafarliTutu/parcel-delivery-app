package com.example.msorder.repository.entity;

import com.example.msorder.util.enums.type.DeliveryType;
import com.example.msorder.util.enums.status.OrderStatus;
import com.example.msorder.util.enums.type.RoleType;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_order")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "user_id", nullable = false)
    Long userId;

    @Column(name = "parcel_id", nullable = false)
    Long parcelId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    LocalDateTime updatedAt;

    @Column(name = "delivery_address", nullable = false)
    String deliveryAddress;

    @Column(name = "delivery_type", nullable = false)
    DeliveryType deliveryType;

    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_updated_by", nullable = false)
    RoleType statusUpdatedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_updated_by", nullable = false)
    RoleType addressUpdatedBy;

}
