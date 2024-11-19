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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class MusicPlayerController {

    @FXML
    public Button artist, playlist, mood, settings, love, home;
    @FXML
    public AnchorPane FrameMusicPlayer;
    @FXML
    private TextField searchBar; // Input field for search text.
    @FXML
    private ListView<String> resultsList; // ListView to display search results.

    // Initialize trackMap as an empty HashMap
    private Map<String, String> trackMap = new HashMap<>();

    @FXML
    public void initialize() {
        // Ensure ListView is interactive
        resultsList.setVisible(false); // Initially hidden
        resultsList.setOnMouseClicked(this::handleListClick); // Attach click listener

        // Fetch trending tracks when the application starts
        fetchTrendingTracks();
    }

    private void fetchTrendingTracks() {
        // Fetch trending tracks in a separate thread to avoid blocking the UI
        new Thread(() -> {
            try {
                // Define the API URL for trending tracks
                String apiUrl = "https://discoveryprovider.audius.co/v1/tracks/search"; // Ensure this is correct
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");

                // Get the HTTP response code
                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {  // 200 OK
                    Platform.runLater(() -> {
                        JOptionPane.showMessageDialog(null, "Error: Received HTTP " + responseCode + " from Audius API.", "Error", JOptionPane.ERROR_MESSAGE);
                    });
                    return;
                }

                // Read the API response
                Scanner scanner = new Scanner(url.openStream());
                StringBuilder jsonBuilder = new StringBuilder();
                while (scanner.hasNext()) {
                    jsonBuilder.append(scanner.nextLine());
                }
                scanner.close();

                // Log the raw response to debug
                String response = jsonBuilder.toString();
                System.out.println("Raw response: " + response);

                // Check if the response is valid JSON
                if (response.trim().startsWith("{")) {
                    // Parse the JSON response
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray tracks = jsonResponse.getJSONArray("data");

                    // Update UI with the fetched tracks
                    Platform.runLater(() -> {
                        trackMap.clear(); // Clear previous tracks
                        for (int i = 0; i < tracks.length(); i++) {
                            JSONObject track = tracks.getJSONObject(i);
                            String title = track.getString("title");

                            // Inspect and log the fields related to URLs
                            System.out.println("Track Data: " + track.toString());

                            // Try to extract streaming URL (Look for a possible field like stream_url, permalink, or url)
                            String streamingUrl = track.optString("permalink"); // Try 'permalink'
                            if (streamingUrl != null && !streamingUrl.isEmpty()) {
                                streamingUrl = "https://audius.co" + streamingUrl; // Complete the URL if permalink exists
                            } else {
                                // If no permalink, check if there is another URL field like stream_url
                                streamingUrl = track.optString("url"); // Fallback to track URL if available
                            }

                            // In case 'permalink' is still missing, check if a different field is available for the actual streaming URL
                            if (streamingUrl == null || streamingUrl.isEmpty()) {
                                streamingUrl = track.optString("stream_url"); // Check if a field like 'stream_url' is available
                            }

                            // Log streaming URL for debugging
                            System.out.println("Extracted Streaming URL: " + streamingUrl);

                            // Handle case where no streaming URL is available
                            if (streamingUrl == null || streamingUrl.isEmpty()) {
                                System.out.println("Error: Streaming URL not available for track: " + title);
                                continue; // Skip this track if no URL is available
                            }

                            // Get artwork URL and user information
                            String artworkUrl = track.getJSONObject("artwork").optString("150x150"); // Get artwork image (use 150x150)
                            String userName = track.getJSONObject("user").getString("name"); // Get artist name

                            // Map track title to URL
                            trackMap.put(title, streamingUrl); // You can modify this to store more info like artwork URL and user name

                            // Display track info on ListView (adjust display as needed)
                            resultsList.getItems().add(title + " by " + userName); // Example: Adding title and artist
                        }
                        resultsList.setVisible(true); // Make the ListView visible
                    });
                } else {
                    // Handle invalid response
                    Platform.runLater(() -> {
                        JOptionPane.showMessageDialog(null, "Error: Invalid response from API. Response was not JSON.", "Error", JOptionPane.ERROR_MESSAGE);
                    });
                }
            } catch (UnknownHostException e) {
                Platform.runLater(() -> {
                    JOptionPane.showMessageDialog(null, "Network Error: Unable to resolve the server (UnknownHostException). Check your internet connection.", "Error", JOptionPane.ERROR_MESSAGE);
                });
            } catch (MalformedURLException e) {
                Platform.runLater(() -> {
                    JOptionPane.showMessageDialog(null, "Error: The URL provided is malformed.", "Error", JOptionPane.ERROR_MESSAGE);
                });
            } catch (IOException e) {
                Platform.runLater(() -> {
                    JOptionPane.showMessageDialog(null, "Error fetching trending tracks: IO issue occurred.", "Error", JOptionPane.ERROR_MESSAGE);
                });
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    JOptionPane.showMessageDialog(null, "Error fetching trending tracks: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                });
            }
        }).start();
    }

    // Method to handle search functionality
    @FXML
    private void handleSearch() {
        String query = searchBar.getText(); // Get the text entered by the user.

        if (query == null || query.isEmpty()) {
            resultsList.getItems().clear();
            resultsList.getItems().add("Please enter a search term.");
            return;
        }

        // Clear old search results
        resultsList.getItems().clear();

        // Fetch search results in a separate thread
        new Thread(() -> {
            try {
                // Audius API endpoint for search
                String apiUrl = "https://discoveryprovider.audius.co/v1/tracks/search?query=" + query;

                // Connect to the API
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");

                // Read the API response
                Scanner scanner = new Scanner(url.openStream());
                StringBuilder jsonBuilder = new StringBuilder();
                while (scanner.hasNext()) {
                    jsonBuilder.append(scanner.nextLine());
                }
                scanner.close();

                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(jsonBuilder.toString());
                JSONArray tracks = jsonResponse.getJSONArray("data");

                // Update UI with the search results
                Platform.runLater(() -> {
                    trackMap.clear(); // Clear previous track map
                    for (int i = 0; i < tracks.length(); i++) {
                        JSONObject track = tracks.getJSONObject(i);
                        String title = track.getString("title");
                        String streamingUrl = track.getString("url"); // Assuming URL is the streaming link
                        trackMap.put(title, streamingUrl); // Map track title to its URL
                    }
                    resultsList.getItems().addAll(trackMap.keySet()); // Update the ListView with track titles
                    resultsList.setVisible(true); // Show the ListView
                });

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    JOptionPane.showMessageDialog(null, "Error searching tracks: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                });
            }
        }).start();
    }
    @FXML
    private void handleListClick(MouseEvent event) {
        if (event.getClickCount() == 1) { // Single-click to select an item
            String selectedTrack = resultsList.getSelectionModel().getSelectedItem();
            if (selectedTrack != null) {
                // Get the streaming URL for the selected track
                final String streamingUrl = trackMap.get(selectedTrack);

                if (streamingUrl != null) {
                    // Ensure the URL has a protocol and make it final
                    final String finalUrl = streamingUrl.startsWith("http://") || streamingUrl.startsWith("https://")
                            ? streamingUrl
                            : "https://" + streamingUrl; // Prepend https:// if missing

                    // Debugging output to ensure the URL is correct
                    System.out.println("URL to play: " + finalUrl);

                    // Try to play the track using AudiusPlayer in a new thread
                    new Thread(() -> {
                        try {
                            AudiusPlayer.playAudioFromUrl(finalUrl); // Play the audio from the URL
                        } catch (Exception e) {
                            // Catch any kind of exception, including those that AudiusPlayer may throw
                            Platform.runLater(() -> {
                                JOptionPane.showMessageDialog(null, "Error: Unable to play the track. The URL may be malformed or an error occurred during playback.", "Error", JOptionPane.ERROR_MESSAGE);
                            });
                        }
                    }).start();
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Streaming URL not available for this track.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

}
