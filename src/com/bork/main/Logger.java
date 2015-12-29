package com.bork.main;

import javafx.scene.control.Alert;

/**
 * CSVDiff
 *
 * @author Konstantin Bork
 * @version 0.1
 * @created 12/13/2015
 * <p>
 * Static class to provide simple logging to standard output. Furthermore it provides an error dialog for the frontend.
 */

public class Logger {

    public static void log(String msg) {
        System.out.println(msg);
    }

    public static void log(char msg) {
        log(String.valueOf(msg));
    }

    public static void log(byte msg) {
        log(String.valueOf(msg));
    }

    public static void log(short msg) {
        log(String.valueOf(msg));
    }

    public static void log(int msg) {
        log(String.valueOf(msg));
    }

    public static void log(long msg) {
        log(String.valueOf(msg));
    }

    public static void log(float msg) {
        log(String.valueOf(msg));
    }

    public static void log(double msg) {
        log(String.valueOf(msg));
    }

    public static void log(boolean msg) {
        log(String.valueOf(msg));
    }

    public static void showErrorDialog(String header, String errorText) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(errorText);
        errorAlert.showAndWait();
    }

}