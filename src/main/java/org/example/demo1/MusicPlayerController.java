package org.example.demo1;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class MusicPlayerController {

    @FXML
    public Button artist, playlist, mood, settings, love, home;
    @FXML
    public AnchorPane FrameMusicPlayer;
    @FXML
    private TextField searchBar;
    @FXML
    private ListView<String> resultsList;

    @FXML
    public void initialize() {
        // Ensure ListView is interactive
        resultsList.setVisible(false); // Initially hidden
        resultsList.setOnMouseClicked(this::handleListClick); // Attach click listener

        // Add an event filter to hide the ListView when clicking outside it
        FrameMusicPlayer.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (!resultsList.isHover()) { // If the click is outside the ListView
                resultsList.setVisible(false);
            }
        });

        // Add focus listener to hide the ListView when it loses focus
        resultsList.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // If the ListView loses focus
                resultsList.setVisible(false); // Hide the ListView
            }
        });
    }

    @FXML
    private void handleSearch() {
        String query = searchBar.getText().trim();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Search query cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Perform the YouTube search in a separate thread
        new Thread(() -> {
            try {
                List<String> videoTitles = ApiExample.searchYouTube(query);

                Platform.runLater(() -> {
                    resultsList.getItems().clear(); // Clear old items
                    if (videoTitles.isEmpty()) {
                        resultsList.setVisible(false); // Hide if no results
                    } else {
                        resultsList.getItems().addAll(videoTitles); // Add new items
                        resultsList.setVisible(true); // Show the list
                        resultsList.requestFocus(); // Ensure focus is on the ListView
                    }
                });
            } catch (GeneralSecurityException | IOException e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                });
            }
        }).start();
    }

    private void handleListClick(MouseEvent event) {
        if (event.getClickCount() == 1) { // Single-click to select an item
            String selectedVideo = resultsList.getSelectionModel().getSelectedItem();
            if (selectedVideo != null) {
                //JOptionPane.showMessageDialog(null, "Selected video: " + selectedVideo, "Video Selected", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Selected video: " + selectedVideo);
            }
        }
    }

    public void setMainApp() {
        System.out.println("Music Player initialized!");
    }
}
