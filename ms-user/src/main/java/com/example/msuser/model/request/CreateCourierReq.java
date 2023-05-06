package com.example.msuser.model.request;

import com.example.msuser.util.enums.type.CourierType;
import java.io.Serializable;
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
public class CreateCourierReq implements Serializable {

    Long id;

    String username;

    Boolean isActive;

    CourierType courierType;

}
