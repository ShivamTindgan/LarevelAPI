package api.utilities;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class XLUtility {

    public FileInputStream fi;
    public FileOutputStream fo;
    public XSSFWorkbook workbook;
    public XSSFSheet sheet;
    public XSSFRow row;
    public XSSFCell cell;
    public CellStyle style;
    String path;

    // Constructor
    public XLUtility(String path) {
        this.path = path;
    }

    // Get Row Count
    public int getRowCount(String sheetName) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getLastRowNum();

        workbook.close();
        fi.close();

        return rowCount;
    }

    // Get Cell Count
    public int getCellCount(String sheetName, int rownum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);

        int cellCount = row.getLastCellNum();

        workbook.close();
        fi.close();

        return cellCount;
    }

    // Get Cell Data
    public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        cell = row.getCell(colnum);

        String data;
        try {
            data = cell.toString();
        } catch (Exception e) {
            data = "";
        }

        workbook.close();
        fi.close();

        return data;
    }

    // Set Cell Data
    public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {

        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);

        row = sheet.getRow(rownum);
        if (row == null)
            row = sheet.createRow(rownum);

        cell = row.createCell(colnum);
        cell.setCellValue(data);

        fo = new FileOutputStream(path);
        workbook.write(fo);

        workbook.close();
        fi.close();
        fo.close();
    }
}
