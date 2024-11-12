package com.example;

import javax.swing.JOptionPane;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

public class AuthService {
    private final FirebaseAuth auth;

    public AuthService() {
        this.auth = FirebaseAuth.getInstance();
    }

    // Registrera en ny användare med e-post, lösenord och visningsnamn/username
    public boolean registerUser(String email, String password, String displayName) { 
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
            .setEmail(email)
            .setPassword(password)
            .setDisplayName(displayName);
    
        try {
            UserRecord userRecord = auth.createUser(request);
            System.out.println("Användare skapad med UID: " + userRecord.getUid());
            return true;
        } catch (FirebaseAuthException e) {
            
            System.err.println("Fel vid skapande av användare: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Registrering misslyckades, email adressen är redan registrerad.");
            return false;
        }
    }
    

    // Logga in användaren med e-post 
    public UserRecord loginUser(String email, String password) {
        try {
            UserRecord userRecord = auth.getUserByEmail(email);

            // Verifiera att användaren finns
            if (userRecord != null) {
                System.out.println("Inloggning lyckades för användare: " + userRecord.getDisplayName());
                return userRecord;
            } else {
                System.out.println("Användaren hittades inte för e-post: " + email);
                return null;
            }

        } catch (FirebaseAuthException e) {
            System.err.println("Fel vid inloggning: " + e.getMessage());
            return null;
        }
    }
}
