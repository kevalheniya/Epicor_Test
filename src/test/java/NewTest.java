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
	Epicor_Page epic=null;
	WebDriver driver = null;
	Properties prop = null;

	@DataProvider
	public Object[][] getData() throws IOException {
		return base.readExcel("GLAccount.xls");
	}

	@BeforeTest
	public void beforeTest() throws IOException {
		driver = base.loadChromeDriver();
		prop = base.loadproperties();
		epic=new Epicor_Page(driver, prop, base);
	}

	@Test(dataProvider = "getData")
	public void Creating_new_General_Ledger(Hashtable<String, String> testData) throws InterruptedException {
		base.loadURL();
		System.out.println(testData.get("GLAccount"));
		System.out.println(testData.get("Description"));
		driver.findElement(By.id("txtUserID")).sendKeys("manager");
		driver.findElement(By.id("txtPassword")).sendKeys("manager");
		System.out.println(driver.getWindowHandle());
		driver.findElement(By.id("btnLogin")).click();
		driver.switchTo().frame("menu");
		Thread.sleep(3000);
		driver.findElement(By.linkText("Epicor Education")).click();
		Thread.sleep(2000);
		String classname = driver.findElement(By.xpath("//*[@id='tree']/ul/li[5]/ul/li[3]/span/span"))
				.getAttribute("class");
		if (classname.contains("tree-expander"))
			epic.clickOnLinkText("Financial Management");
		epic.clickOnLinkText("General Ledger");
		epic.clickOnLinkText("Setup");
		driver.findElement(By.xpath("//*[@id='list']/div/span[15]")).click();
		Thread.sleep(2000);
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		driver.findElement(By.id("glaeGLAccount_dropText")).sendKeys(testData.get("GLAccount"));
		
	}

	@AfterTest
	public void afterTest() {
		// driver.quit();
	}

}
