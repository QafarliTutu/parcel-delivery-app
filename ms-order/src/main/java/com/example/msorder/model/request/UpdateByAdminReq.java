package com.example.msorder.model.request;

import com.example.msorder.beans.AllowedOrderStatus;
import com.example.msorder.beans.Conditional;
import com.example.msorder.util.enums.status.OrderStatus;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Conditional(
        conditionalProperty = "orderStatus", values = {"ASSIGNED"},
        requiredProperties = {"courierId"}
)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateByAdminReq {

    @AllowedOrderStatus(anyOf = {
            OrderStatus.CANCELLED,
            OrderStatus.ASSIGNED,
            OrderStatus.SHIPPED,
            OrderStatus.DELIVERED
    })
    @NotNull(message = "orderStatus {javax.validation.constraints.NotNull.message}")
    OrderStatus orderStatus;

    Long courierId;

}
