package org.myhealth.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.myhealth.DAO.User_DataOperation;
import org.myhealth.model.User;
import org.myhealth.security.HashedPassword;

public class Signup_Controller {

    @FXML
    private TextField username_Field;

    @FXML
    private PasswordField password_Field;

    @FXML
    private TextField firstName_Field;

    @FXML
    private TextField lastName_Field;

    @FXML
    private Label message_Label;

    private final User_DataOperation user_Data = new User_DataOperation();

    // Opens Login page when user clicks Login button
    @FXML
    private void openLoginPage() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/myhealth/view/login.fxml"));

            Scene scene = new Scene(loader.load(), 800, 600);

            Stage stage = (Stage) username_Field.getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {

            System.out.println(Login_Controller.ConsoleColor.RED +
                    "Login page could not be loaded: " + e +
                    Login_Controller.ConsoleColor.RESET);
        }
    }

    // Runs when Sign Up button is clicked
    @FXML
    private void handleSignup() {
        String username = username_Field.getText();
        String password = password_Field.getText();
        String firstName = firstName_Field.getText();
        String lastName = lastName_Field.getText();

        // Makes sure user fills every field
        if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {

            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText("Please fill all fields.");
            return;
        }

        // Checks password rules before saving user
        if (!Password_Validator.isValidPassword(password)) {

            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText("Password must be 8+ chars, uppercase, number and special char.");
            return;
        }

        // Checks if username is already taken
        if (user_Data.usernameExists(username)) {

            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText("Username already exists. Choose another.");
            return;
        }

        String hashedPassword = HashedPassword.hashPassword(password);
        User user = new User(username, hashedPassword, firstName, lastName);

        // Saves user into database
        if (user_Data.registerUser(user)) {

            message_Label.setStyle("-fx-text-fill: green;");
            message_Label.setText("Signup successful!");
        } else {

            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText("Signup failed.");
        }
    }
}
