package com.example.msuser.security;

import com.example.msuser.util.enums.type.RoleType;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtPayload {

    String userId;

    List<RoleType> roles;
}
