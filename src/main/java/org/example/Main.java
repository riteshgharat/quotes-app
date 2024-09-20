package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {

    // Fetch the random quote from ZenQuotes API
    public static String getRandomQuote() throws IOException, InterruptedException {
        String apiUrl = "https://zenquotes.io/api/random";

            HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(apiUrl)).build();
            var client = HttpClient.newBuilder().build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
            }

            // Parse the JSON response using Gson
            JsonArray jsonArray = JsonParser.parseString(response.body().toString()).getAsJsonArray();
            JsonObject quoteObj = jsonArray.get(0).getAsJsonObject();
            String quote = quoteObj.get("q").getAsString();
            String author = quoteObj.get("a").getAsString();

            System.out.println(quote + " " + author);
            return "\"" + quote + "\" - " + author;
    }

    // Create the GUI using Swing
    public static void createAndShowGUI() {
        // Create the main frame
        JFrame frame = new JFrame("Quote App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        // Create a text area to display the quote
        JTextArea quoteArea = new JTextArea();
        quoteArea.setLineWrap(true);
        quoteArea.setWrapStyleWord(true);
        quoteArea.setEditable(false);
        quoteArea.setFont(new Font("Arial", Font.PLAIN, 16));

        // Create a button to fetch a new quote
        JButton fetchQuoteButton = new JButton("Get Random Quote");
        fetchQuoteButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Action listener for the button
        fetchQuoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String quote = null;
                try {
                    quote = getRandomQuote();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                quoteArea.setText(quote);
            }
        });

        // Layout setup
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(quoteArea), BorderLayout.CENTER);
        panel.add(fetchQuoteButton, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);

        // Show the GUI
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Run the GUI on the event-dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}