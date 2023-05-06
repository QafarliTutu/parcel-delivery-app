package com.example.msdelivery.util.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    SYSTEM_ERROR(0, "Internal System Error."),
    VALIDATION_FAILED(3,"Argument Validation Failed."),
    ACCESS_DENIED(6, "Access Denied For Role."),
    DELIVERY_NOT_FOUND(9,"Delivery Not Found."),
    COURIER_NOT_FOUND(10,"Courier Not Found.");

    private final int code;
    private final String description;

}
