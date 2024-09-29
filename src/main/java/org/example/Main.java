package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

// FetchQuotes.java
import static fetch.FetchQuotes.getRandomQuote;

public class Main {
    // Create the GUI using Swing
    public static void createAndShowGUI() {
        // Create the main frame
        JFrame frame = new JFrame("Motivational Quote App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 500);

        // Create a text area to display the quote
        JTextArea quoteArea = new JTextArea();
        quoteArea.setLineWrap(true);
        quoteArea.setWrapStyleWord(true);
        quoteArea.setEditable(false);
        quoteArea.setBackground(Color.DARK_GRAY);
        quoteArea.setForeground(Color.WHITE);

        // Set padding to horizontally center the text
        quoteArea.setMargin(new Insets(150, 20, 50, 20));  // top, left, bottom, right padding
        quoteArea.setFont(new Font("Monospaced", Font.PLAIN, 15));

        // Create a button to fetch a new quote
        JButton fetchQuoteButton = new JButton("Get Random Quote");
        fetchQuoteButton.setFont(new Font("Arial", Font.BOLD, 14));
        fetchQuoteButton.setBackground(Color.GRAY);
        fetchQuoteButton.setForeground(Color.WHITE);

        // Action listener for the button
        fetchQuoteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String quote = null;
                try {
                    quote = getRandomQuote();
                } catch (IOException | InterruptedException ex) {
                    JOptionPane.showMessageDialog(null, "Failed to fetch data", "Connection Error", JOptionPane.ERROR_MESSAGE);
                }
                if (quote != null) {
                    quoteArea.setText(quote);
                }
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

    public static void main(String args[]) {
        // Run the GUI
        createAndShowGUI();
    }
}