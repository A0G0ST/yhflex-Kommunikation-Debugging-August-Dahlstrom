package com.example;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ChatUI {
    private final FirebaseService firebaseService;
    private JTextArea chatArea;
    private JTextField inputField;
    private String username;

    public ChatUI(String username) {
        this.username = username;
        firebaseService = new FirebaseService();
        initializeUI();
        loadMessages();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("FireChatApp");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel för att hålla chattinmatningsfältet och logout-knappen
        JPanel bottomPanel = new JPanel(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        inputField = new JTextField();
        inputField.addActionListener(e -> sendMessage());
        bottomPanel.add(inputField, BorderLayout.CENTER);

        // Logga ut knapp som tar användaren tillbaka till inloggningsskärmen
        JButton logoutButton = new JButton("Logga ut");
        logoutButton.addActionListener(e -> logout(frame));
        bottomPanel.add(logoutButton, BorderLayout.EAST);

        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // Hämtar meddelanden från Firebase och visar dem i chatArea
    private void loadMessages() {
        firebaseService.getMessages(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatArea.setText("");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    if (message != null) {
                        chatArea.append(formatMessage(message) + "\n");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                JOptionPane.showMessageDialog(null, "Fel vid laddning av meddelanden: " + databaseError.getMessage());
            }
        });
    }

    // Skickar meddelandet till Firebase
    private void sendMessage() {
        String text = inputField.getText().trim();
        if (!text.isEmpty()) {
            Message message = new Message(text, username, new Date().getTime());
            firebaseService.sendMessage(message);
            inputField.setText("");
        }
    }

    private String formatMessage(Message message) {
        return message.getSender() + ": " + message.getText();
    }

    // Logga ut genom att stänga fönstret och återgå till inloggningsskärmen
    private void logout(JFrame frame) {
        frame.dispose();
        new LoginUI();
    }
}
