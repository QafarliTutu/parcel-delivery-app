package com.example.msdelivery.model.response;

import com.example.msdelivery.util.enums.status.OrderStatus;
import com.example.msdelivery.util.enums.type.DeliveryType;
import com.example.msdelivery.util.enums.type.RoleType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeliveryResp {

    Long id;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    String deliveryAddress;

    DeliveryType deliveryType;

    LocalDateTime estimatedDelivery;

    Long courierId;

    OrderStatus orderStatus;

    RoleType statusUpdatedBy;

    RoleType addressUpdatedBy;
}
