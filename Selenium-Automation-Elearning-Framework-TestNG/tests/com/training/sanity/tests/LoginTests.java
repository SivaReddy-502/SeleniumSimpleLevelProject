package com.training.sanity.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.training.generics.ScreenShot;
import com.training.pom.LoginPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

public class LoginTests {

	private WebDriver driver;
	private String baseUrl;
	private LoginPOM loginPOM;
	private static Properties properties;
	private ScreenShot screenShot;

	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		properties = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/others.properties");
		properties.load(inStream);
	}

	@BeforeMethod
	public void setUp() throws Exception {
		driver = DriverFactory.getDriver(DriverNames.CHROME);
		loginPOM = new LoginPOM(driver); 
		baseUrl = properties.getProperty("baseURL");
		screenShot = new ScreenShot(driver);
		// open the browser 
		driver.get(baseUrl);
		
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
		Thread.sleep(1000);
		//Close the all browsers
		driver.quit();
	}
	@Test
	public void Testcase1() {
		
		//enter valid credentials in Username textbox
		loginPOM.sendUserName("admin");
		//enter valid credentials in Password textbox
		loginPOM.sendPassword("admin@123");
		//click on Login button
		loginPOM.clickLoginBtn(); 
		
		//Verifying Dashboard Title for login successful webpage
		String ExpectedTitle="Dashboard";
		String ActualTitle=driver.getTitle();
		if(ActualTitle.contentEquals(ExpectedTitle)) {
			Assert.assertEquals(ActualTitle, ExpectedTitle);
		}
		else {
			Assert.fail();
		}
		
		screenShot.captureScreenShot("First");
	}
}
