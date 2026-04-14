package com.github.GaskaPiotr.spring_boot_boilerplate.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordConstraintValidator implements ConstraintValidator<StrongPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        if (password == null) {
            return false;
        }

        boolean isValid = true;

        if (password.length() < 8) {
            context.buildConstraintViolationWithTemplate("Must be at lest 8 characters long")
                    .addConstraintViolation();
            isValid = false;
        }

        if (!password.matches(".*[A-Z].*")) {
            context.buildConstraintViolationWithTemplate("Must contain at least one uppercase letter")
                    .addConstraintViolation();
            isValid = false;
        }

        if (!password.matches(".*[a-z].*")) {
            context.buildConstraintViolationWithTemplate("Must contain at least one lowercase letter")
                    .addConstraintViolation();
            isValid = false;
        }

        if (!password.matches(".*\\d.*")) {
            context.buildConstraintViolationWithTemplate("Must contain at least one digit")
                    .addConstraintViolation();
            isValid = false;
        }


        if (!isValid) {
            context.disableDefaultConstraintViolation();
        }

        return isValid;
    }
}
