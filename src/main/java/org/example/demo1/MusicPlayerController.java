package org.example.demo1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class MusicPlayerController {

    @FXML
    public Button settings;
    @FXML
    public Button love;
    @FXML
    public Button home;
    @FXML
    public Button playlist;
    @FXML

    public Button list;
    @FXML

    public AnchorPane FrameMusicPlayer;
    @FXML

    public Button mood;
    @FXML

    public Button artist;
    @FXML

    public TextField searchBox;


    private ContextMenu settingsMenu;  // Δημιουργούμε το ContextMenu για τις επιλογές


    @FXML

    public void initialize() {

        // Ρυθμίζουμε τα tooltips για τα κουμπιά

        setTooltipWithDelay(list, "Categories");

        setTooltipWithDelay(mood, "My Mood");

        setTooltipWithDelay(settings, "Settings");

        setTooltipWithDelay(home, "Home");

        setTooltipWithDelay(playlist, "My Playlist");

        setTooltipWithDelay(artist, "My favourite Artists");

        setTooltipWithDelay(love, "My favourite Songs");




// Δημιουργία του ContextMenu
        settingsMenu = new ContextMenu();

        // Δημιουργία του item "Log Out"
        MenuItem logoutItem = new MenuItem("Log Out");
        logoutItem.setOnAction(event -> handleLogoutAction());  // Συνδέουμε την ενέργεια για Log Out

        // Προσθήκη του item στο ContextMenu
        settingsMenu.getItems().add(logoutItem);

        // Όταν πατιέται το κουμπί settings, εμφανίζεται το ContextMenu
        settings.setOnAction(event -> {
            if (!settingsMenu.isShowing()) {
                settingsMenu.show(settings, settings.getLayoutX(), settings.getLayoutY() + settings.getHeight());
            } else {
                settingsMenu.hide();
            }
        });






    }
    // Μέθοδος για τη δημιουργία tooltip με μικρή καθυστέρηση

    private void setTooltipWithDelay(Button button, String text) {

        Tooltip tooltip = new Tooltip(text);



        // Ρυθμίζουμε την καθυστέρηση εμφάνισης και απόκρυψης του tooltip

        tooltip.setShowDelay(javafx.util.Duration.millis(100));  // 100 ms για γρηγορότερη εμφάνιση

        tooltip.setHideDelay(javafx.util.Duration.millis(100));  // 100 ms για γρηγορότερη απόκρυψη



        // Εφαρμόζουμε το tooltip στο κουμπί

        button.setTooltip(tooltip);

    }


/***///
// Μέθοδος για το logout
@FXML
private void handleLogoutAction() {
    // Κλείνει το παράθυρο του MusicPlayer
    Stage currentStage = (Stage) FrameMusicPlayer.getScene().getWindow();
    currentStage.close();


}


/*****////


    public void setMainApp() {
        // Optional logic to initialize the Music Player
        System.out.println("Music Player initialized!");
    }
}
