package org.example.demo1;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class ApiExample {
    private static final String DEVELOPER_KEY = "AIzaSyDo_hvRUaEP50f9nSOjLQvjWUt3zU7fo20";
    private static final String APPLICATION_NAME = "MusicPlayerProject";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static List<String> searchYouTube(String query) throws GeneralSecurityException, IOException {
        YouTube youtubeService = getService();

        YouTube.Search.List request = youtubeService.search()
                .list(List.of("snippet"));
        SearchListResponse response = request.setKey(DEVELOPER_KEY)
                .setQ(query)
                .setMaxResults(10L)  // Limiting the number of results
                .execute();

        List<String> videoTitles = new ArrayList<>();
        for (SearchResult result : response.getItems()) {
            videoTitles.add(result.getSnippet().getTitle());
        }
        return videoTitles;
    }

    public static void displayResults(List<String> videoTitles) {
        // Create a new frame to hold the UI elements
        JFrame frame = new JFrame("YouTube Search Results");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create a JList to display the video titles
        JList<String> list = new JList<>(videoTitles.toArray(new String[0]));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);

        // Add the list to a scroll pane
        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                List<String> videoTitles = searchYouTube("??/");  // You can change the query here
                displayResults(videoTitles);
            } catch (GeneralSecurityException | IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}