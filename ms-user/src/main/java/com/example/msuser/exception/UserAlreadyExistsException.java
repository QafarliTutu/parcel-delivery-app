package com.example.msuser.exception;

import static com.example.msuser.util.response.ResponseCode.USER_ALREADY_EXISTS;

public class UserAlreadyExistsException extends CustomException {

    public UserAlreadyExistsException(String username) {
        super(USER_ALREADY_EXISTS, username);
    }
}
