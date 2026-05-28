package org.myhealth.controller;
import javafx.scene.control.Label;
import org.myhealth.DAO.User_DataOperation;
import org.myhealth.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Edit_Profile_Controller {

    @FXML
    private TextField firstName_Field;

    @FXML
    private TextField lastName_Field;

    @FXML
    private Label message_Label;

    private final User_DataOperation userData = new User_DataOperation();

    private User currentUser;

    public void setUser(User user){

        this.currentUser = user;

        firstName_Field.setText(
                user.getFirst_Name()
        );

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

            currentUser.setFirst_Name(newFirstName);
            currentUser.setLast_Name(newLastName);

            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/org/myhealth/view/dashboard.fxml")
                );

                Scene scene = new Scene(loader.load(), 800, 600);

                Dashboard_Controller dashboardController = loader.getController();
                dashboardController.setUser(currentUser);

                Stage stage = (Stage) firstName_Field.getScene().getWindow();
                stage.setTitle("MyHealth Dashboard");
                stage.setScene(scene);

            } catch (Exception e) {
                System.out.println("Dashboard could not be loaded: " + e);
            }
        }
    }
}
