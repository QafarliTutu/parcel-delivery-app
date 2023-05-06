package com.example.msorder.util.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    SYSTEM_ERROR(0, "Internal System Error."),
    VALIDATION_FAILED(3,"Argument Validation Failed."),
    ACCESS_DENIED(6, "Access Denied For Role."),
    ORDER_NOT_FOUND(7, "Delivery Order Not Found."),
    ALREADY_HAVE_STATUS(8, "Delivery Order Already Have The Same Status.");

    private final int code;
    private final String description;

}
