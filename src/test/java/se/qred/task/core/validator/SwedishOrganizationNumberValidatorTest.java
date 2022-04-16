package se.qred.task.core.validator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import se.qred.task.base.BaseMockitoTest;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.*;

public class SwedishOrganizationNumberValidatorTest extends BaseMockitoTest {

    @Mock
    private SwedishOrganizationNumberValidation swedishOrganizationNumberValidation;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private SwedishOrganizationNumberValidator validator;

    @Before
    public void setUp() throws Exception {
        validator = new SwedishOrganizationNumberValidator();
        validator.initialize(swedishOrganizationNumberValidation);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isValidOrganization() {
        boolean valid = validator.isValid("123456-7890", constraintValidatorContext);
        assertTrue(valid);

        valid = validator.isValid("1234567890", constraintValidatorContext);
        assertTrue(valid);
    }

    @Test
    public void isNotValidOrganization() {
        // 9 numbers not allowed
        boolean valid = validator.isValid("123456789", constraintValidatorContext);
        assertFalse(valid);

        // 11 numbers not allowed
        valid = validator.isValid("12345678901", constraintValidatorContext);
        assertFalse(valid);

        // - needs to be 7th character
        valid = validator.isValid("12345-67890", constraintValidatorContext);
        assertFalse(valid);

        valid = validator.isValid("1234567-890", constraintValidatorContext);
        assertFalse(valid);
    }
}