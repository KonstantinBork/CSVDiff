package com.bonial.csv;

import com.bonial.Logger;
import com.bonial.interfaces.Controller;
import com.bonial.interfaces.Model;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * CSVDiff
 *
 * @author Konstantin Bork
 * @version 1.0
 * @created 11/30/2015
 * Controller for the application which checks if files are null or not a CSV file.
 */

public class CSVDiffController implements Controller {

    private Model csvDiffModel;

    public CSVDiffController(Model model) {
        this.csvDiffModel = model;
    }

    public String processFiles(File oldFile, File newFile) {
        Logger.log("Checking input files");
        if(checkIfOneFileIsNull(oldFile, newFile)) {
            return "Please import files!";
        } else if(!filesHaveSameType(oldFile, newFile)) {
            return "Please check your files, they are not of the same type!";
        }
        return csvDiffModel.removeDuplicates(oldFile, newFile);
    }

    private boolean checkIfOneFileIsNull(File oldFile, File newFile) {
        return oldFile == null || newFile == null;
    }

    private boolean filesHaveSameType(File oldFile, File newFile) {
        String oldFileType = FilenameUtils.getExtension(oldFile.getName());;
        String newFileType = FilenameUtils.getExtension(newFile.getName());;
        return oldFileType.equals(newFileType);
    }

}