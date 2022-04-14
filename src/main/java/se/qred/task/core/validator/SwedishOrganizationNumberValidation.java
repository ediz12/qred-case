package se.qred.task.core.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {SwedishOrganizationNumberValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface SwedishOrganizationNumberValidation {

    String message() default "is not a valid Swedish organization number";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}