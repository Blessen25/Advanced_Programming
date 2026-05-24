package org.myhealth.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.myhealth.model.User;
import javafx.scene.control.TextField;
import org.myhealth.dboperations.User_DataOperation;
import org.myhealth.model.User;

public class Dashboard_Controller {

    // Welcome label for dashboard
    @FXML
    private Label welcome_label;

    // Text field for editing first name
    @FXML
    private TextField firstName_Field;

    // Text field for editing last name
    @FXML
    private TextField lastName_Field;

    // Displays success/error messages
    @FXML
    private Label message_Label;

    // To Store currently logged-in user
    private User currentUser;

    // Object used to access database methods
    private final User_DataOperation userData = new User_DataOperation();

    // Receives logged-in user details from Login_Controller
    public void setUser(User user){

        // Saves logged-in user.
        this.currentUser = user;

        // Display Welcome Message.
        welcome_label.setText(
                "Welcome," + user.getFirst_Name()+ " " + user.getLast_Name()
        );

        // Displays current first name
        firstName_Field.setText(
                user.getFirst_Name()
        );

        // Displays current last name
        lastName_Field.setText(
                user.getLast_Name()
        );
    }

    /* Runs when user clicks:"Save Profile Changes" */
    @FXML
    private void handleUpdateProfile() {

        // Gets updated names from UI
        String newFirstName = firstName_Field.getText();

        String newLastName = lastName_Field.getText();

        // Prevents empty profile fields
        if (newFirstName.isEmpty() || newLastName.isEmpty()) {

            message_Label.setStyle("-fx-text-fill: red;");

            message_Label.setText("First name and last name cannot be empty.");
            return;
        }

        boolean updated = userData.updateProfile(
                currentUser.getID(),
                newFirstName,
                newLastName);

        // if update successful
        if (updated) {

            currentUser.setFirst_Name(
                    newFirstName
            );

            currentUser.setLast_Name(
                    newLastName
            );

            // Updates welcome message immediately
            welcome_label.setText(
                    "Welcome, " +
                            newFirstName +
                            " " +
                            newLastName
            );

            // Displays success message
            message_Label.setStyle("-fx-text-fill: green;");
            message_Label.setText("Profile updated successfully.");
        } else {

            // Displays error message
            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText("Profile update failed.");
        }
    }
}
