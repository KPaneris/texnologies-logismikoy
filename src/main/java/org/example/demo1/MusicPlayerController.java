package org.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MusicPlayerController {

    @FXML
    private AnchorPane FrameMusicPlayer;

    @FXML
    private Button mood;

    public void setMainApp() {
        // Optional logic to initialize the Music Player
        System.out.println("Music Player initialized!");
    }
}
