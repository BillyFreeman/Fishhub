package com.victor.fishhub.service.validation;

import com.victor.fishhub.service.auth.dto.RegDTO;
import com.victor.fishhub.service.validation.annotation.PasswordMatch;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object>{

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value.getClass() != RegDTO.class){
            return false;
        }
        RegDTO regDTO = (RegDTO) value;
        return regDTO.getPassword().equals(regDTO.getConfirmPassword());
    }
    
}
