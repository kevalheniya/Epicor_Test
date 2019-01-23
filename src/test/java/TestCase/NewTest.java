package TestCase;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.BasePage;

public class NewTest {
	BasePage base = new BasePage();
	WebDriver driver = null;
	Properties prop = null;

	@DataProvider
	public Object[][] getData() throws IOException {
		return base.readExcel("GLAccount.xls");
	}

	@BeforeTest
	public void beforeTest() throws IOException {
		driver = base.loadChromeDriver();
//		driver = base.loadIEDriver();
		prop = base.loadproperties();
	}

	@Test(dataProvider = "getData")
	public void Creating_new_General_Ledger(Hashtable<String, String> testData) throws InterruptedException {
		/*
		 * Open Internet explorer 11 and type below given link URL: (
		 * http://125.18.224.122/ERP10Demo-EWA)
		 */
		base.loadURL();
		/*
		 * Enter credentials and click on ->
		 */
		driver.findElement(By.id(prop.getProperty("UserID"))).sendKeys(testData.get("UserID"));
		driver.findElement(By.id(prop.getProperty("Password"))).sendKeys(testData.get("Password"));
		driver.findElement(By.id("btnLogin")).click();
		
		/*
		 * Handle window and iframe 
		 */
		base.windowHandle();
		base.waitfor(3000);
		driver.switchTo().frame(prop.getProperty("Home_Menu_Iframe"));
		base.waitfor(3000);
		/*
		 * Click on Epicor EDUCATION, 
		 * followed by Main, 
		 * followed by Financial Management, 
		 * followed by General Ledger, 
		 * followed by Setup, 
		 * followed by General Ledger Account form.
		 */
		driver.findElement(By.linkText("Epicor Education")).click();
		base.waitfor(2000);
		String classname = driver.findElement(By.xpath(prop.getProperty("Home_Menu_Main"))).getAttribute("class");
		if (classname.contains("tree-expander"))
			base.clickOnLinkText("Financial Management");
		base.waitfor(1000);
		base.clickOnLinkText("General Ledger");
		base.waitfor(1000);
		base.clickOnLinkText("Setup");
		base.waitfor(3000);
		driver.findElement(By.xpath(prop.getProperty("Home_General_Ledger_Account"))).click();
		base.waitfor(2000);
		base.windowHandle();
		/*
		 * Click on New
		 */
		driver.findElement(By.cssSelector(prop.getProperty("Header_GL_AC_page_New"))).click();
		base.waitfor(3000);
		/*
		 * enter GL account (1238-01-20)
		 */
		driver.findElement(By.id(prop.getProperty("GL_AC_page_GLAccount_Input"))).sendKeys(testData.get("GLAccount"));
		base.waitfor(3000);
		/*
		 * Click on save button on toolbar and Click on Yes
		 */
		driver.findElement(By.cssSelector(prop.getProperty("Header_GL_AC_page_Save"))).click();
		base.waitfor(3000);
		driver.switchTo().alert().accept();
		base.waitfor(3000);

		/*
		 * Search Existing GL Account Click on GL Account
		 */
		driver.findElement(By.id(prop.getProperty("GL_AC_page_GL_Account_Button"))).click();
		/*
		 * Click on Natural accounts starting at, then enter 1238-01-20 (GL
		 * Account)
		 */
		driver.switchTo().activeElement();
		driver.findElement(By.id("txtNaturalAccount")).sendKeys(testData.get("GLAccount"));
		driver.findElement(By.id(prop.getProperty("Search_button"))).click();
		/*
		 * Click on Search, GL Account which was created should reflect in the
		 * search list, Click on OK
		 */
		driver.findElement(By.id(prop.getProperty("Search_Ok_button"))).click();
		/*
		 * click on Menu
		 */
		driver.findElement(By.id(prop.getProperty("Header_GL_AC_page_Menu"))).click();
		/*
		 * Click on File
		 */
		driver.findElement(By.cssSelector(prop.getProperty("Header_GL_AC_page_Menu_File"))).click();

		/*
		 * Click on Delete
		 */
		driver.findElement(By.cssSelector(prop.getProperty("Header_GL_AC_page_Menu_Delete"))).click();
		/*
		 * Click on Yes
		 */
		base.waitfor(3000);
		driver.switchTo().alert().accept();

	}

	@AfterTest
	public void afterTest() {
		// driver.quit();
	}

}
