package com.bonial.csv;

/**
 * CSVDiff
 *
 * @author Konstantin Bork
 * @version 1.0
 * @created 11/30/2015
 * Main class of the application.
 */

public class CSVDiff {

    public static void main(String[] args) {
        String startMessage =
                "Welcome to CSV Diff!\n" +
                "Import two files and remove duplicates in the second one.";
        Logger.log(startMessage);
        Logger.log(System.getProperty("os.name"));
        CSVModel model = new CSVModel();
        CSVController controller = new CSVController(model);
        CSVView view = new CSVView(controller);
    }

}