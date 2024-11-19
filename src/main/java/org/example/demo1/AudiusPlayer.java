package org.example.demo1;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AudiusPlayer {

    private static final String BASE_URL = "https://api.audius.co/v1";

    public static void main(String[] args) {
        try {
            // Example: Track ID
            String trackId = "<iframe src=https://audius.co/embed/track/P9p0A6k?flavor=card width=\"100%\" height=\"480\" allow=\"encrypted-media\" style=\"border: none;\"></iframe>";  // Replace with a valid track ID

            // Construct the streaming URL
            String streamEndpoint = BASE_URL + "/tracks/" + trackId + "/stream";

            // Get the streaming URL
            String streamingUrl = getStreamingUrl(streamEndpoint);

            if (streamingUrl != null) {
                System.out.println("Playing track...");
                playAudioFromUrl(streamingUrl);
            } else {
                System.out.println("Failed to fetch the streaming URL.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Fetch the streaming URL for a track.
     */
    public static String getStreamingUrl(String endpoint) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Audius API might redirect to the actual stream URL
                return connection.getURL().toString();
            } else {
                System.out.println("Failed to fetch streaming URL. Response code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Play audio from a URL.
     */
    public static void playAudioFromUrl(String audioUrl) {
        try {
            // Open the audio stream
            URL url = new URL(audioUrl);
            InputStream inputStream = new BufferedInputStream(url.openStream());

            // Use JLayer to play the MP3
            Player player = new Player(inputStream);
            player.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}