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
	
	
	@BeforeTest
	public void invokeBrowser() throws IOException, InterruptedException {
		homepage.invokeBrowser();	
	}
	
	@Test
	public void test1() throws IOException, InterruptedException {
		homepage.marketsPage();
		homepage.login();
	}
	
	@AfterTest
	public void closeBrowser() {
		driver.close();
	}
}
