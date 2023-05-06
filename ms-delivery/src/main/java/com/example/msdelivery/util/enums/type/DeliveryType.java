package com.example.msdelivery.util.enums.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryType {

    STANDARD(3),
    EXPRESS(0),
    OVERNIGHT(1);

    private final int days;

}
