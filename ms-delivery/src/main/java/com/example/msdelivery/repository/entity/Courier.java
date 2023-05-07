package com.example.msdelivery.repository.entity;

import com.example.msdelivery.util.enums.status.CourierStatus;
import com.example.msdelivery.util.enums.type.CourierType;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "_courier", schema = "msdelivery")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Courier {

    @Id
    @Column(nullable = false)
    Long id;

    @Column(nullable = false)
    String username;

    @Column(nullable = false)
    Boolean isActive;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    CourierStatus courierStatus;

    @Column(name = "courier_type", nullable = false)
    @Enumerated(EnumType.STRING)
    CourierType courierType;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    LocalDateTime updatedAt;
}
