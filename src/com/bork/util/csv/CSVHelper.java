package com.bork.util.csv;

import com.bork.interfaces.Helper;
import com.bork.main.Logger;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.List;

/**
 * CSVDiff
 *
 * @author Konstantin Bork
 * @version 0.1
 * @created 12/14/2015
 * <p>
 * §DESCRIPTION§
 */

public class CSVHelper implements Helper {

    @Override
    public String removeDuplicates(File oldFile, File newFile, File saveFile) {
        try {
            improveContentOfInputFiles(oldFile, newFile);
            Logger.log("Reading old file");
            List<String> oldFileLines = FileUtils.readLines(oldFile);
            Logger.log("Reading new file");
            List<String> newFileLines = FileUtils.readLines(newFile);
            Logger.log("Removing duplicates");
            newFileLines.removeAll(oldFileLines);
            Logger.log("Writing into new File");
            FileUtils.writeLines(saveFile, newFileLines);
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