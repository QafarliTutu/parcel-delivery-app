package com.example.msdelivery.controller.v1;

import com.example.msdelivery.beans.CheckRoleHeader;
import com.example.msdelivery.model.request.PageableReq;
import com.example.msdelivery.model.request.delivery.UpdateDeliveryByAdminReq;
import com.example.msdelivery.model.request.delivery.UpdateDeliveryByCourierReq;
import com.example.msdelivery.model.response.DeliveryResp;
import com.example.msdelivery.repository.entity.Delivery;
import com.example.msdelivery.service.delivery.DeliveryService;
import com.example.msdelivery.util.response.ServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Operation(summary = "Courier can only view his/her all assigned deliveries.")
    @ApiResponse(description = "View all assigned deliveries.")
    @CheckRoleHeader(
            headerName = "roles",
            headerValue = {"COURIER"})
    @PostMapping("/courier/view")
    public ResponseEntity<ServiceResponse<Page<DeliveryResp>>> getAllCourierDeliveries(
            @RequestBody @Valid PageableReq pageableReq,
            @RequestHeader("userID") Long userID) {

        return new ResponseEntity<>(
                ServiceResponse.success(deliveryService
                        .getAllCourierDeliveries(pageableReq, userID)),
                HttpStatus.OK);
    }

    @Operation(summary = "Courier can only view his/her assigned delivery.")
    @ApiResponse(description = "View assigned delivery.")
    @CheckRoleHeader(
            headerName = "roles",
            headerValue = {"COURIER"})
    @PostMapping("/courier/view/{deliveryId}")
    public ResponseEntity<ServiceResponse<DeliveryResp>> getCourierDelivery(
            @PathVariable Long deliveryId,
            @RequestHeader("userID") Long userID) {

        return new ResponseEntity<>(
                ServiceResponse.success(deliveryService
                        .getCourierDelivery(userID, deliveryId)),
                HttpStatus.OK);
    }

    @Operation(summary = "Courier can only update status of delivery.")
    @ApiResponse(description = "Updated delivery.")
    @CheckRoleHeader(
            headerName = "roles",
            headerValue = {"COURIER"})
    @PatchMapping("/courier/view/{deliveryId}")
    public ResponseEntity<ServiceResponse<Void>> updateDeliveryByCourier(
            @RequestBody @Valid UpdateDeliveryByCourierReq updateDeliveryByCourierReq,
            @PathVariable Long deliveryId,
            @RequestHeader("userID") Long userID) {

        deliveryService.updateDeliveryByCourier(updateDeliveryByCourierReq, userID, deliveryId);
        return new ResponseEntity<>(
                ServiceResponse.success(),
                HttpStatus.OK);
    }

    @Operation(summary = "Admin can only update status of delivery.")
    @ApiResponse(description = "Updated delivery.")
    @CheckRoleHeader(
            headerName = "roles",
            headerValue = {"ADMIN"})
    @PatchMapping("/admin/view/{deliveryId}")
    public ResponseEntity<ServiceResponse<Void>> updateDeliveryByAdmin(
            @RequestBody @Valid UpdateDeliveryByAdminReq updateDeliveryByAdminReq,
            @PathVariable Long deliveryId) {

        deliveryService.updateDeliveryByAdmin(updateDeliveryByAdminReq, deliveryId);
        return new ResponseEntity<>(
                ServiceResponse.success(),
                HttpStatus.OK);
    }

    @Operation(summary = "Only Admin can view all deliveries.")
    @ApiResponse(description = "View all deliveries.")
    @CheckRoleHeader(
            headerName = "roles",
            headerValue = {"ADMIN"})
    @PostMapping("/admin/view")
    public ResponseEntity<ServiceResponse<Page<Delivery>>> getAllDelivery(
            @RequestBody @Valid PageableReq pageableReq) {

        return new ResponseEntity<>(
                ServiceResponse.success(deliveryService
                        .getAllDeliveries(pageableReq)),
                HttpStatus.OK);
    }

}
