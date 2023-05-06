package com.example.msdelivery.model.request.delivery;

import com.example.msdelivery.beans.AllowedOrderStatus;
import com.example.msdelivery.util.enums.status.OrderStatus;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateDeliveryByAdminReq {

    @AllowedOrderStatus(anyOf = {
            OrderStatus.SHIPPED,
            OrderStatus.DELIVERED,
            OrderStatus.CANCELLED
    })
    @NotNull(message = "orderStatus {javax.validation.constraints.NotNull.message}")
    OrderStatus orderStatus;

}
