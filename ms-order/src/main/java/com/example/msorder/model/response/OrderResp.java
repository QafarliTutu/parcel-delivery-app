package com.example.msorder.model.response;

import com.example.msorder.util.enums.status.OrderStatus;
import com.example.msorder.util.enums.type.DeliveryType;
import com.example.msorder.util.enums.type.RoleType;
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
public class OrderResp {

    Long id;

    Long userId;

    Long parcelId;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    String deliveryAddress;

    DeliveryType deliveryType;

    OrderStatus orderStatus;

    RoleType statusUpdatedBy;

    RoleType addressUpdatedBy;

}
