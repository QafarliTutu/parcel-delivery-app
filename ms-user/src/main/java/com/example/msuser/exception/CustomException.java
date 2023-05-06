package com.example.msuser.exception;

import com.example.msuser.util.response.ResponseCode;
import lombok.Data;

@Data
public class CustomException extends RuntimeException {

    private final ResponseCode responseCode;

    public CustomException(ResponseCode responseCode, String parameter) {
        super(responseCode.getDescription().replace("{}", parameter));
        this.responseCode = responseCode;
    }

    public CustomException(ResponseCode responseCode) {
        super(responseCode.getDescription());
        this.responseCode = responseCode;
    }
}
