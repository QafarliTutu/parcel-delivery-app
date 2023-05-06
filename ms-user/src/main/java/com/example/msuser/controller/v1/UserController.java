package com.example.msuser.controller.v1;

import com.example.msuser.model.request.RegisterCourierReq;
import com.example.msuser.model.request.RegisterUserReq;
import com.example.msuser.service.courier.CourierService;
import com.example.msuser.service.user.UserService;
import com.example.msuser.util.response.ServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;
    private final CourierService courierService;

    @Operation(summary = "Only User can register new user account.")
    @ApiResponse(description = "Register user account.")
    @PostMapping
    public ResponseEntity<ServiceResponse<Void>> registerUser(
            @RequestBody @Valid RegisterUserReq registerUserReq) {

        userService.registerUser(registerUserReq);
        return new ResponseEntity<>(
                ServiceResponse.success(),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Only Admin can register new courier account.")
    @ApiResponse(description = "Registered courier account.")
    @PostMapping("/courier")
    public ResponseEntity<ServiceResponse<Void>> registerCourier(
            @RequestBody @Valid RegisterCourierReq registerCourierReq) {

        courierService.registerCourier(registerCourierReq);
        return new ResponseEntity<>(
                ServiceResponse.success(),
                HttpStatus.CREATED);
    }
}
