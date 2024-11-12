package org.example.demo1;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class  CreateAccountApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(CreateAccountApplication.class.getResource("Create_Account.fxml"));

        // Φόρτωση του FXML χωρίς ορισμό αρχικού πλάτους και ύψους
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        stage.setTitle("CreateAccount Page");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}