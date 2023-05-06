package com.example.msdelivery.model.request.delivery;

import com.example.msdelivery.util.enums.status.OrderStatus;
import com.example.msdelivery.util.enums.type.RoleType;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
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
public class UpdateDeliveryReq implements Serializable {

    @NotNull(message = "deliveryId {javax.validation.constraints.NotNull.message}")
    Long deliveryId;

    String deliveryAddress;

    OrderStatus orderStatus;

    RoleType statusUpdatedBy;

    RoleType addressUpdatedBy;

}
