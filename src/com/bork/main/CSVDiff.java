package com.bork.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * CSVDiff
 *
 * @author Konstantin Bork
 * @version 0.1
 * @created 12/13/2015
 *
 * The main class of the application.
 */

public class CSVDiff extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final String viewLocation = "../view/csvdiff.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(viewLocation));
        Scene scene = new Scene(root);

        primaryStage.setTitle("CSVDiff");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}