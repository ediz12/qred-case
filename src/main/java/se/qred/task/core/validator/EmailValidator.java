package se.qred.task.core.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailValidation, String> {

    @Override
    public void initialize(EmailValidation constraintAnnotation) {
        // No initialization so far
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("^\\S+@{1}\\S+\\.{1}\\S+$");
    }
}
