package com.example.msorder.model.request;

import com.example.msorder.util.enums.status.OrderStatus;
import com.example.msorder.util.enums.type.RoleType;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateOrderReq {

    @NotNull(message = "orderId {javax.validation.constraints.NotNull.message}")
    Long orderId;

    @NotNull(message = "orderStatus {javax.validation.constraints.NotNull.message}")
    OrderStatus orderStatus;

    @NotNull(message = "statusUpdatedBy {javax.validation.constraints.NotNull.message}")
    RoleType statusUpdatedBy;

}
