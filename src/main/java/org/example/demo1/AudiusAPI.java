package org.example.demo1;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AudiusAPI {

    private static final String BASE_URL = "https://api.audius.co/v1";

    public static Map<String, String> fetchTrendingTracks() throws Exception {
        String endpoint = BASE_URL + "/tracks/trending";
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            // Parse the JSON response to extract track titles and streaming URLs
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray tracks = jsonResponse.getJSONArray("data");

            Map<String, String> trackMap = new HashMap<>();
            for (int i = 0; i < tracks.length(); i++) {
                JSONObject track = tracks.getJSONObject(i);
                String title = track.getString("title");
                String streamUrl = track.getString("permalink"); // Adjust if Audius API provides direct stream URL
                trackMap.put(title, streamUrl);
            }

            return trackMap;
        } else {
            throw new Exception("Failed to fetch trending tracks. Response code: " + responseCode);
        }
    }
}
