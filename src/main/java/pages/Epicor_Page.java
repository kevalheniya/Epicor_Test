package pages;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Epicor_Page {
	WebDriver driver = null;
	Properties prop = null;
	BasePage base = null;

	public Epicor_Page(WebDriver driver, Properties prop, BasePage base) {
		this.driver = driver;
		this.prop = prop;
		this.base = base;
	}
	public void clickOnLinkText(String link_Text){
		driver.findElement(By.linkText(link_Text)).click();
	}
}
