package com.moneycontrol.Testcases;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.moneycontrol.Base.Base;
import com.moneycontrol.Pages.*;

public class TestRun extends Base {
	WebDriver driver;
	HomePage homepage = new HomePage();
	GlobalMarkets gm = new GlobalMarkets();
	Portfolio portfolio = new Portfolio();
	
	@BeforeTest
	public void invokeBrowser() throws IOException, InterruptedException {
		homepage.invokeBrowser();	
	}
	
	@Test
	public void test1() throws IOException, InterruptedException {
		homepage.marketsPage();
		homepage.login();
	}
	
	@Test(dependsOnMethods = { "test1" })
	public void test3() throws InterruptedException, IOException {
		portfolio.addStock();
		portfolio.sellStock();
	}
	
	
	@Test(enabled = false)
	public void test2() throws IOException, InterruptedException {
		homepage.marketsPage();
		gm.globalMarkets();
		
	}
	
	@AfterTest
	public void closeBrowser() {
		tearDown();
	}
}
