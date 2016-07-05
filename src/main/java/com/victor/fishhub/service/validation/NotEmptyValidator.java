package com.victor.fishhub.service.validation;

import com.victor.fishhub.service.validation.annotation.NotEmpty;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyValidator implements ConstraintValidator<NotEmpty, String> {

    @Override
    public void initialize(NotEmpty constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && !"".equals(value)) {
            return true;
        }
        return false;
    }

}
