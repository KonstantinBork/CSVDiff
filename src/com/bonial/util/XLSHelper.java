package com.bonial.util;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * CSVDiff
 *
 * @author Konstantin Bork
 * @version 0.1
 * @created 12/08/2015
 * <p>
 * §DESCRIPTION§
 */

public class XLSHelper {

    public static List<List<String>> getSheetContents(Workbook wb, HSSFSheet sheet) {
        List<List<String>> sheetContents = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.rowIterator();
        while(rowIterator.hasNext()) {
            List<String> rowContent = new ArrayList<>();
            Row currentRow = rowIterator.next();
            Iterator<Cell> cellIterator = currentRow.cellIterator();
            while(cellIterator.hasNext()) {
                Cell currentCell = cellIterator.next();
                rowContent.add(getCellValue(wb, currentCell));
            }
            sheetContents.add(rowContent);
        }
        return sheetContents;
    }

    public static boolean writeToNewFile(File file, List<List<String>> content) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Iterator<List<String>> listIterator = content.listIterator();
        int i = 0;
        while(listIterator.hasNext()) {
            List<String> currentRow = listIterator.next();
            Row row = sheet.createRow(i++);
            Iterator<String> stringIterator = currentRow.iterator();
            int j = 0;
            while(stringIterator.hasNext()) {
                Cell cell = row.createCell(j++);
                cell.setCellValue(stringIterator.next());
            }
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            wb.write(outputStream);
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String getCellValue(Workbook wb, Cell cell) {
        FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
        CellValue cellValue = evaluator.evaluate(cell);
        if(cellValue == null) {
            return cell.getStringCellValue();
        }
        switch (cellValue.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return cell.getStringCellValue();
        }
    }

}