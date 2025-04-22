package API_utils;


import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

	public class ExcelUtil {

	    private Workbook workbook;

	    public  ExcelUtil(String filePath) throws IOException {
	        FileInputStream fis = new FileInputStream(filePath);
	        workbook = WorkbookFactory.create(fis);
	    }

	    public String getCellData(String sheetName, int rowNum, int colNum) {
	        Sheet sheet = workbook.getSheet(sheetName);
	        if (sheet == null) {
	            throw new IllegalArgumentException("Sheet " + sheetName + " does not exist in the workbook.");
	        }
	        Row row = sheet.getRow(rowNum);
	        if (row == null) {
	            return null;
	        }
	        Cell cell = row.getCell(colNum);
	        if (cell == null) {
	            return null;
	        }
	        return cell.toString();
	    }

	    public int getRowCount(String sheetName) {
	        Sheet sheet = workbook.getSheet(sheetName);
	        return sheet == null ? 0 : sheet.getLastRowNum() + 1;
	    }

	    public void close() throws IOException {
	        if (workbook != null) {
	            workbook.close();
	        }
	    }
	}


