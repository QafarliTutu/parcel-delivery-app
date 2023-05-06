package com.example.msorder.exception;

import static com.example.msorder.util.response.ResponseCode.ORDER_NOT_FOUND;

public class OrderNotFoundException extends CustomException{

    public OrderNotFoundException() {
        super(ORDER_NOT_FOUND);
    }
}
