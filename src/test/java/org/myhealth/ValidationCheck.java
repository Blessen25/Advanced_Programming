package org.myhealth;
import org.junit.jupiter.api.Test;
import org.myhealth.validation.Password_Validator;
import org.myhealth.security.HashedPassword;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationCheck {

    // Test valid password
    @Test
    public void testValidPassword() {

        assertTrue(Password_Validator.isValidPassword("Aiswarya123!")
        );
    }

    // Test password without uppercase letter
    @Test
    public void testPasswordWithoutUppercase() {

        assertFalse(

                Password_Validator.isValidPassword("christina123!")
        );
    }

    // Test password without special character
    @Test
    public void testPasswordWithoutSpecialCharacter() {

        assertFalse(

                Password_Validator.isValidPassword("Cherian123")
        );
    }

    // Test password hashing is different from original password
    @Test
    public void testPasswordHashIsNotPlainText() {

        String password = "Glen123!";
        String hashedPassword = HashedPassword.hashPassword(password);

        assertNotEquals(
                password,
                hashedPassword
        );
    }

    // Test same password generates same hash value
    @Test
    public void testSamePasswordSameHash() {

        String hash1 = HashedPassword.hashPassword("Binny123!");
        String hash2 = HashedPassword.hashPassword("Binny123!");

        assertEquals(
                hash1,
                hash2
        );
    }

    // Test password shorter than 8 characters
    @Test
    public void testPasswordTooShort() {

        assertFalse(

                Password_Validator.isValidPassword("Ben1!")
        );
    }


}
