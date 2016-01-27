package com.bork.util.excel;

import com.bork.util.Constants;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.DateFormatConverter;

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

    private static final Map<Locale, String> LOCALES;
    static {
        Map<Locale, String> temp = new HashMap<>();
        temp.put(Locale.GERMAN, "dd.MM.yy");
        temp.put(Locale.FRENCH, "yyyy-MM-dd");
        temp.put(Locale.US, "yyyy/MM/dd");
        LOCALES = Collections.unmodifiableMap(temp);
    }
    private static final String FORMATTEDDATE = "\\d{1,2}/\\d{1,2}/\\d{2,4}";
    private static final String YYYYMMDD = "\\d{4}/\\d{2}/\\d{2}";
    private static final String HHMM = "(([01]?[0-9])|(2[0-3])):\\d{2}";
    private static final String HHMMSS = HHMM + ":\\d{2}";
    private static final String NUMBER = "\\d+\\.?\\d*";

    public static Set<List<String>> getSheetContents(Workbook wb, Sheet sheet, Locale locale) {
        Set<List<String>> sheetContents = new HashSet<>();
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            List<String> rowContent = new ArrayList<>();
            Row currentRow = rowIterator.next();
            Iterator<Cell> cellIterator = currentRow.cellIterator();
            while (cellIterator.hasNext()) {
                Cell currentCell = cellIterator.next();
                String content = getCellValue(wb, currentCell, locale);
                rowContent.add(content);
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
                    DataFormatter formatter = new DataFormatter(locale);
                    String formattedDate = formatter.formatCellValue(cell, evaluator);
                    if(formattedDate.matches(FORMATTEDDATE)) {
                        return new SimpleDateFormat("yyyy/MM/dd").format(cell.getDateCellValue());
                    }
                    return formattedDate;
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
                writeToCell(cell, stringIterator.next(), wb);
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

    private static void writeToCell(Cell cell, String stringValue, Workbook wb) {
        if(stringValue.matches(YYYYMMDD)) {
            String pattern = DateFormatConverter.convert(Constants.LOCALE, LOCALES.get(Constants.LOCALE));
            CellStyle style = wb.createCellStyle();
            DataFormat format = wb.createDataFormat();
            style.setDataFormat(format.getFormat(pattern));
            cell.setCellValue(DateUtil.parseYYYYMMDDDate(stringValue));
            cell.setCellStyle(style);
        } else if(stringValue.matches(HHMM) || stringValue.matches(HHMMSS)) {
            String pattern;
            if(stringValue.matches(HHMM)) {
                pattern = DateFormatConverter.convert(Constants.LOCALE, "HH:mm");
            } else {
                pattern = DateFormatConverter.convert(Constants.LOCALE, "HH:mm:ss");
            }
            CellStyle style = wb.createCellStyle();
            DataFormat format = wb.createDataFormat();
            style.setDataFormat(format.getFormat(pattern));
            cell.setCellValue(DateUtil.convertTime(stringValue));
            cell.setCellStyle(style);
        } else if(stringValue.matches(NUMBER)) {
            cell.setCellValue(Double.parseDouble(stringValue));
        } else {
            cell.setCellValue(stringValue);
        }
    }

}