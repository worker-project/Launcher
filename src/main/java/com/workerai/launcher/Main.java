package com.workerai.launcher;

import javafx.application.Application;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("javafx.application.Application");
            Application.launch(WorkerLauncher.class, args);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error: \n" + e.getLocalizedMessage() + "not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}