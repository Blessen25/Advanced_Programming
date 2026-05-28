package org.myhealth.controller;

import javafx.stage.FileChooser;
import org.myhealth.DAO.Health_Record_Operations;
import org.myhealth.model.Health_Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.myhealth.model.User;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class HealthRecords_Controller {

    @FXML
    private TextField weight_Field;

    @FXML
    private TextField temperature_Field;

    @FXML
    private TextField bloodPressure_Field;

    @FXML
    private TextArea note_Area;

    @FXML
    private TableView<Health_Record> records_Table;

    @FXML
    private TableColumn<Health_Record, String> date_Column;

    @FXML
    private TableColumn<Health_Record, String> weight_Column;

    @FXML
    private TableColumn<Health_Record, String> temperature_Column;

    @FXML
    private TableColumn<Health_Record, String> bloodPressure_Column;

    @FXML
    private TableColumn<Health_Record, String> note_Column;

    @FXML
    private Label message_Label;

    // Stores the logged-in user
    private User currentUser;

    // DAO object used for database operations
    private final Health_Record_Operations recordData = new Health_Record_Operations();

    // Stores records displayed in the table
    private final ObservableList<Health_Record> recordList = FXCollections.observableArrayList();

    // Receives logged-in user from dashboard
    public void setUser(User user) {

        this.currentUser = user;
        setupTable();
        loadRecords();
    }

    // Connects table columns to Health_Record getters
    private void setupTable() {

        date_Column.setCellValueFactory(new PropertyValueFactory<>("recordDate"));
        weight_Column.setCellValueFactory(new PropertyValueFactory<>("weight"));
        temperature_Column.setCellValueFactory(new PropertyValueFactory<>("temperature"));
        bloodPressure_Column.setCellValueFactory(new PropertyValueFactory<>("bloodPressure"));
        note_Column.setCellValueFactory(new PropertyValueFactory<>("note"));
    }

    // Loads only the current user's records
    private void loadRecords() {
        recordList.clear();

        List<Health_Record> records = recordData.getRecordsByUserId(currentUser.getID());

        recordList.addAll(records);
        records_Table.setItems(recordList);
    }

    // Adds a new health record
    @FXML
    private void handleAddRecord() {

        String weight = weight_Field.getText().trim();
        String temperature = temperature_Field.getText().trim();
        String bloodPressure = bloodPressure_Field.getText().trim();
        String note = note_Area.getText().trim();

        // At least one field must be completed
        if (weight.isEmpty() && temperature.isEmpty() && bloodPressure.isEmpty() && note.isEmpty()) {
            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText("Please enter at least one health record field.");
            return;
        }

        // Note must not exceed 50 words
        if (!note.isEmpty() && note.split("\\s+").length > 50) {
            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText("Note must not exceed 50 words.");
            return;
        }

        // Note must not exceed 250 characters
        if (note.length() > 250) {
            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText("Note must not exceed 250 characters.");
            return;
        }

        String today = LocalDate.now().toString();

        Health_Record record = new Health_Record(
                currentUser.getID(),
                weight,
                temperature,
                bloodPressure,
                note,
                today
        );

        if (recordData.addRecord(record)) {
            message_Label.setStyle("-fx-text-fill: green;");
            message_Label.setText("Health record added successfully.");

            clearFields();
            loadRecords();

        } else {
            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText("Failed to add health record.");
        }
    }

    // Deletes the selected health record
    @FXML
    private void handleDeleteRecord() {

        Health_Record selectedRecord = records_Table.getSelectionModel().getSelectedItem();

        if (selectedRecord == null) {
            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText("Please select a record to delete.");
            return;
        }

        if (recordData.deleteRecord(selectedRecord.getId())) {
            message_Label.setStyle("-fx-text-fill: green;");
            message_Label.setText("Record deleted successfully.");
            loadRecords();
        } else {
            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText("There have been some errors, Failed to delete record.");
        }
    }

    // Exports all health records into a text file selected by the user
    @FXML
    private void handleExportRecords() {

        // Checks if there are any records to export
        if (recordList.isEmpty()) {

            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText("No records to export.");
            return;
        }

        // Opens a file chooser so user can select save location
        FileChooser fileChooser1 = new FileChooser();

        // Title displayed on file chooser window
        fileChooser1.setTitle("Save Health Records");

        // Allows only text files to be saved
        fileChooser1.getExtensionFilters().add(

                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        // Suggested default file name
        fileChooser1.setInitialFileName("my_health_records.txt");

        // Gets current application window
        Stage stage = (Stage) records_Table.getScene().getWindow();
        File file = fileChooser1.showSaveDialog(stage);

        // User clicked cancel
        if (file == null) {

            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText("Export cancelled.");
            return;
        }

        // Calls DAO method to export records into selected file
        if (recordData.exportRecords(
                recordList,
                file.getAbsolutePath())) {

            message_Label.setStyle("-fx-text-fill: green;");
            message_Label.setText("Records exported successfully.");

        } else {

            message_Label.setStyle("-fx-text-fill: red;");
            message_Label.setText("Export failed.");
        }
    }

    // Goes back to dashboard
    @FXML
    private void goBack_Dashboard() {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/myhealth/view/dashboard.fxml")
            );

            Scene scene = new Scene(loader.load(), 800, 600);

            Dashboard_Controller dashboardController = loader.getController();
            dashboardController.setUser(currentUser);

            Stage stage = (Stage) records_Table.getScene().getWindow();
            stage.setTitle("MyHealth Dashboard");
            stage.setScene(scene);

        } catch (Exception e) {
            System.out.println("Dashboard could not be loaded: " + e);
        }
    }

    // Clears input fields after adding a record
    private void clearFields() {
        weight_Field.clear();
        temperature_Field.clear();
        bloodPressure_Field.clear();
        note_Area.clear();
    }


}
