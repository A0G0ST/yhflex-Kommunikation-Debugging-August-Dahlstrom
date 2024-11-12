package com.example;

import com.google.firebase.database.*;

public class FirebaseService {
    private final DatabaseReference messagesRef;

    public FirebaseService() {
        messagesRef = FirebaseDatabase.getInstance().getReference("messages");
    }

    public void sendMessage(Message message) {
        try {
            messagesRef.push().setValue(message, (databaseError, databaseReference) -> {
                if (databaseError != null) {
                    System.err.println("Data kunde inte sparas: " + databaseError.getMessage());
                } else {
                    System.out.println("Meddelande sparat till databasen.");
                }
            });
        } catch (Exception e) {
            System.err.println("Ett oväntat fel inträffade: " + e.getMessage());
        }
    }

    public void getMessages(ValueEventListener listener) {
        try {
            messagesRef.addValueEventListener(listener);
        } catch (Exception e) {
            System.err.println("Misslyckades att hämta meddelanden: " + e.getMessage());
        }
    }
}
