package com.moneycontrol.Pages;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.moneycontrol.Base.Base;

public class HomePage extends Base {
	
	
	
	By user = By.xpath("//a[@class=	'userlink']");
	By login = By.xpath("//a[@title='Log In']");
	By loginFrame = By.xpath("//iframe[@id='myframe']");
	By email = By.xpath("//input[@placeholder='Email or Mobile Number or User ID']");
	By otp = By.id("USE_OTP_BTN");
	By pswd = By.xpath("//div[@class='textfieldbox PR usepwd']/input[@id='pwd']");
	By loginBtn = By.id("ACCT_LOGIN_SUBMIT");
	By username = By.xpath("//span[@class='usr_nm']");
	
	
	@Test
	public void login() throws IOException, InterruptedException {
		
		
		
		
		Actions action = new Actions(driver);
		
		action.moveToElement(driver.findElement(user)).build().perform();
		driver.findElement(login).click();
		driver.switchTo().frame(driver.findElement(loginFrame));
		String emailID = prop.getProperty("emailID");
		driver.findElement(email).sendKeys(emailID);
		String pass = prop.getProperty("password");
		
		//For login via otp
		/*driver.findElement(otp).click();
		Scanner sc = new Scanner(System.in);
		String enterOTP = sc.nextLine();*/
		driver.findElement(pswd).sendKeys(pass);
		Thread.sleep(2000);
		driver.findElement(loginBtn).click();
		//Thread.sleep(10000);
		driver.switchTo().defaultContent();
		System.out.println(driver.getTitle());
		String accName = driver.findElement(username).getText();
		String profileName = System.getProperty("profile");
		Assert.assertEquals(profileName, accName);
		//tearDown(); 
	}
	
	By markets = By.xpath("//nav[@class='navbg']/div/ul/li[2]/a");
	By home = By.xpath("//*[@id=\"mc_mainWrapper\"]/header/div[1]/div[3]/nav/div/ul/li[2]/div/div/ul/li[1]/ul/li[1]/a");
	By globalIndices = By.xpath("//h3[@class='tplhead MB5']");
	By index = By.xpath("//div[@class='MR5 clearfix']/table/tbody/tr/td[1]/h3/a");
	By price = By.xpath("//div[@class='MR5 clearfix']/table/tbody/tr/td[2]/b");
	By chgInPrice = By.xpath("//div[@class='MR5 clearfix']/table/tbody/tr/td[3]");
	By chgInPercent = By.xpath("//div[@class='MR5 clearfix']/table/tbody/tr/td[4]");

	
	public void marketsPage() throws IOException, InterruptedException {
		List<WebElement> indexElement = driver.findElements(index);
		List<WebElement> priceElement = driver.findElements(price);
		List<WebElement> chgPriceEle = driver.findElements(chgInPrice);
		List<WebElement> chgPercentEle = driver.findElements(chgInPercent);
		javaScript(indexElement.get(0));
		Map<String,Object[]> dataset = new TreeMap<String,Object[]>();
		dataset.put("1", new Object[] {"Index","Price","Change In Price","Change in %"});
		int j=2; 
		for(int i=0;i<indexElement.size();i++) {
			javaScript(indexElement.get(i));
			System.out.println("Index: "+indexElement.get(i).getText() + "	" + "Price: " + priceElement.get(i).getText() + "	" + "Change in Price: "
					+ chgPriceEle.get(i).getText() + "	" + "Change In %: " + chgPercentEle.get(i).getText());
			String s=Integer.toString(j);
			
			dataset.put(s, new Object[] {indexElement.get(i).getText(),priceElement.get(i).getText(),chgPriceEle.get(i).getText(),
					chgPercentEle.get(i).getText()});
			j++;
		}
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("MarketData");
		Set<String> set = dataset.keySet();
		int rownum = 0;
		for(String key:set) {
			Row row = sheet.createRow(rownum++);
			Object[] data = dataset.get(key);
			int cellnum=0;
			for(Object value:data) {
				Cell cell = row.createCell(cellnum++);
				if(value instanceof String)
					cell.setCellValue((String)value);
				else if(value instanceof Integer)
					cell.setCellValue((Integer)value);
			}
		}
		try {
			FileOutputStream writeFile = new FileOutputStream(System.getProperty("user.dir")+"\\Output\\MarketData.xlsx");
			workbook.write(writeFile);
			writeFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Thread.sleep(5000);
		
		//tearDown();
	}
	
}
