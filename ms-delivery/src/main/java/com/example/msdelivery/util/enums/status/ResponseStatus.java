package com.example.msdelivery.util.enums.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

    ok(0, "processed successfully"),
    fail(1, "processing failed");

    private final int code;

    private final String message;

}

