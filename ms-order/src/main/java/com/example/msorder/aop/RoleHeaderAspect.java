package com.example.msorder.aop;

import static com.example.msorder.util.response.ResponseCode.ACCESS_DENIED;
import static com.example.msorder.util.response.ResponseCode.VALIDATION_FAILED;

import com.example.msorder.beans.CheckRoleHeader;
import com.example.msorder.util.response.ServiceResponse;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RoleHeaderAspect {

    private final HttpServletRequest request;

    @Around("@annotation(checkRoleHeader)")
    public Object checkHeader(ProceedingJoinPoint joinPoint, CheckRoleHeader checkRoleHeader) throws Throwable {
        var headerName = checkRoleHeader.headerName();
        var headerValue = Arrays.stream(checkRoleHeader.headerValue()).toList();
        var requestHeaderValue = request.getHeader(headerName);

        if (requestHeaderValue != null) {
            var roles = Arrays.stream(requestHeaderValue.split(",")).toList();

            var matched = roles
                    .stream()
                    .anyMatch(headerValue::contains);

            if (matched)
                return joinPoint.proceed();
            else
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(ServiceResponse.error(
                                ACCESS_DENIED,
                                ACCESS_DENIED.getDescription()));
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ServiceResponse.error(
                        VALIDATION_FAILED,
                        VALIDATION_FAILED.getDescription()));
    }


}
