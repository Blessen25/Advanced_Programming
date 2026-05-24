package org.myhealth.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.myhealth.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Dashboard_Controller {

    // Welcome label for dashboard
    @FXML
    private Label welcome_label;

    // To Store currently logged-in user
    private User currentUser;

    // Receives logged-in user details from Login_Controller
    public void setUser(User user){

        // Saves logged-in user.
        this.currentUser = user;

        // Display Welcome Message.
        welcome_label.setText(
                "Welcome, " + user.getFirst_Name() + " " + user.getLast_Name()
        );
    }

    @FXML
    private void openEditProfile() {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/myhealth/view/edit_profile.fxml")
            );

            Scene scene = new Scene(loader.load(), 400, 300);

            Edit_Profile_Controller controller = loader.getController();
            controller.setUser(currentUser);

            Stage stage = (Stage) welcome_label.getScene().getWindow();
            stage.setTitle("Edit Profile");
            stage.setScene(scene);

        } catch (Exception e) {
            System.out.println("Edit profile page could not be loaded: " + e);
        }
    }


}
