package com.ltp.backend.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckPasswordValidator implements ConstraintValidator<CheckPassword, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Regex condition:
        // • Should contain at least a capital letter
        // • Should contain at least a small letter
        // • Should contain at least a number
        // • Should contain at least a special character
        // • And minimum length is 8
        // TODO Auto-generated method stub
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*]).{8,}$");
        Matcher matcher = pattern.matcher(value);

        return matcher.matches();
    }
}
