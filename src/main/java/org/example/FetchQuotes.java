package fetch;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FetchQuotes {
    // Fetch the random quote from ZenQuotes API
    public static String getRandomQuote() throws IOException, InterruptedException {
        String apiUrl = "https://zenquotes.io/api/random";

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(apiUrl)).build();
        var client = HttpClient.newBuilder().build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (ConnectException e) {
            // Show a popup when the user is offline
            JOptionPane.showMessageDialog(null, "You are offline. Please check your internet connection.", "Connection Error", JOptionPane.ERROR_MESSAGE);
            throw e; // rethrow the exception if needed, or return null if you want to stop here
        }

        if (response.statusCode() != 200) {
            JOptionPane.showMessageDialog(null, "Failed to fetch data", "Connection Error", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
        }

        // Parse the JSON response using Gson
        JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();
        JsonObject quoteObj = jsonArray.get(0).getAsJsonObject();
        String quote = quoteObj.get("q").getAsString();
        String author = quoteObj.get("a").getAsString();

        return "\"" + quote + "\"\n\n - " + author;
    }
}
