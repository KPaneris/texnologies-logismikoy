package org.example.demo1;

import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AudiusPlayer {

    /**
     * Play audio from a URL.
     */
    public static void playAudioFromUrl(String audioUrl) {
        new Thread(() -> {
            try {
                URL url = new URL(audioUrl);
                InputStream inputStream = new BufferedInputStream(url.openStream());
                Player player = new Player(inputStream);
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Fetch the streaming URL for a given track.
     */
    public static String getStreamingUrl(String trackId) {
        try {
            String endpoint = "https://api.audius.co/v1/tracks/" + trackId + "/stream";
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return connection.getURL().toString();
            } else {
                System.out.println("Failed to fetch streaming URL. Response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
