package TestCase;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
//		driver = base.loadChromeDriver();
		driver = base.loadIEDriver();
		prop = base.loadproperties();
	}

	@Test(dataProvider = "getData")
	public void Creating_new_General_Ledger(Hashtable<String, String> testData)
			throws InterruptedException, AWTException {
		/*
		 * STEP 1 Open Internet explorer 11 and type below given link URL: (
		 * http://125.18.224.122/ERP10Demo-EWA)
		 */
		base.loadURL();

		/*
		 * STEP 2 Enter credentials and click on ->
		 */
		driver.findElement(By.id(prop.getProperty("UserID"))).sendKeys(testData.get("UserID"));
		driver.findElement(By.id(prop.getProperty("Password"))).sendKeys(testData.get("Password"));
		driver.findElement(By.id("btnLogin")).click();
		base.waitfor(3000);
		driver.switchTo().frame(prop.getProperty("Home_Menu_Iframe"));

		/*
		 * STEP 3 Click on Epicor EDUCATION, followed by Main, followed by Financial
		 * Management, followed by General Ledger, followed by Setup, followed by
		 * General Ledger Account form.
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
		base.waitfor(5000);

		/*
		 * STEP 4 enter GL account (1238-01-20) and click on Yes to create new record
		 */
		driver.findElement(By.id(prop.getProperty("GL_AC_page_GLAccount_Input"))).sendKeys(testData.get("GLAccount"),
				Keys.TAB);
		base.waitfor(3000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		base.waitfor(3000);

		/*
		 * STEP 5, 6, 7 Click on New button on toolbar Enter GL account (1238-01-20)
		 * Click on save button on toolbar and Duplicate Entry pop up appears Click on
		 * Yes
		 */
		driver.findElement(By.cssSelector(prop.getProperty("Header_GL_AC_page_New"))).click();
		base.waitfor(2000);
		driver.findElement(By.id(prop.getProperty("GL_AC_page_GLAccount_Input"))).sendKeys(testData.get("GLAccount"));
		base.waitfor(2000);
		driver.findElement(By.cssSelector(prop.getProperty("Header_GL_AC_page_Save"))).click();
		base.waitfor(3000);
		robot.keyPress(KeyEvent.VK_ENTER);

		/*
		 * STEP 8 Search Existing GL Account Click on GL Account
		 */
//		driver.findElement(By.id(prop.getProperty("GL_AC_page_GL_Account_Button"))).click();

		/*
		 * STEP 9 Click on Natural accounts starting at, then enter 1238-01-20 (GL
		 * Account)
		 */
		/*
		 * base.waitfor(3000); System.out.println(driver.getCurrentUrl());
		 * driver.findElement(By.id("txtNaturalAccount")).sendKeys(testData.get(
		 * "GLAccount"));
		 * driver.findElement(By.id(prop.getProperty("Search_button"))).click();
		 */
		/*
		 * Click on Search, GL Account which was created should reflect in the search
		 * list, Click on OK
		 */
//		driver.findElement(By.id(prop.getProperty("Search_Ok_button"))).click();

		/*
		 * STEP 11 Click on Menu Click on File
		 * 
		 * STEP 12 Click on Delete
		 *
		 * STEP 13 Click on Yes
		 */
		List<WebElement> item = driver.findElements(By.cssSelector("[class='TreeItem']"));
		for (int i = 0; i < item.size(); i++) {
			driver.findElement(By.id(prop.getProperty("Header_GL_AC_page_Menu"))).click();
			base.waitfor(2000);
			driver.findElement(By.cssSelector(prop.getProperty("Header_GL_AC_page_Menu_File"))).click();
			base.waitfor(2000);
			driver.findElement(By.cssSelector(prop.getProperty("Header_GL_AC_page_Menu_Delete"))).click();
			base.waitfor(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
		}

	}

	@AfterTest
	public void afterTest() {
		// driver.quit();
	}

}
