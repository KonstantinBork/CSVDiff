package com.bork.main;

import com.bork.interfaces.Controller;
import com.bork.interfaces.Helper;
import com.bork.util.csv.CSVHelper;
import com.bork.util.excel.XLSHelper;
import com.bork.util.excel.XLSXHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * CSVDiff
 *
 * @author Konstantin Bork
 * @version 0.1
 * @created 12/14/2015
 *
 * §DESCRIPTION§
 */

public class CSVDiffController implements Controller {

    @FXML
    private Label importLbl1;
    @FXML
    private Label importLbl2;
    @FXML
    private Label infoLbl;

    private File oldInputFile;
    private File newInputFile;

    @Override
    public String processFiles(File oldFile, File newFile, File saveFile) {
        Logger.log("Checking input files");
        if (checkIfOneFileIsNull(oldFile, newFile)) {
            return "Please import files!";
        } else if (!filesHaveSameType(oldFile, newFile)) {
            return "Please check your files, they are not of the same type!";
        }
        String fileType = FilenameUtils.getExtension(newFile.getName());
        String message = "File type is not supported!";
        Helper fileHelper;
        switch (fileType) {
            case "csv":
                fileHelper = new CSVHelper();
                break;
            case "xls":
                fileHelper = new XLSHelper();
                break;
            case "xlsx":
                fileHelper = new XLSXHelper();
                break;
            default:
                fileHelper = null;
        }
        if (fileHelper != null) {
            message = fileHelper.removeDuplicates(oldFile, newFile, saveFile);
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

    /*
     * UI operations
     */

    public void importOldFile(ActionEvent actionEvent) {
        oldInputFile = importFile();
        importLbl1.setText("File name: " + oldInputFile.getName());
    }

    public void importNewFile(ActionEvent actionEvent) {
        newInputFile = importFile();
        importLbl2.setText("File name: " + newInputFile.getName());
    }

    public void runProcessFiles(ActionEvent actionEvent) {
        try {
            String fileExtension = FilenameUtils.getExtension(oldInputFile.getName());
            File saveFile = saveFile("*." + fileExtension);
            String message = processFiles(oldInputFile, newInputFile, saveFile);
            infoLbl.setText(message);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private File importFile() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Import file");
        chooser.getExtensionFilters().addAll(
                new ExtensionFilter("Supported Files", "*.csv", "*.xls", "*.xlsx"),
                new ExtensionFilter("All Files", "*.*")
        );
        return chooser.showOpenDialog(new Stage());
    }

    private File saveFile(String type) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save file");
        chooser.getExtensionFilters().addAll(
                new ExtensionFilter("Supported Files", type),
                new ExtensionFilter("All Files", "*.*")
        );
        return chooser.showSaveDialog(new Stage());
    }

}