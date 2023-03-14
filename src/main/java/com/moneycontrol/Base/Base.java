package com.moneycontrol.Base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Base {

	public static WebDriver driver;
	public static Properties prop;
	WebDriverWait wait;
	JavascriptExecutor js;
	
	
	By mainWeb = By.xpath("//span/a");
	By popup = By.id("wzrk-cancel");
	public void invokeBrowser() throws IOException, InterruptedException {
		prop = getProperties();
		String browserName = prop.getProperty("browser");
		if(browserName.equalsIgnoreCase("Chrome")) {
			System.getProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if(browserName.equalsIgnoreCase("Edge")) {
			System.getProperty("webdriver.edge.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\Drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
		}
		wait = new WebDriverWait(driver,Duration.ofSeconds(50));
		driver.manage().window().maximize();
		String website = prop.getProperty("url");
		driver.get(website);
		
		driver.findElement(mainWeb).click();
		Thread.sleep(2000);
		driver.findElement(popup).click();
	}
	
	public Properties getProperties() throws IOException {
		prop = new Properties();
		FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\ObjectRepository\\configProperties.properties");
		prop.load(file);
		return prop;
	}
	
	public void explicitWait(By xpath) {
		wait.until(ExpectedConditions.elementToBeClickable(xpath));
	}
	
	
	public void javaScript(WebElement element) {
		js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
		
	}
	public void tearDown() {
		driver.close();
	}
}
