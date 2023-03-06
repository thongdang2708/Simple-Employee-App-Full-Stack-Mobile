package com.ltp.backend.validator;

import java.util.Date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckDateValidator implements ConstraintValidator<CheckDate, Date> {
    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {

        long thisDate = new Date().getTime();
        long thatDate = value.getTime();

        System.out.println(thisDate);
        System.out.println(thatDate);
        return thatDate <= thisDate;
    }
}
