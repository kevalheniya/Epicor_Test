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
import pages.Epicor_Page;

public class NewTest {
	BasePage base = new BasePage();
	Epicor_Page epic = null;
	WebDriver driver = null;
	Properties prop = null;

	@DataProvider
	public Object[][] getData() throws IOException {
		return base.readExcel("GLAccount.xls");
	}

	@BeforeTest
	public void beforeTest() throws IOException {
		// driver = base.loadChromeDriver();
		driver = base.loadIEDriver();
		prop = base.loadproperties();
		epic = new Epicor_Page(driver, prop, base);
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
		driver.findElement(By.id("txtUserID")).sendKeys("manager");
		driver.findElement(By.id("txtPassword")).sendKeys("manager");
		driver.findElement(By.id("btnLogin")).click();

		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
			driver.manage().window().maximize();
		}
		Thread.sleep(3000);
		driver.switchTo().frame("menu");
		Thread.sleep(3000);
		/*
		 * Click on Epicor EDUCATION, followed by Main, followed by Financial
		 * Management, followed by General Ledger, followed by Setup, followed
		 * by General Ledger Account form.
		 */
		driver.findElement(By.linkText("Epicor Education")).click();
		Thread.sleep(2000);
		String classname = driver.findElement(By.xpath("//*[@id='tree']/ul/li[5]/ul/li[3]/span/span"))
				.getAttribute("class");
		if (classname.contains("tree-expander"))
			epic.clickOnLinkText("Financial Management");
		Thread.sleep(1000);
		epic.clickOnLinkText("General Ledger");
		Thread.sleep(1000);
		epic.clickOnLinkText("Setup");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id='list']/div/span[15]")).click();
		Thread.sleep(2000);
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
			driver.manage().window().maximize();
		}
		/*
		 * Click on New
		 */
		driver.findElement(By.cssSelector("button[data-key='NewTool']")).click();
		Thread.sleep(3000);
		/*
		 * enter GL account (1238-01-20)
		 */
		driver.findElement(By.id("glaeGLAccount_dropText")).sendKeys(testData.get("GLAccount"));
		Thread.sleep(3000);
		/*
		 * Click on save button on toolbar and Click on Yes
		 */
		driver.findElement(By.cssSelector("button[data-key='SaveTool']")).click();
		Thread.sleep(3000);
		driver.switchTo().alert().accept();
		Thread.sleep(3000);

		/*
		 * Search Existing GL Account Click on GL Account
		 */
		driver.findElement(By.id("btnKeyField")).click();
		/*
		 * Click on Natural accounts starting at, then enter 1238-01-20 (GL
		 * Account)
		 */
		driver.switchTo().activeElement();
		driver.findElement(By.id("txtNaturalAccount")).sendKeys(testData.get("GLAccount"));
		driver.findElement(By.id("btnSearch")).click();
		/*
		 * Click on Search, GL Account which was created should reflect in the
		 * search list, Click on OK
		 */
		driver.findElement(By.id(prop.getProperty("Search_Ok_button"))).click();
		/*
		 * click on Menu
		 */
		driver.findElement(By.id(prop.getProperty("Menu"))).click();
		/*
		 * Click on File
		 */
		driver.findElement(By.cssSelector(prop.getProperty("Menu_File"))).click();

		/*
		 * Click on Delete
		 */
		driver.findElement(By.cssSelector(prop.getProperty("Menu_delete"))).click();
		/*
		 * Click on Yes
		 */
		Thread.sleep(3000);
		driver.switchTo().alert().accept();

	}

	@AfterTest
	public void afterTest() {
		// driver.quit();
	}

}
