package com.example.msuser.model.request;

import com.example.msuser.beans.Email;
import com.example.msuser.beans.StrongPassword;
import com.example.msuser.util.enums.type.CourierType;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterCourierReq {

    @Email
    String username;

    @StrongPassword
    String temporaryPassword;

    @NotNull(message = "courierType {javax.validation.constraints.NotNull.message}")
    CourierType courierType;

}
