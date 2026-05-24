package org.myhealth.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.myhealth.dboperations.User_DataOperation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.myhealth.model.User;


public class Login_Controller {

    public class ConsoleColor {
        public static final String GREEN = "\u001B[32m";
        public static final String RED = "\u001B[31m";
        public static final String RESET = "\u001B[0m";
    }

    @FXML
    private TextField username_Field;

    @FXML
    private PasswordField password_Field;

    @FXML
    private Label message_Label;

    private final User_DataOperation userData = new User_DataOperation();

    // Opens signup page when user clicks signup button
    @FXML
    private void openSignupPage() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/myhealth/signup.fxml"));

            Scene scene = new Scene(loader.load(), 400, 350);

            Stage stage = (Stage) username_Field.getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {

            System.out.println(ConsoleColor.RED +
                    "Signup page could not be loaded: " + e +
                    ConsoleColor.RESET);
        }
    }

    @FXML
    private void handleLogin() {
        String username = username_Field.getText();
        String password = password_Field.getText();

        // If the username is empty it shows an error
        if (username.isEmpty() || password.isEmpty()) {

            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText("Please enter username and password.");
            return;
        }

        // Gets user details from database if username and password match
        User loggedInUser = userData.getUserByLogin(username, password);

        if (loggedInUser != null) {

            try {
                // Loads dashboard page after successful login
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/myhealth/dashboard.fxml"));
                Scene scene = new Scene(loader.load(), 500, 350);

                Dashboard_Controller dashboardController = loader.getController();
                // Sends logged-in user details to dashboard
                dashboardController.setUser(loggedInUser);

                // Gets current window and replaces login scene with dashboard scene
                Stage stage = (Stage) username_Field.getScene().getWindow();
                stage.setTitle("MyHealth Dashboard");
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                message_Label.setStyle("-fx-text-fill: red;");
                message_Label.setText("Dashboard could not be loaded.");
                System.out.println("Dashboard error: " + e);
            }

        } else {
            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText("Invalid username or password.");
        }
    }
}
