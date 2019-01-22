package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class read_excel {
	private Object[][] testData=null;
	Hashtable<String,String> table = null;
	
	public Object[][] readExcel(String fileName) throws IOException {
		File file = new File(System.getProperty("user.dir") + "\\src\\main\\java\\resource\\" + fileName);
		FileInputStream inputStream = new FileInputStream(file);
		Workbook Wkbook = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));

		if (fileExtensionName.equals(".xlsx")) {
			Wkbook = new XSSFWorkbook(inputStream);
		}else if (fileExtensionName.equals(".xls")) {
			Wkbook = new HSSFWorkbook(inputStream);
		}
		
		Sheet sheet = Wkbook.getSheetAt(0);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		Row firstRow=sheet.getRow(sheet.getFirstRowNum());

		int index=0;
		for (int i = 0; i < rowCount + 1; i++) {
			table = new Hashtable<String,String>();
			Row row = sheet.getRow(i);
			
			for (int j = 0; j < row.getLastCellNum(); j++) {
				String key = firstRow.getCell(j).getStringCellValue();
				String value = row.getCell(j).getStringCellValue();
				table.put(key, value);
			}
			testData[index][0] = table;
			index ++;
		}
		return testData;
	}
}
