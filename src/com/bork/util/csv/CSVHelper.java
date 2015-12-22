package com.bork.util.csv;

import com.bork.interfaces.Controller;
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
 *
 * §DESCRIPTION§
 */

public class CSVHelper implements Helper {

    private final double FILE1PROGRESS;
    private final double FILE2PROGRESS;
    private Controller controller;

    public CSVHelper(Controller c) {
        controller = c;
        FILE1PROGRESS = 0.48;
        FILE2PROGRESS = 0.96;
    }

    @Override
    public void removeDuplicates(File oldFile, File newFile, File saveFile) {
        try {
            improveContentOfInputFiles(oldFile, newFile);
            Logger.log("Reading old file");
            List<String> oldFileLines = FileUtils.readLines(oldFile);
            controller.setProgress(FILE2PROGRESS + 0.01);
            Logger.log("Reading new file");
            List<String> newFileLines = FileUtils.readLines(newFile);
            controller.setProgress(FILE2PROGRESS + 0.02);
            Logger.log("Removing duplicates");
            newFileLines.removeAll(oldFileLines);
            controller.setProgress(FILE2PROGRESS + 0.03);
            Logger.log("Writing into new File");
            FileUtils.writeLines(saveFile, newFileLines);
            controller.setProgress(1.00);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void improveContentOfInputFiles(File oldFile, File newFile) {
        try {
            String oldFileContent = new String(Files.readAllBytes(oldFile.toPath()));
            String newFileContent = new String(Files.readAllBytes(newFile.toPath()));
            Logger.log("Improving content of file " + oldFile.getName());
            oldFileContent = improveContent(oldFileContent);
            FileUtils.write(oldFile, oldFileContent);
            controller.setProgress(FILE1PROGRESS);
            Logger.log("Improving content of file " + newFile.getName());
            newFileContent = improveContent(newFileContent);
            FileUtils.write(newFile, newFileContent);
            controller.setProgress(FILE2PROGRESS);
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