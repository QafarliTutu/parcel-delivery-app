package com.example.msdelivery.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageableReq {

    @NotNull(message = "page {javax.validation.constraints.NotNull.message}")
    @Min(0)
    private Integer page;

    @Builder.Default
    private Integer size = 10;

}
