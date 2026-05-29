package org.myhealth.security;
import java.security.MessageDigest;

public class HashedPassword {

    // Converts password into SHA-256 hashed password
    public static String hashPassword(String password) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] encodedHash = digest.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();

            for (byte b : encodedHash) {

                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();

        } catch (Exception e) {
            throw new RuntimeException("Sorry there was an issue while password hashing, Please Try Again", e);
        }
    }
}
