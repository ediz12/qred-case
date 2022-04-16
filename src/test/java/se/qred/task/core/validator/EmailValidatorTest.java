package se.qred.task.core.validator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import se.qred.task.base.BaseMockitoTest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EmailValidatorTest extends BaseMockitoTest {

    @Mock
    private EmailValidation emailValidation;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    EmailValidator emailValidator;

    @Before
    public void setUp() throws Exception {
        emailValidator = new EmailValidator();
        emailValidator.initialize(emailValidation);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isValidEmail() {
        boolean valid = emailValidator.isValid("test@test.com", constraintValidatorContext);
        assertTrue(valid);

        valid = emailValidator.isValid("test@more.co.uk", constraintValidatorContext);
        assertTrue(valid);
    }

    @Test
    public void isNotValidEmail() {
        boolean valid = emailValidator.isValid("test@test", constraintValidatorContext);
        assertFalse(valid);

        valid = emailValidator.isValid("test", constraintValidatorContext);
        assertFalse(valid);
    }
}