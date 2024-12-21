package util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ExcelReader {

    public static Map<String, List<String>> readIngredientsFromExcel(String filePath, String sheetName) throws IOException {
        Map<String, List<String>> ingredientsMap = new HashMap<>();
        ingredientsMap.put("Eliminate", new ArrayList<>());
        ingredientsMap.put("To Add", new ArrayList<>());

        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName); // Access the specified sheet
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet " + sheetName + " not found in Excel file.");
            }

            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) rowIterator.next(); // Skip header row

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                
                // Process "Eliminate" column
                Cell eliminateCell = row.getCell(0);
                if (eliminateCell != null && !eliminateCell.getStringCellValue().trim().isEmpty()) {
                    ingredientsMap.get("Eliminate").add(eliminateCell.getStringCellValue().trim());
                }

                // Process "To Add" column
                Cell addCell = row.getCell(1);
                if (addCell != null && !addCell.getStringCellValue().trim().isEmpty()) {
                    ingredientsMap.get("To Add").add(addCell.getStringCellValue().trim());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("The file was not found: " + filePath);
            throw e;
        } catch (IOException e) {
            System.err.println("Error reading the file: " + filePath);
            throw e;
        }

        return ingredientsMap;
    }
}
