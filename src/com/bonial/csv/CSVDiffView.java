package com.bonial.csv;

import com.bonial.Logger;
import com.bonial.interfaces.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * CSVDiff
 *
 * @author Konstantin Bork
 * @version 1.0
 * @created 11/30/2015
 * View of the application.
 */

public class CSVDiffView {

    private final String IMPORTOLD = "Import old CSV file: ";
    private final String IMPORTNEW = "Import new CSV file: ";
    private final String RESULT = "Result: ";

    private Controller csvDiffController;

    private JFrame frame;
    private JLabel importOldFileLabel;
    private JLabel importNewFileLabel;
    private JLabel importResultLabel;
    private JLabel tempLabel1;
    private JLabel tempLabel2;
    private JButton importOldFileButton;
    private JButton importNewFileButton;
    private JButton removeDuplicatesButton;

    private File oldFile;
    private File newFile;

    public CSVDiffView(Controller controller) {
        csvDiffController = controller;
        initialize();
    }

    private void initialize() {
        Logger.log("Initializing GUI");
        frame = new JFrame("CSV Diff");
        frame.setBounds(100, 100, 400, 350);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);

        int maxWidth = frame.getWidth() - 40;

        importOldFileLabel = new JLabel("Old file: ");
        importOldFileLabel.setBounds(20, 20, maxWidth, 40);
        importOldFileLabel.setVisible(true);
        frame.add(importOldFileLabel);

        importOldFileButton = new JButton("Import file");
        importOldFileButton.setBounds(20, 60, maxWidth, 40);
        importOldFileButton.setVisible(true);
        importOldFileButton.addActionListener(new ActionListener() {

            JFileChooser fileChooser = new JFileChooser();

            @Override
            public void actionPerformed(ActionEvent e) {
                Logger.log("Import old file button clicked");
                int result = fileChooser.showOpenDialog(importOldFileButton);
                if(result == JFileChooser.APPROVE_OPTION) {
                    oldFile = fileChooser.getSelectedFile();
                    importOldFileLabel.setText(IMPORTOLD + oldFile.getName());
                }
            }
        });
        frame.add(importOldFileButton);

        importNewFileLabel = new JLabel("New file: ");
        importNewFileLabel.setBounds(20, 100, maxWidth, 40);
        importNewFileLabel.setVisible(true);
        frame.add(importNewFileLabel);

        importNewFileButton = new JButton("Import file");
        importNewFileButton.setBounds(20, 140, maxWidth, 40);
        importNewFileButton.setVisible(true);
        importNewFileButton.addActionListener(new ActionListener() {

            JFileChooser fileChooser = new JFileChooser();

            @Override
            public void actionPerformed(ActionEvent e) {
                Logger.log("Import new file button clicked");
                int result = fileChooser.showOpenDialog(importNewFileButton);
                if(result == JFileChooser.APPROVE_OPTION) {
                    newFile = fileChooser.getSelectedFile();
                    importNewFileLabel.setText(IMPORTNEW + newFile.getName());
                }
            }
        });
        frame.add(importNewFileButton);

        removeDuplicatesButton = new JButton("Remove duplicates");
        removeDuplicatesButton.setBounds(20, 180, maxWidth, 40);
        removeDuplicatesButton.setBackground(Color.GREEN);
        removeDuplicatesButton.setContentAreaFilled(true);
        removeDuplicatesButton.setOpaque(true);
        removeDuplicatesButton.setVisible(true);
        removeDuplicatesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logger.log("Remove duplicates button clicked");
                importResultLabel.setText("Improving both files and removing duplicates");
                String result = csvDiffController.processFiles(oldFile, newFile);
                importResultLabel.setText(RESULT + result);
                tempLabel1.setVisible(false);
                tempLabel2.setVisible(false);
            }
        });
        frame.add(removeDuplicatesButton);

        importResultLabel = new JLabel("Processing takes ca. 30 min.");
        importResultLabel.setBounds(20, 220, maxWidth, 40);
        importResultLabel.setVisible(true);
        frame.add(importResultLabel);

        tempLabel1 = new JLabel("Please don't close this window!");
        tempLabel1.setBounds(20, 240, maxWidth, 40);
        tempLabel1.setVisible(true);
        frame.add(tempLabel1);

        tempLabel2 = new JLabel("Result will be saved in second file.");
        tempLabel2.setBounds(20, 260, maxWidth, 40);
        tempLabel2.setVisible(true);
        frame.add(tempLabel2);

        Logger.log("Initializing complete");
        frame.setVisible(true);
    }

}