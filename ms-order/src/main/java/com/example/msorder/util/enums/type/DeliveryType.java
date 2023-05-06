package com.example.msorder.util.enums.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryType {

    EXPRESS(0),
    OVERNIGHT(1),
    STANDARD(3);

    private final int days;

}
