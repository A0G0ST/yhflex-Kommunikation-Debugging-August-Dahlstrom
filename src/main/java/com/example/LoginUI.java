package com.example;

import javax.swing.*;
import java.awt.*;
import com.google.firebase.auth.UserRecord;

public class LoginUI {
    private final AuthService authService;

    public LoginUI() {
        authService = new AuthService();
        initializeUI();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("Logga in");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 1));

        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Logga in");

        frame.add(new JLabel("E-post:"));
        frame.add(emailField);
        frame.add(new JLabel("Lösenord:"));
        frame.add(passwordField);
        frame.add(loginButton);

        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            // Kontrollera att inga fält är tomma
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Vänligen fyll i både e-post och lösenord.");
                return; // Stoppa om något fält är tomt
            }

            // Kontrollera användare genom email och lösenord
            UserRecord user = authService.loginUser(email, password); // Skicka med lösenord
            if (user != null) {
                JOptionPane.showMessageDialog(frame, "Inloggning lyckades! Välkommen, " + user.getDisplayName());
                frame.dispose();
                new ChatUI(user.getDisplayName()); // Starta chattfönster med användarnamn
            } else {
                JOptionPane.showMessageDialog(frame, "Inloggning misslyckades. Har du skrivit rätt epost och lösenord?");
            }
        });

        frame.setVisible(true);
    }
}
