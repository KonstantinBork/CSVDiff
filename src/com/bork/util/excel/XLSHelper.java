package com.bork.util.excel;

import com.bork.interfaces.Controller;
import com.bork.interfaces.Helper;
import com.bork.main.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
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

public class XLSHelper implements Helper {

    private final double FILE1PROGRESS;
    private final double FILE2PROGRESS;

    private Controller controller;

    public XLSHelper(Controller c) {
        controller = c;
        FILE1PROGRESS = 0.49;
        FILE2PROGRESS = 0.98;
    }

    @Override
    public void removeDuplicates(File oldFile, File newFile, File saveFile) {
        try {
            HSSFWorkbook oldWorkbook = new HSSFWorkbook(new FileInputStream(oldFile));
            HSSFWorkbook newWorkbook = new HSSFWorkbook(new FileInputStream(newFile));
            HSSFSheet sheet1 = oldWorkbook.getSheetAt(0);
            HSSFSheet sheet2 = newWorkbook.getSheetAt(0);
            Logger.log("Getting content of sheet 1");
            List<List<String>> sheet1Contents = ExcelHelper.getSheetContents(oldWorkbook, sheet1);
            controller.setProgress(FILE1PROGRESS);
            Logger.log("Getting content of sheet 2");
            List<List<String>> sheet2Contents = ExcelHelper.getSheetContents(newWorkbook, sheet2);
            controller.setProgress(FILE2PROGRESS);
            sheet2Contents.removeAll(sheet1Contents);
            controller.setProgress(0.99);
            writeToNewFile(saveFile, sheet2Contents);
            controller.setProgress(1.00);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean writeToNewFile(File file, List<List<String>> content) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Iterator<List<String>> listIterator = content.listIterator();
        int i = 0;
        return ExcelHelper.writeFile(listIterator, sheet, i, file, wb);
    }

}