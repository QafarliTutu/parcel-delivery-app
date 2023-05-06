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
public class UpdateDeliveryReq {

    @NotNull(message = "deliveryId {javax.validation.constraints.NotNull.message}")
    Long deliveryId;

    String deliveryAddress;

    OrderStatus orderStatus;

    RoleType statusUpdatedBy;

    RoleType addressUpdatedBy;

}
