package com.example.msorder.model.request;

import com.example.msorder.beans.AllowedOrderStatus;
import com.example.msorder.util.enums.status.OrderStatus;
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
public class UpdateByUserReq {

    String deliveryAddress;

    @AllowedOrderStatus(anyOf = {
            OrderStatus.CANCELLED
    })
    OrderStatus orderStatus;
}
