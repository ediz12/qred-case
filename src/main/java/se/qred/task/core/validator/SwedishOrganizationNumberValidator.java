package se.qred.task.core.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SwedishOrganizationNumberValidator implements ConstraintValidator<SwedishOrganizationNumberValidation, String> {
    @Override
    public void initialize(SwedishOrganizationNumberValidation constraintAnnotation) {
        // No initialization so far
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("^[\\d]{6}-?[\\d]{4}$");
    }
}
