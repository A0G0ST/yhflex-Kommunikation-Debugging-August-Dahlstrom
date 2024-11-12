package com.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        FirebaseInitializer.initialize();

        int choice = JOptionPane.showOptionDialog(null, "Välj ett alternativ", "FireChatApp",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                new String[]{"Registrera", "Logga in"}, "Registrera");

        if (choice == 0) {
            new RegisterUI();
        } else {
            new LoginUI();
        }
    }
}

//Mina test användare
//test@test.se/ test123/ TestUser
//testar@testar.com/ fest123/ Anna
//bert@bert.se/ test123/ Bert
//lisa@lisa.se/ test123/ Lisa
//axel@axel.se/ test123/ Axel