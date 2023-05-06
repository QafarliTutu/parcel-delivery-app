package com.example.msorder.exception.handler;

import static com.example.msorder.util.response.ResponseCode.SYSTEM_ERROR;
import static com.example.msorder.util.response.ResponseCode.VALIDATION_FAILED;

import com.example.msorder.exception.CustomException;
import com.example.msorder.exception.OrderNotFoundException;
import com.example.msorder.util.response.ErrorDetail;
import com.example.msorder.util.response.ServiceResponse;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {OrderNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(CustomException ex, WebRequest request) {
        return handleException(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<Object> handleCustomException(CustomException ex, WebRequest request) {
        return handleException(ex, request, HttpStatus.CONFLICT);
    }

    private ResponseEntity<Object> handleException(CustomException ex,
                                                   WebRequest request,
                                                   HttpStatus status) {
        log.error("{} : {}", ex.getClass().getSimpleName(), ex.getMessage());
        return handleExceptionInternal(ex,
                ServiceResponse.error(ex.getResponseCode(), ex.getMessage()),
                new HttpHeaders(),
                status,
                request);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleInternal(Exception ex, WebRequest request) {
        log.error("{} : {}", ex.getClass().getSimpleName(), ex.getMessage());
        return handleExceptionInternal(
                ex, ServiceResponse.error(SYSTEM_ERROR, ex.getMessage()),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {


        var errorDetails = ex.getBindingResult().getAllErrors().stream().map(error -> {
            if (error instanceof FieldError fieldError) {
                return ErrorDetail.of(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ErrorDetail.of(error.getDefaultMessage());
        }).collect(Collectors.toList());
        log.error("{} : {}", ex.getClass().getSimpleName(), ex.getMessage());
        return handleExceptionInternal(
                ex,
                ServiceResponse.error(VALIDATION_FAILED, errorDetails),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request) {
        log.error("{} : {}", ex.getClass().getSimpleName(), ex.getMessage());
        return handleExceptionInternal(
                ex,
                ServiceResponse.error(VALIDATION_FAILED, ex.getMessage()),
                new HttpHeaders(),
                HttpStatus.METHOD_NOT_ALLOWED,
                request);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        log.error("{} : {}", ex.getClass().getSimpleName(), ex.getMessage());
        String message = ex.getMessage();

        if (ex instanceof MissingRequestHeaderException headerException)
            message = headerException.getMessage();

        return handleExceptionInternal(
                ex,
                ServiceResponse.error(VALIDATION_FAILED, message),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }
}
