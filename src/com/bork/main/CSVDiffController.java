package com.bork.main;

import com.bork.interfaces.Controller;
import com.bork.interfaces.Helper;
import com.bork.util.csv.CSVHelper;
import com.bork.util.excel.XLSHelper;
import com.bork.util.excel.XLSXHelper;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * CSVDiff
 *
 * @author Konstantin Bork
 * @version 0.1
 * @created 12/14/2015
 * <p>
 * §DESCRIPTION§
 */

public class CSVDiffController implements Controller {

    @Override
    public String processFiles(File oldFile, File newFile, File saveFile) {
        Logger.log("Checking input files");
        if(checkIfOneFileIsNull(oldFile, newFile)) {
            return "Please import files!";
        } else if(!filesHaveSameType(oldFile, newFile)) {
            return "Please check your files, they are not of the same type!";
        }
        String fileType = FilenameUtils.getExtension(newFile.getName());
        String message;
        Helper fileHelper;
        switch(fileType) {
            case "csv":
                fileHelper = new CSVHelper();
                message = fileHelper.removeDuplicates(oldFile, newFile, saveFile);
                break;
            case "xls":
                fileHelper = new XLSHelper();
                message = fileHelper.removeDuplicates(oldFile, newFile, saveFile);
                break;
            case "xlsx":
                fileHelper = new XLSXHelper();
                message = fileHelper.removeDuplicates(oldFile, newFile, saveFile);
                break;
            default:
                message = "File type is not supported!";
        }
        return message;
    }

    private boolean checkIfOneFileIsNull(File oldFile, File newFile) {
        return oldFile == null || newFile == null;
    }

    private boolean filesHaveSameType(File oldFile, File newFile) {
        String oldFileType = FilenameUtils.getExtension(oldFile.getName());
        String newFileType = FilenameUtils.getExtension(newFile.getName());
        return oldFileType.equals(newFileType);
    }

}