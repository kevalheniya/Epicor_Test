package pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage {

	public WebDriver driver;
	public Properties prop = null;
	public FileInputStream fis = null;
	private Object[][] testData=null;
	Hashtable<String,String> table = null;
	
	public void loadURL() {
		driver.get(prop.getProperty("APPLICATION_URL"));
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	public WebDriver loadChromeDriver() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		return driver;
	}
	public WebDriver loadIEDriver() {
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"\\IEDriverServer.exe");
		driver = new InternetExplorerDriver(capabilities);
		return driver;
	}
	public Properties loadproperties() throws IOException {
		prop = new Properties();
		fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\resource\\Epicor_GL.properties");		
		prop.load(fis);
		return prop;
	}
	
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
		System.out.println(rowCount);
		Row firstRow= sheet.getRow(sheet.getFirstRowNum());
		testData= new Object[rowCount][1];
		int index=0;
		for (int i = 0; i < rowCount; i++) {
			table = new Hashtable<String,String>();
			Row row = sheet.getRow(sheet.getFirstRowNum()+1);
			
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
