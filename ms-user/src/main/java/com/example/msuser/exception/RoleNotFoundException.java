package com.example.msuser.exception;


import static com.example.msuser.util.response.ResponseCode.ROLE_NOT_FOUND;

import com.example.msuser.util.enums.type.RoleType;

public class RoleNotFoundException extends CustomException {

    public RoleNotFoundException(RoleType roleType) {
        super(ROLE_NOT_FOUND, roleType.name());
    }
}
