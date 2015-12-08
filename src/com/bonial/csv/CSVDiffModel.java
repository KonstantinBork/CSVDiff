package com.bonial.csv;

import com.bonial.Logger;
import com.bonial.interfaces.Model;
import com.bonial.util.XLSHelper;
import com.bonial.util.XLSXHelper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

/**
 * CSVDiff
 *
 * @author Konstantin Bork
 * @version 1.0
 * @created 11/30/2015
 * Model of the application which removes duplicates from the newer CSV file.
 */

public class CSVDiffModel implements Model {

    public CSVDiffModel() {

    }

    public String removeDuplicates(File oldFile, File newFile) {
        String fileExtension = FilenameUtils.getExtension(oldFile.getName());
        switch(fileExtension) {
            case "csv":
                return removeDuplicatesInCSV(oldFile, newFile);
            case "xls":
                return removeDuplicatesInXLS(oldFile, newFile);
            case "xlsx":
                return removeDuplicatesInXLSX(oldFile, newFile);
            default:
                return "No result";
        }
    }


    private String removeDuplicatesInCSV(File oldFile, File newFile) {
        try {
            improveContentOfInputFiles(oldFile, newFile);
            List<String> oldFileLines = FileUtils.readLines(oldFile);
            Logger.log("Reading old file");
            List<String> newFileLines = FileUtils.readLines(newFile);
            Logger.log("Reading new file");
            newFileLines.removeAll(oldFileLines);
            Logger.log("Removing duplicates");
            FileUtils.writeLines(newFile, newFileLines);
            Logger.log("Writing into new File");
            return "New file successfully shortened!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Exception caught, please check logs.";
        }
    }

    private String removeDuplicatesInXLS(File oldFile, File newFile) {
        try {
            HSSFWorkbook oldWorkbook = new HSSFWorkbook(new FileInputStream(oldFile));
            HSSFWorkbook newWorkbook = new HSSFWorkbook(new FileInputStream(newFile));
            HSSFSheet sheet1 = oldWorkbook.getSheetAt(0);
            HSSFSheet sheet2 = newWorkbook.getSheetAt(0);
            Logger.log("Getting content of sheet 1");
            List<List<String>> sheet1Contents = XLSHelper.getSheetContents(oldWorkbook, sheet1);
            Logger.log("Getting content of sheet 2");
            List<List<String>> sheet2Contents = XLSHelper.getSheetContents(newWorkbook, sheet2);
            sheet2Contents.removeAll(sheet1Contents);
            XLSXHelper.writeToNewFile(newFile, sheet2Contents);
            Logger.log("");
            return "New file successfully shortened!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Exception caught, please check logs.";
        }
    }

    private String removeDuplicatesInXLSX(File oldFile, File newFile) {
        try {
            XSSFWorkbook oldWorkbook = new XSSFWorkbook(new FileInputStream(oldFile));
            XSSFWorkbook newWorkbook = new XSSFWorkbook(new FileInputStream(newFile));
            XSSFSheet sheet1 = oldWorkbook.getSheetAt(0);
            XSSFSheet sheet2 = newWorkbook.getSheetAt(0);
            Logger.log("Getting content of sheet 1");
            List<List<String>> sheet1Contents = XLSXHelper.getSheetContents(oldWorkbook, sheet1);
            Logger.log("Getting content of sheet 2");
            List<List<String>> sheet2Contents = XLSXHelper.getSheetContents(newWorkbook, sheet2);
            sheet2Contents.removeAll(sheet1Contents);
            XLSXHelper.writeToNewFile(newFile, sheet2Contents);
            Logger.log("");
            return "New file successfully shortened!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Exception caught, please check logs.";
        }
    }

    private void improveContentOfInputFiles(File oldFile, File newFile) {
        try {
            String oldFileContent = new String(Files.readAllBytes(oldFile.toPath()));
            String newFileContent = new String(Files.readAllBytes(newFile.toPath()));
            Logger.log("Improving content of file " + oldFile.getName());
            oldFileContent = improveContent(oldFileContent);
            FileUtils.write(oldFile, oldFileContent);
            Logger.log("Improving content of file " + newFile.getName());
            newFileContent = improveContent(newFileContent);
            FileUtils.write(newFile, newFileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String improveContent(String content) {
        String result = "";
        BufferedReader reader = new BufferedReader(new StringReader(content));
        try {
            Logger.log("Started improving content");
            long startTime = System.currentTimeMillis();
            String singleLine = reader.readLine();
            int numberOfAttributes = singleLine.split(";").length;
            result += singleLine;
            while((singleLine = reader.readLine()) != null) {
                int i = singleLine.split(";").length;
                while(i < numberOfAttributes - 1) {
                    singleLine += reader.readLine();
                    i = singleLine.split(";").length;
                }
                result += singleLine + "\n";
            }
            long endTime = System.currentTimeMillis();
            long elapsedTime = (endTime - startTime) / 1000;
            Logger.log("Finished improving content in " + elapsedTime / 60 + " minutes and " + elapsedTime % 60 + " seconds");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}