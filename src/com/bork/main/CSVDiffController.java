package com.bork.main;

import com.bork.exceptions.FileNotSupportedException;
import com.bork.exceptions.NotSameTypeException;
import com.bork.interfaces.Controller;
import com.bork.interfaces.Helper;
import com.bork.util.csv.CSVHelper;
import com.bork.util.excel.XLSHelper;
import com.bork.util.excel.XLSXHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;

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

    @FXML
    private Label file1Lbl;
    @FXML
    private Label file2Lbl;
    @FXML
    private ProgressBar progressBar;

    private File oldInputFile;
    private File newInputFile;

    @Override
    public void processFiles(File oldFile, File newFile, File saveFile) throws NullPointerException, NotSameTypeException, FileNotSupportedException {
        Logger.log("Checking input files");
        if (checkIfOneFileIsNull(oldFile, newFile)) {
            throw new NullPointerException();
        } else if (!filesHaveSameType(oldFile, newFile)) {
            throw new NotSameTypeException();
        }
        String fileType = FilenameUtils.getExtension(newFile.getName());
        Helper fileHelper;
        switch (fileType) {
            case "csv":
                fileHelper = new CSVHelper(this);
                break;
            case "xls":
                fileHelper = new XLSHelper(this);
                break;
            case "xlsx":
                fileHelper = new XLSXHelper(this);
                break;
            default:
                throw new FileNotSupportedException();
        }
        boolean saveFileExists = saveFile.exists();
        if (!saveFileExists) {
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                Logger.log("Save file could not be created!");
                e.printStackTrace();
            }
        }
        fileHelper.removeDuplicates(oldFile, newFile, saveFile);

    }

    @Override
    public void setProgress(double value) {
        progressBar.setProgress(value);
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

    /**
     * @param actionEvent
     */
    public void importOldFile(ActionEvent actionEvent) {
        try {
            oldInputFile = importFile();
            file1Lbl.setText(oldInputFile.getName());
        } catch (NullPointerException e) {
            Logger.log("Old file is not selected!");
            e.printStackTrace();
        }
    }

    /**
     * @param actionEvent
     */
    public void importNewFile(ActionEvent actionEvent) {
        try {
            newInputFile = importFile();
            file2Lbl.setText(newInputFile.getName());
        } catch (NullPointerException e) {
            Logger.log("New file is not selected!");
            e.printStackTrace();
        }
    }

    /**
     * @param actionEvent
     */
    public void runProcessFiles(ActionEvent actionEvent) {
        File saveFile = null;
        try {
            String fileExtension = FilenameUtils.getExtension(oldInputFile.getName());
            Logger.log("File extension is " + fileExtension + ".");
            saveFile = saveFile("*." + fileExtension);
        } catch (NullPointerException e) {
            Logger.log("Save file is not selected!");
            e.printStackTrace();
        }
        try {
            processFiles(oldInputFile, newInputFile, saveFile);
        } catch (NullPointerException e) {
            Logger.log("At least one input file is not selected!");
            e.printStackTrace();
        } catch (NotSameTypeException e) {
            Logger.log("Input files do not have the same type!");
            e.printStackTrace();
        } catch (FileNotSupportedException e) {
            Logger.log("Type of input files is not supported!");
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