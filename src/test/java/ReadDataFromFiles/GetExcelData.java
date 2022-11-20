package ReadDataFromFiles;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class GetExcelData {

    public static Object[][] getDataFromExcel(String fileName, String sheetName) {
        Object[][] data = null;
        XSSFWorkbook wb;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            wb = new XSSFWorkbook(fis);
            Sheet sh = wb.getSheet(sheetName);
            Row row = sh.getRow(0);
            int noOfRows = sh.getPhysicalNumberOfRows();
            System.out.println(noOfRows);
            int noOfCols = row.getLastCellNum();
            System.out.println(noOfCols);
            Cell cell;
            data = new Object[noOfRows - 1][noOfCols];
            System.out.println(data.toString());
            for (int i = 1; i <= noOfRows - 1; i++) {
                for (int j = 0; j < noOfCols; j++) {
                    row = sh.getRow(i);
                    cell = row.getCell(j);
                    switch (cell.getCellType()) {
                        case STRING:
                            data[i - 1][j] = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            data[i - 1][j] = (long)cell.getNumericCellValue();
                            break;
                        case BLANK:
                            data[i - 1][j] = "";
                            break;
                        case BOOLEAN:
                            data[i-1][j]= cell.getBooleanCellValue();
                            break;
                        default:
                            data[i - 1][j] = null;
                            break;
                    }

                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("error message : " + e.getMessage());
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return data;
    }


}
