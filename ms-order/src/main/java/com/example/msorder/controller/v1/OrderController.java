package com.example.msorder.controller.v1;

import com.example.msorder.beans.CheckRoleHeader;
import com.example.msorder.model.request.CreateOrderReq;
import com.example.msorder.model.request.PageableReq;
import com.example.msorder.model.request.UpdateByAdminReq;
import com.example.msorder.model.request.UpdateByUserReq;
import com.example.msorder.model.response.OrderResp;
import com.example.msorder.repository.entity.Order;
import com.example.msorder.service.order.OrderService;
import com.example.msorder.util.response.ServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Only User can create parcel delivery order.")
    @ApiResponse(description = "Created parcel delivery order.")
    @CheckRoleHeader(
            headerName = "roles",
            headerValue = {"USER"})
    @PostMapping
    public ResponseEntity<ServiceResponse<Void>> createOrder(
            @RequestBody @Valid CreateOrderReq createOrderReq,
            @RequestHeader("userID") Long userID) {

        orderService.createOrder(createOrderReq, userID);
        return new ResponseEntity<>(
                ServiceResponse.success(),
                HttpStatus.CREATED);
    }

    @Operation(summary = "User can only update address and status (cancelling) of parcel delivery order.")
    @ApiResponse(description = "Updated parcel delivery order.")
    @CheckRoleHeader(
            headerName = "roles",
            headerValue = {"USER"})
    @PatchMapping("/user/view/{orderId}")
    public ResponseEntity<ServiceResponse<Void>> updateOrderByUser(
            @RequestBody @Valid UpdateByUserReq updateByUserReq,
            @PathVariable Long orderId,
            @RequestHeader("userID") Long userID) {

        orderService.updateOrderByUser(updateByUserReq, orderId, userID);
        return new ResponseEntity<>(
                ServiceResponse.success(),
                HttpStatus.OK);
    }

    @Operation(summary = "User can only view  his/her parcel delivery order.")
    @ApiResponse(description = "View user parcel delivery order.")
    @CheckRoleHeader(
            headerName = "roles",
            headerValue = {"USER"})
    @GetMapping("/user/view/{orderId}")
    public ResponseEntity<ServiceResponse<OrderResp>> getUserOrder(
            @PathVariable Long orderId,
            @RequestHeader("userID") Long userID) {

        return new ResponseEntity<>(
                ServiceResponse.success(orderService
                        .getUserOrder(userID, orderId)),
                HttpStatus.OK);
    }

    @Operation(summary = "Only User can view his/her all parcel delivery orders.")
    @ApiResponse(description = "View all user parcel delivery orders.")
    @CheckRoleHeader(
            headerName = "roles",
            headerValue = {"USER"})
    @PostMapping("/user/view")
    public ResponseEntity<ServiceResponse<Page<OrderResp>>> getAllUserOrders(
            @RequestBody @Valid PageableReq pageableReq,
            @RequestHeader("userID") Long userID) {

        return new ResponseEntity<>(
                ServiceResponse.success(orderService
                        .getAllUserOrders(pageableReq, userID)),
                HttpStatus.OK);
    }

    @Operation(summary = "Admin can only update status of parcel delivery order.")
    @ApiResponse(description = "Updated parcel delivery order.")
    @CheckRoleHeader(
            headerName = "roles",
            headerValue = {"ADMIN"})
    @PatchMapping("/admin/view/{orderId}")
    public ResponseEntity<ServiceResponse<Void>> updateOrderByAdmin(
            @RequestBody @Valid UpdateByAdminReq updateByAdminReq,
            @PathVariable Long orderId) {

        orderService.updateOrderByAdmin(updateByAdminReq, orderId);
        return new ResponseEntity<>(
                ServiceResponse.success(),
                HttpStatus.OK);
    }

    @Operation(summary = "Only Admin can view all parcel delivery orders.")
    @ApiResponse(description = "View all parcel delivery orders.")
    @CheckRoleHeader(
            headerName = "roles",
            headerValue = {"ADMIN"})
    @PostMapping("/admin/view")
    public ResponseEntity<ServiceResponse<Page<Order>>> getAllOrders(
            @RequestBody @Valid PageableReq pageableReq) {

        return new ResponseEntity<>(
                ServiceResponse.success(orderService
                        .getAllOrders(pageableReq)),
                HttpStatus.OK);
    }

}
