package com.example;

import javax.swing.*;
import java.awt.*;

public class RegisterUI {
    private final AuthService authService;

    public RegisterUI() {
        authService = new AuthService();
        initializeUI();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("Registrering");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 1));

        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField displayNameField = new JTextField();
        JButton registerButton = new JButton("Registrera");

        frame.add(new JLabel("E-post:"));
        frame.add(emailField);
        frame.add(new JLabel("Lösenord:"));
        frame.add(passwordField);
        frame.add(new JLabel("Användarnamn:"));
        frame.add(displayNameField);
        frame.add(registerButton);

        registerButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String displayName = displayNameField.getText().trim();

            // Kontrollera att inga fält är tomma
            if (email.isEmpty() || password.isEmpty() || displayName.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Vänligen fyll i alla fält.");
                return; // Stoppa om något fält är tomt
            }

            if (authService.registerUser(email, password, displayName)) {
                JOptionPane.showMessageDialog(frame, "Registrering lyckades!");
                frame.dispose();
                new LoginUI(); // Starta inloggningsfönstret efter registrering
            } else {
                JOptionPane.showMessageDialog(frame, "Registrering misslyckades.");
            }
        });

        frame.setVisible(true);
    }
}
