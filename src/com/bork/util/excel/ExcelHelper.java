package com.bork.util.excel;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * CSVDiff
 *
 * @author Konstantin Bork
 * @version 0.1
 * @created 12/17/2015
 * <p>
 * §DESCRIPTION§
 */

public class ExcelHelper {

    public static Set<List<String>> getSheetContents(Workbook wb, Sheet sheet, Locale locale) {
        Set<List<String>> sheetContents = new HashSet<>();
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            List<String> rowContent = new ArrayList<>();
            Row currentRow = rowIterator.next();
            Iterator<Cell> cellIterator = currentRow.cellIterator();
            while (cellIterator.hasNext()) {
                Cell currentCell = cellIterator.next();
                rowContent.add(getCellValue(wb, currentCell, locale));
            }
            sheetContents.add(rowContent);
        }
        return sheetContents;
    }

    private static String getCellValue(Workbook wb, Cell cell, Locale locale) {
        FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
        CellValue cellValue = evaluator.evaluate(cell);
        if (cellValue == null) {
            return cell.getStringCellValue();
        }
        switch (cellValue.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)) {
                    DataFormatter formatter = new DataFormatter();
                    return formatter.formatCellValue(cell, evaluator);
                }
                return String.valueOf(cell.getNumericCellValue());
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return cell.getStringCellValue();
        }
    }

    public static boolean writeFile(Iterator<List<String>> listIterator, Sheet sheet, int i, File file, Workbook wb) {
        while (listIterator.hasNext()) {
            List<String> currentRow = listIterator.next();
            Row row = sheet.createRow(i++);
            Iterator<String> stringIterator = currentRow.iterator();
            int j = 0;
            while (stringIterator.hasNext()) {
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

    private static String parseDate(Date date, Locale locale) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        if(locale == Locale.FRANCE) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        } else if(locale == Locale.US) {
            dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        }
        return dateFormat.format(date);
    }

}