package com.example;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.InputStream;

public class FirebaseInitializer {

    public static void initialize() {
        try {
            InputStream serviceAccount = FirebaseInitializer.class.getResourceAsStream("/firebase-key.json");

            if (serviceAccount == null) {
                throw new RuntimeException("Service account JSON file not found in resources!");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://firechatapp-e6974-default-rtdb.europe-west1.firebasedatabase.app/")  
                .build();

            FirebaseApp.initializeApp(options);
            System.out.println("Firebase har initialiserats!");

        } catch (Exception e) {
            System.err.println("Kunde inte initialisera Firebase: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
