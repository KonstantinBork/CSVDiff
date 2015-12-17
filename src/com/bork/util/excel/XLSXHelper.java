package com.bork.util.excel;

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
 * <p>
 * §DESCRIPTION§
 */

public class XLSXHelper implements Helper {

    @Override
    public String removeDuplicates(File oldFile, File newFile, File saveFile) {
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
            Logger.log("");
            return "New file successfully shortened!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Exception caught, please check logs.";
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