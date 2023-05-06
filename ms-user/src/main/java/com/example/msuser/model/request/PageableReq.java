package com.example.msuser.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
