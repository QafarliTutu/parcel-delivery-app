package com.example.msdelivery.exception;

import static com.example.msdelivery.util.response.ResponseCode.COURIER_NOT_FOUND;

public class CourierNotFoundException extends CustomException {

    public CourierNotFoundException() {
        super(COURIER_NOT_FOUND);
    }
}
