package com.training.sanity.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.training.generics.ScreenShot;
import com.training.pom.CatloglinksPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

public class CatloglinksTests {

	private WebDriver driver;
	private String baseUrl;
	private CatloglinksPOM catloglinks;
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
		catloglinks = new CatloglinksPOM(driver); 
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
	public void TestCase2() throws InterruptedException {
		
		//enter valid credentials in Username textbox
		catloglinks.sendUserName("admin");
		//enter valid credentials in Password textbox
		catloglinks.sendPassword("admin@123");
		//click on Login button
		catloglinks.clickLoginBtn(); 
		
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
		
		System.out.println("Testcase 2 : Step 1 : Click on Catolog icon and display the list of links");
		
		//Move the element to Catlog icon 
		Actions action=new Actions(driver);
		WebElement obj=driver.findElement(By.xpath("//*[@id='catalog']/a/i"));
		action.moveToElement(obj).build().perform();
		obj.click();
		
		//Actual list of Catlog links
		String[] Actuallistdata={"Categories", "Products", "Recurring Profiles", "Filters", "Attributes", "Options", "Manufacturers", "Downloads", "Reviews", "Information"};
		String Expectedlistdata;	
		List<WebElement> Catloglinks = driver.findElements(By.xpath("//*[@id='catalog']/ul/li/a"));
		System.out.println(Catloglinks.size());
		//Gettting the list of Catlog links
		for(int i=0;i<Catloglinks.size();i++) {
			Expectedlistdata=Catloglinks.get(i).getText();
			System.out.println(Expectedlistdata);
			System.out.println(Actuallistdata[i]);
			if(Expectedlistdata.equals(Actuallistdata[i])) {
				Assert.assertEquals(Actuallistdata[i], Expectedlistdata);
				System.out.println("The Actual list Data "+ Actuallistdata[i] + " and The Expected data list " + Expectedlistdata + "is same hence the testcase pass");
			}
			else
			{
				Assert.fail();
				System.out.println("test case failed");
			}
			
		}	
		
		System.out.println("testcase 2: Step 2 : click on Catogories link in Catlog and display the list of Categories");
		
		// click on categories
		catloglinks.clickCategorieslink();
		
		String CategoryListdata;
		List<WebElement> CategoriesList = driver.findElements(By.xpath("//*[@id='form-category']/div/table/tbody/tr/td"));
		//Getting the list of Categories
		for(int i=0;i<CategoriesList.size();i++) {
			CategoryListdata = CategoriesList.get(i).getText();
			System.out.println(CategoryListdata);
			if(driver.findElement(By.xpath("//*[@id='form-category']/div/table/tbody/tr/td")).isDisplayed()) {
				System.out.println("The Categories page containig list is displayed");
			}
			else {
				Assert.fail();
			}
		}
		
	}
}
