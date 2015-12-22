package com.bork.util.excel;

import com.bork.interfaces.Controller;
import com.bork.interfaces.Helper;
import com.bork.main.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
 *
 * §DESCRIPTION§
 */

public class XLSXHelper implements Helper {

    private Controller controller;

    public XLSXHelper(Controller c) {
        controller = c;
    }

    @Override
    public void removeDuplicates(File oldFile, File newFile, File saveFile) {
        try {
            XSSFWorkbook oldWorkbook = new XSSFWorkbook(new FileInputStream(oldFile));
            XSSFWorkbook newWorkbook = new XSSFWorkbook(new FileInputStream(newFile));
            XSSFSheet sheet1 = oldWorkbook.getSheetAt(0);
            XSSFSheet sheet2 = newWorkbook.getSheetAt(0);
            Logger.log("Getting content of sheet 1");
            List<List<String>> sheet1Contents = ExcelHelper.getSheetContents(oldWorkbook, sheet1);
            Logger.log("Getting content of sheet 2");
            List<List<String>> sheet2Contents = ExcelHelper.getSheetContents(newWorkbook, sheet2);
            sheet2Contents.removeAll(sheet1Contents);
            writeToNewFile(saveFile, sheet2Contents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean writeToNewFile(File file, List<List<String>> content) {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Iterator<List<String>> listIterator = content.listIterator();
        int i = 0;
        return ExcelHelper.writeFile(listIterator, sheet, i, file, wb);
    }

}