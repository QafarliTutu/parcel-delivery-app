package com.example.msorder.model.request;

import com.example.msorder.util.enums.type.DeliveryType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateDeliveryReq {

    @NotNull(message = "orderId {javax.validation.constraints.NotNull.message}")
    Long orderId;

    @NotNull(message = "orderId {javax.validation.constraints.NotNull.message}")
    Long courierId;

    @NotEmpty(message = "deliveryAddress {javax.validation.constraints.NotEmpty.message}")
    String deliveryAddress;

    @NotNull(message = "deliveryType {javax.validation.constraints.NotNull.message}")
    DeliveryType deliveryType;

}
