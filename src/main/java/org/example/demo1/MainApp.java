package org.example.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        showLoginPage(); // Αρχικό άνοιγμα της Login Page
    }

    // Μέθοδος για την εμφάνιση της Login Page
    public void showLoginPage() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

        // Λήψη του controller και σύνδεση με την κεντρική εφαρμογή
        LoginController loginController = loader.getController();
        loginController.setMainApp(this);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Login Page");
        primaryStage.show();
    }

    // Μέθοδος για την εμφάνιση της Create Account Page
    public void showCreateAccountPage() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Create_Account.fxml"));
        Parent root = loader.load();

        CreateAccountController createAccountController = loader.getController();
        createAccountController.setMainApp(this);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Create Account Page");
        primaryStage.show();
    }

    // Μέθοδος για την εμφάνιση της Music Player Page
    public void showMusicPlayerPage() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MusicPlayer.fxml"));
        Parent root = loader.load();

        MusicPlayerController musicPlayerController = loader.getController();
        musicPlayerController.setMainApp();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Music Player");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
