package com.example.msdelivery.rabbit;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RabbitMessage implements Serializable {

    Object payload;

    Operation operation;

    public enum Operation {

        CREATE_COURIER,
        CREATE_DELIVERY,
        UPDATE_DELIVERY,
        UPDATE_ORDER
    }

}
