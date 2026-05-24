package org.myhealth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.myhealth.DAO.DB_connections;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // Creates the users table when the application starts.
        DB_connections.createUserTable();
        try{

        // This is to load the login fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/myhealth/view/login.fxml"));
        Scene scene = new Scene(loader.load(), 400, 300);
        stage.setTitle("MyHealth app Login");
        stage.setScene(scene);
        stage.show();
        } catch (Exception e) {

            // To print error if the fxml is not loaded
            System.out.println(DB_connections.ConsoleColor.RED + "FXML file could not be loaded:" + e + DB_connections.ConsoleColor.RESET);
        }
    }

    public static void main(String[] args) {

        // To launch the application
        launch();
    }
}