package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import utils.EmailValidator;

public class EmailValidatorTest {

    @Test
    public void testValidEmails() {
        Assert.assertTrue(EmailValidator.isValid("test@example.com"));
        Assert.assertTrue(EmailValidator.isValid("user.name+tag+sorting@example.com"));
    }

    @Test
    public void testInvalidEmails() {
        Assert.assertFalse(EmailValidator.isValid(null));
        Assert.assertFalse(EmailValidator.isValid(""));
        Assert.assertFalse(EmailValidator.isValid("abc.com"));
        Assert.assertFalse(EmailValidator.isValid("@mail.com"));
        Assert.assertFalse(EmailValidator.isValid("test@.com"));
    }
}
