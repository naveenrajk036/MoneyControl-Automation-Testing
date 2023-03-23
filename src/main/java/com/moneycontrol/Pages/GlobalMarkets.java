package com.moneycontrol.Pages;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.moneycontrol.Base.Base;
public class GlobalMarkets extends Base {
	
	By globalMarkets = By.xpath("//a[@title='View all Global Markets']");
	By indices = By.xpath("//section[3]/div/div/div/div/table/tbody/tr/td[1]");
	By price = By.xpath("//section[3]/div/div/div/div/table/tbody/tr/td[2]");
	By chgPrice = By.xpath("//section[3]/div/div/div/div/table/tbody/tr/td[3]");
	By chgPercent = By.xpath("//section[3]/div/div/div/div/table/tbody/tr/td[4]");
	By high = By.xpath("//section[3]/div/div/div/div/table/tbody/tr/td[5]");
	By low = By.xpath("//section[3]/div/div/div/div/table/tbody/tr/td[6]");
	By open = By.xpath("//section[3]/div/div/div/div/table/tbody/tr/td[7]");
	By close = By.xpath("//section[3]/div/div/div/div/table/tbody/tr/td[8]");
	By yearlyHigh = By.xpath("//section[3]/div/div/div/div/table/tbody/tr/td[9]");
	By yearlyLow = By.xpath("//section[3]/div/div/div/div/table/tbody/tr/td[10]");
	By adv = By.xpath("//div[@class='advHead']");
	
	@Test
	public void globalMarkets() throws InterruptedException, IOException {
		
		WebElement advEle = driver.findElement(adv);
		javaScript(advEle);
		WebElement globalEle = driver.findElement(globalMarkets);
		globalEle.click();
		Thread.sleep(2000);
		
		List<WebElement> marketEle = driver.findElements(indices);
		List<WebElement> priceEle = driver.findElements(price);
		List<WebElement> chgPriceEle = driver.findElements(chgPrice);
		List<WebElement> percentEle = driver.findElements(chgPercent);
		List<WebElement> highEle = driver.findElements(high);
		List<WebElement> lowEle = driver.findElements(low);
		List<WebElement> openEle = driver.findElements(open);
		List<WebElement> closeEle = driver.findElements(close);
		List<WebElement> yrHighEle = driver.findElements(yearlyHigh);
		List<WebElement> yrLowEle = driver.findElements(yearlyLow);
		
		Map<String,Object[]> dataset = new TreeMap<String,Object[]>();
		dataset.put("1", new Object[] {"Index","Price","Change In Price","Change in %","Day's High","Day's Low","Open Price",
				"Close Price","52 week High","52 week Low"});
		int j=2, k=0;
		for(int i=0;i<marketEle.size();i++) {
			//javaScript(marketEle.get(i));
			 if(i==0 || i==4 || i==8) {
				 
				 String s=Integer.toString(j);
				 dataset.put(s, new Object[] {marketEle.get(i).getText()});
				 System.out.println(marketEle.get(i).getText());
				 j++;
				 continue;
			}
			System.out.println("Index: "+marketEle.get(i).getText() + "	" + "Price: " + priceEle.get(k).getText() + "	" + "Change in Price: "
					+ chgPriceEle.get(k).getText() + "	" + "Change In %: " + percentEle.get(k).getText());
			
			String s=Integer.toString(j);
			
			dataset.put(s, new Object[] {marketEle.get(i).getText(),priceEle.get(k).getText(),chgPriceEle.get(k).getText(),
					percentEle.get(k).getText(), highEle.get(k).getText(),lowEle.get(k).getText(),openEle.get(k).getText(),
					closeEle.get(k).getText(),yrHighEle.get(k).getText(),yrLowEle.get(k).getText()});
			j++;
			k++;
		}
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("GlobalCues");
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
			FileOutputStream writeFile = new FileOutputStream(System.getProperty("user.dir")+"\\Output\\GlobalCues.xlsx");
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

	}
}
