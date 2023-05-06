package com.example.msdelivery.util.validator;

import com.example.msdelivery.beans.AllowedOrderStatus;
import com.example.msdelivery.util.enums.status.OrderStatus;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AllowedOrderStatusValidator implements ConstraintValidator<AllowedOrderStatus, OrderStatus> {

    private List<OrderStatus> allowedValues;

    @Override
    public void initialize(AllowedOrderStatus constraintAnnotation) {
        allowedValues = List.of(constraintAnnotation.anyOf());
    }

    @Override
    public boolean isValid(OrderStatus requestValue, ConstraintValidatorContext context) {
        if (requestValue != null) {
            return allowedValues.contains(requestValue);
        }
        return true;
    }
}
