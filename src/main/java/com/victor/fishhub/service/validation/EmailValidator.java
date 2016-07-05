package com.victor.fishhub.service.validation;

import javax.validation.ConstraintValidator;
import com.victor.fishhub.service.validation.annotation.Email;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {

    private static final String EMAIL_REGEXP = "^\\w+(\\.\\w+)*@\\w(\\.\\w+)*\\.\\w{2,6}$";
    private static final Pattern EMAIL_PATTERN;

    static {
        EMAIL_PATTERN = Pattern.compile(EMAIL_REGEXP);
    }

    @Override
    public void initialize(Email constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return EMAIL_PATTERN.matcher(value).matches();
    }

}
