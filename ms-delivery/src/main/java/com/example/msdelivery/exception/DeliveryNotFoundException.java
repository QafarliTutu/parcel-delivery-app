package com.example.msdelivery.exception;

import static com.example.msdelivery.util.response.ResponseCode.DELIVERY_NOT_FOUND;

public class DeliveryNotFoundException extends CustomException{

    public DeliveryNotFoundException() {
        super(DELIVERY_NOT_FOUND);
    }
}
