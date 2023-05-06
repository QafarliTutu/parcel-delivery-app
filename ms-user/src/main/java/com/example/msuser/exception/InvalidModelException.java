package com.example.msuser.exception;

import static com.example.msuser.util.response.ResponseCode.VALIDATION_FAILED;

public class InvalidModelException extends CustomException {

    public InvalidModelException() {
        super(VALIDATION_FAILED);
    }
}
