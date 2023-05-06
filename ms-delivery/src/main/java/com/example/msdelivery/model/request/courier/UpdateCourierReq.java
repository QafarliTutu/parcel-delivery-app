package com.example.msdelivery.model.request.courier;

import com.example.msdelivery.util.enums.status.CourierStatus;
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
public class UpdateCourierReq {

    @NotNull(message = "courierStatus {javax.validation.constraints.NotNull.message}")
    CourierStatus courierStatus;
}
