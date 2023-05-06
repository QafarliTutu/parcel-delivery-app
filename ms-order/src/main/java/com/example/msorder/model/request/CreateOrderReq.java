package com.example.msorder.model.request;

import com.example.msorder.util.enums.type.DeliveryType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderReq {

    @NotNull(message = "parcelId {javax.validation.constraints.NotNull.message}")
    Long parcelId;

    @NotEmpty(message = "deliveryAddress {javax.validation.constraints.NotEmpty.message}")
    String deliveryAddress;

    @NotNull(message = "deliveryType {javax.validation.constraints.NotNull.message}")
    DeliveryType deliveryType;

}
