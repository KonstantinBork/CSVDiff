package com.bonial.csv;

import java.io.File;

/**
 * CSVDiff
 *
 * @author Konstantin Bork
 * @version 1.0
 * @created 11/30/2015
 * Controller for the application which checks if files are null or not a CSV file.
 */

public class CSVController {

    private CSVModel csvModel;

    public CSVController(CSVModel model) {
        this.csvModel = model;
    }

    public String processFiles(File oldFile, File newFile) {
        Logger.log("Checking input files");
        if(oldFile == null || newFile == null) {
            return "Please import files!";
        } else if(!checkFile(oldFile) || !checkFile(newFile)) {
            return "Please check your files, one of them is not a CSV file!";
        }
        return csvModel.removeDuplicates(oldFile, newFile);
    }

    private boolean checkFile(File inputFile) {
        String fileName = inputFile.getName();
        Logger.log("Checking " + fileName);
        return fileName.endsWith(".csv");
    }

}