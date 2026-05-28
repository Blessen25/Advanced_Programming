package org.myhealth.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.myhealth.DAO.Health_Record_Operations;
import org.myhealth.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Dashboard_Controller {

    private final Health_Record_Operations healthRecordData = new Health_Record_Operations();

    @FXML
    private Label recordCount_Label;

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
        welcome_label.setText("Welcome, " + user.getFirst_Name() + " " + user.getLast_Name());

        int totalRecords = healthRecordData.getRecordCount(user.getID());
        recordCount_Label.setText("You currently have " + totalRecords + " health record(s).");
    }

    @FXML
    private void openEditProfile() {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/myhealth/view/edit_profile.fxml")
            );

            Scene scene = new Scene(loader.load(), 800, 600);

            Edit_Profile_Controller controller = loader.getController();
            controller.setUser(currentUser);

            Stage stage = (Stage) welcome_label.getScene().getWindow();
            stage.setTitle("Edit Profile");
            stage.setScene(scene);

        } catch (Exception e) {
            System.out.println("Edit profile page could not be loaded: " + e);
        }
    }

    @FXML
    private void openHealthRecords() {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/myhealth/view/health_records.fxml")
            );

            Scene scene = new Scene(loader.load(), 800, 600);

            HealthRecords_Controller controller = loader.getController();
            controller.setUser(currentUser);

            Stage stage = (Stage) welcome_label.getScene().getWindow();
            stage.setTitle("Health Records");
            stage.setScene(scene);

        } catch (Exception e) {
            System.out.println("Health records page could not be loaded: " + e);
        }
    }

    // Logout option.
    @FXML
    private void handleLogout() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/myhealth/view/login.fxml")
            );

            Scene scene = new Scene(loader.load(), 800, 600);

            // Gets current window
            Stage stage = (Stage) welcome_label.getScene().getWindow();

            // Removes reference to current user
            currentUser = null;

            stage.setTitle("MyHealth Login");
            stage.setScene(scene);

        } catch (Exception e) {

            System.out.println(
                    "Login page could not be loaded: " + e
            );
        }
    }

}
