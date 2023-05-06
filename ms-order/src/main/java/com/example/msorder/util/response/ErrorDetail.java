package com.example.msorder.util.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {

    String invalidField;
    String message;

    public static ErrorDetail of(String field, String message) {
        return ErrorDetail
                .builder()
                .invalidField(field)
                .message(message)
                .build();
    }

    public static ErrorDetail of(String message) {
        return ErrorDetail
                .builder()
                .message(message)
                .build();
    }
}
