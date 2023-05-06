package com.example.msorder.util.response;

import static com.example.msorder.util.enums.status.ResponseStatus.fail;
import static com.example.msorder.util.enums.status.ResponseStatus.ok;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.example.msorder.util.enums.status.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@JsonInclude(NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse<T> {

    private ResponseStatus status;
    private Integer responseCode;
    private String error;
    private List<ErrorDetail> errorDetails;
    private T data;

    public static ServiceResponse<Void> success() {
        return ServiceResponse.<Void>builder()
                .status(ok)
                .build();
    }

    public static <T> ServiceResponse<T> success(T data) {
        return ServiceResponse.<T>builder()
                .status(ok)
                .data(data)
                .build();
    }

    public static ServiceResponse<Void> error(ResponseCode responseCode,
                                              String message) {
        return ServiceResponse.<Void>builder()
                .status(fail)
                .responseCode(responseCode.getCode())
                .error(responseCode.name())
                .errorDetails(List.of(ErrorDetail.of(message)))
                .build();
    }

    public static ServiceResponse<Void> error(ResponseCode responseCode,
                                              List<ErrorDetail> errorDetails) {
        return ServiceResponse.<Void>builder()
                .status(fail)
                .responseCode(responseCode.getCode())
                .error(responseCode.name())
                .errorDetails(errorDetails)
                .build();
    }

    public static ServiceResponse<Void> error(String error,
                                              Integer errorCode,
                                              List<ErrorDetail> errorDetails) {
        return ServiceResponse.<Void>builder()
                .status(fail)
                .responseCode(errorCode)
                .error(error)
                .errorDetails(errorDetails)
                .build();
    }
}
