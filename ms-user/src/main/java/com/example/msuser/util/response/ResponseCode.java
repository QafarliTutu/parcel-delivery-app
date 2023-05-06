package com.example.msuser.util.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    SYSTEM_ERROR(0, "Internal System Error."),
    INVALID_CREDENTIALS(1, "Invalid username or password"),
    INVALID_TOKEN(2, "Unable to validate token."),
    VALIDATION_FAILED(3,"Argument Validation Failed."),
    ROLE_NOT_FOUND(4, "Role not found: {}"),
    USER_ALREADY_EXISTS(5, "User already exists with username: {}");

    private final int code;
    private final String description;

}
