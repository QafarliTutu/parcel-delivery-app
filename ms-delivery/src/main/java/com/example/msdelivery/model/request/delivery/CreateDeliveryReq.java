package com.example.msdelivery.model.request.delivery;

import com.example.msdelivery.util.enums.type.DeliveryType;
import java.io.Serializable;
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
public class CreateDeliveryReq implements Serializable {

    @NotNull(message = "orderId {javax.validation.constraints.NotNull.message}")
    Long orderId;

    @NotNull(message = "orderId {javax.validation.constraints.NotNull.message}")
    Long courierId;

    @NotEmpty(message = "deliveryAddress {javax.validation.constraints.NotEmpty.message}")
    String deliveryAddress;

    @NotNull(message = "deliveryType {javax.validation.constraints.NotNull.message}")
    DeliveryType deliveryType;

}
