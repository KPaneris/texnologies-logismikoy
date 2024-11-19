package org.example.demo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;




public class AudiusAPI {
    public static void main(String[] args) {
        try {
            // Define the Audius API endpoint
            String endpoint = "https://api.audius.co/v1/tracks/trending";

            // Create a URL object
            URL url = new URL(endpoint);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method to GET
            connection.setRequestMethod("GET");

            // Set request headers (optional)
            connection.setRequestProperty("Accept", "application/json");

            // Get response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // If the response is successful (200 OK), read the response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                // Read each line from the input stream
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Print the response
                System.out.println("Response: " + response.toString());
            } else {
                System.out.println("GET request failed.");
            }

            // Disconnect the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
