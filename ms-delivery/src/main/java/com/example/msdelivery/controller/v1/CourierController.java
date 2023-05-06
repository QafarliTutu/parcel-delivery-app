package com.example.msdelivery.controller.v1;

import com.example.msdelivery.beans.CheckRoleHeader;
import com.example.msdelivery.model.request.PageableReq;
import com.example.msdelivery.repository.entity.Courier;
import com.example.msdelivery.service.courier.CourierService;
import com.example.msdelivery.util.response.ServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
@RequestMapping("/v1/couriers")
public class CourierController {

    private final CourierService courierService;

    @Operation(summary = "Only Admin can view all couriers.")
    @ApiResponse(description = "View all couriers.")
    @CheckRoleHeader(
            headerName = "roles",
            headerValue = {"ADMIN"}
    )
    @PostMapping("/view")
    public ResponseEntity<ServiceResponse<Page<Courier>>> getAllCourier(
            @RequestBody @Valid PageableReq pageableReq) {

        return new ResponseEntity<>(
                ServiceResponse.success(courierService.getAllCouriers(pageableReq)),
                HttpStatus.OK);
    }

}
