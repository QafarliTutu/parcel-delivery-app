package com.example.msuser.exception;


import static com.example.msuser.util.response.ResponseCode.INVALID_TOKEN;

public class InvalidTokenException extends CustomException {

    public InvalidTokenException() {
        super(INVALID_TOKEN);
    }
}
