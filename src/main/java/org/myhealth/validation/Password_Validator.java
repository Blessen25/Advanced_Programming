package org.myhealth.validation;

public class Password_Validator {

    // To Check if password is valid.
    public static boolean isValidPassword(String password) {
        // Password must be at least 8 characters long
        if (password.length() < 8) {
            return false;
        }
        // Checks for at least one uppercase letter
        boolean hasUppercase = password.matches(".*[A-Z].*");
        // Checks for at least one letter
        boolean hasLetter = password.matches(".*[a-zA-Z].*");
        // Checks for at least one number
        boolean hasNumber = password.matches(".*[0-9].*");
        // Checks for at least one special character
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+=\\-{};':\"\\\\|,.<>/?].*");

        // Password is valid only if all rules are true
        return hasUppercase && hasLetter && hasNumber && hasSpecial;
    }
}
