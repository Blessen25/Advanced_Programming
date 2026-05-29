package org.myhealth.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import org.myhealth.DAO.User_DataOperation;
import org.myhealth.model.User;
import org.myhealth.security.HashedPassword;

public class Change_Password_Controller {

    @FXML
    private PasswordField oldPassword_Field;

    @FXML
    private PasswordField newPassword_Field;

    @FXML
    private PasswordField confirmPassword_Field;

    @FXML
    private Label message_Label;

    private User currentUser;

    private final User_DataOperation userData = new User_DataOperation();

    // Receives logged-in user
    public void setUser(User user) {

        this.currentUser = user;
    }

    // Updates user password
    @FXML
    private void handleChangePassword() {

        String oldPassword = oldPassword_Field.getText();

        String newPassword = newPassword_Field.getText();

        String confirmPassword = confirmPassword_Field.getText();

        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {

            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText("Please fill all fields.");
            return;
        }

        String oldHashedPassword = HashedPassword.hashPassword(

                oldPassword
                );

        if (!oldHashedPassword.equals(currentUser.getPassword())) {

            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText("Current password is incorrect.");
            return;
        }

        if (!Password_Validator.isValidPassword(newPassword)) {

            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText("Password must contain uppercase letter, number and special character."
            );
            return;
        }

        if (!newPassword.equals(confirmPassword)) {

            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText("Passwords do not match."
            );
            return;
        }

        String newHashedPassword =
                HashedPassword.hashPassword(newPassword);

        boolean updated =
                userData.updatePassword(currentUser.getID(), newHashedPassword);

        if (updated) {

            currentUser.setPassword(newHashedPassword);

            message_Label.setStyle("-fx-text-fill: green;");
            message_Label.setText(
                    "Password updated successfully."
            );

        } else {

            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText(
                    "Password update failed."
            );
        }
    }

    // Returns to Edit Profile page
    @FXML
    private void goBackToEditProfile() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/myhealth/view/edit_profile.fxml"));

            Scene scene = new Scene(loader.load(),800,600);

            Edit_Profile_Controller controller = loader.getController();

            controller.setUser(currentUser);

            Stage stage = (Stage) oldPassword_Field.getScene().getWindow();

            stage.setTitle("Edit Profile");

            stage.setScene(scene);

        } catch (Exception e) {

            System.out.println("Edit profile page could not be loaded: " + e);
        }
    }

    }
