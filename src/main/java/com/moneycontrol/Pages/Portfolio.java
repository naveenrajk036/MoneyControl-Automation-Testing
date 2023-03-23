package com.moneycontrol.Pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.moneycontrol.Base.Base;

public class Portfolio extends Base {
	
	By portfolioTab = By.xpath("//nav[@class='navbg']/div/ul/li[5]");
	By stocksPage = By.xpath("//h3[text()='Stocks']");
	//By addStock = By.xpath("//*[@id=\"mc_content\"]/section/section/div[2]/span/div[2]/table/tbody/tr[1]/td[1]/div/span/a[1]");
	By addTrans = By.className("add_transctn");
	By stockName = By.xpath("//*[@id=\"frm_add_transaction\"]/div[1]/h2/div[2]/div[2]/div/input[1]");
	//By stockList = By.xpath("//ul[@class='asset_list_sugg']/li/a[text()='Tata Motors']");
	By quantity = By.xpath("//*[@id=\"frm_add_transaction\"]/div[1]/h2/div[2]/div[5]/div/input");
	By date = By.xpath("//*[@id=\"frm_add_transaction\"]/div[1]/h2/div[2]/div[4]/input");
	By price = By.xpath("//*[@id=\"frm_add_transaction\"]/div[1]/h2/div[2]/div[6]/input");
	By submit = By.xpath("//*[@id='ADD_STOCK']");
	
	public void addStock() throws InterruptedException, IOException {
		driver.findElement(portfolioTab).click();
		hold(addTrans);
		
		driver.findElement(stocksPage).click();
		
		FileInputStream readfile = new FileInputStream(System.getProperty("user.dir")+ "\\src\\test\\resources\\ObjectRepository\\StockAddition.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(readfile);
		XSSFSheet sheet = workbook.getSheet("Buy");
		Row row;
		Cell cell;
		Iterator<Row> rowItr = sheet.iterator();
		while(rowItr.hasNext()) {
			row = rowItr.next();
			driver.findElement(addTrans).click();
			Iterator<Cell> cellItr = row.iterator();
			int i = 1;
			while(cellItr.hasNext()) {
				cell = cellItr.next();
				DataFormatter formatter = new DataFormatter();
				String text = formatter.formatCellValue(cell);
				System.out.println(text);
				if(i==1) {
					driver.findElement(stockName).sendKeys(text);
					By stockList = By.xpath("//ul[@class='asset_list_sugg']/li/a[text()='" + text + "']");
					driver.findElement(stockList).click();
				}
				else if(i==2)
					driver.findElement(quantity).sendKeys(text);
				else if(i==3)
					driver.findElement(price).sendKeys(text);
				else if(i==4)
					driver.findElement(date).sendKeys(text);
				i++;
			}
			
			driver.findElement(submit).click();
			hold(addTrans);
		}
	}
	
	By sell = By.xpath("//*[@id=\"frm_add_transaction\"]/div[1]/h2/div[2]/div[1]/div[2]/select");
	
	public void sellStock() throws IOException {
		driver.findElement(portfolioTab).click();
		driver.findElement(stocksPage).click();
		
		
		FileInputStream readfile = new FileInputStream(System.getProperty("user.dir")+ "\\src\\test\\resources\\ObjectRepository\\Stocks.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(readfile);
		XSSFSheet sheet = workbook.getSheet("Sell");
		Row row;
		Cell cell;
		Iterator<Row> rowItr = sheet.iterator();
		while(rowItr.hasNext()) {
			row = rowItr.next();
			driver.findElement(addTrans).click();
			Iterator<Cell> cellItr = row.iterator();
			int i = 1;
			while(cellItr.hasNext()) {
				Select select = new Select(driver.findElement(sell));
				select.selectByVisibleText("Sell");
				cell = cellItr.next();
				DataFormatter formatter = new DataFormatter();
				String text = formatter.formatCellValue(cell);
				System.out.println(text);
				if(i==1) {
					driver.findElement(stockName).sendKeys(text);
					By stockList = By.xpath("//ul[@class='asset_list_sugg']/li/a[text()='" + text + "']");
					driver.findElement(stockList).click();
				}
				else if(i==2)
					driver.findElement(quantity).sendKeys(text);
				else if(i==3)
					driver.findElement(price).sendKeys(text);
				else if(i==4)
					driver.findElement(date).sendKeys(text);
				i++;
			}
			
			driver.findElement(submit).click();
			hold(addTrans);
		}
	}
}

