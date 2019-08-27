package com.training.sanity.tests;

import static org.testng.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.Alert;
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
import com.training.pom.DeleteCatogoryPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

public class DeleteCatogoryTests {

	private WebDriver driver;
	private String baseUrl;
	private DeleteCatogoryPOM deletgecatogorytests;
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
		deletgecatogorytests = new DeleteCatogoryPOM(driver); 
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
	public void TestCase3() throws InterruptedException {
		
		//enter valid credentials in Username textbox
		deletgecatogorytests.sendUserName("admin");
		//enter valid credentials in Password textbox
		deletgecatogorytests.sendPassword("admin@123");
		//click on Login button
		deletgecatogorytests.clickLoginBtn(); 
		
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
		
		System.out.println("Testcase 3 : Step 1 : Click on Catolog icon and display the list of links");
		
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
		
		System.out.println("testcase 3: Step 2 : click on Catogories link in Catlog and display the list of Categories");
		
		// click on categories
		deletgecatogorytests.clickCategorieslink();
		
		String CategoryListdata;
		List<WebElement> CategoriesList = driver.findElements(By.xpath("//*[@id='form-category']/div/table/tbody/tr/td"));
		Integer BeforeDeletingCategorysize= CategoriesList.size();
		System.out.println("The Category list size is "+BeforeDeletingCategorysize);
		//Getting the list of Categories
		for(int i=0;i<CategoriesList.size();i++) {
			CategoryListdata = CategoriesList.get(i).getText();
			System.out.println(CategoryListdata);
		}
		
		
		System.out.println("testcase 3: Step 3 :Click the checkbox for Catogeries list");
		//Click the checkbox for Categories list
		deletgecatogorytests.clickCatogoryCheckbox();
		
		System.out.println("testcase 3: Step 4 : Click on Remove button");
		//Click the Remove button 
		deletgecatogorytests.clickRemovebutton();
		
			
		Alert alert=driver.switchTo().alert();
		
		String AlertText=alert.getText();
		System.out.println("The Alert message is " + AlertText);
		
		System.out.println("testcase 3: Step 5 : Click on Ok button");
		//Click on Ok button for Alert message
		alert.accept();
		
		Thread.sleep(2000);
		
		//Getting Delete message 
		String msg=driver.findElement(By.xpath("//*[@id='content']/div[2]/div[1]")).getText();
		System.out.println("The Delete success  message is " + msg);
		
		System.out.println("Verifying the size after deleting record");
		//Verifying the size after deleting record
		List<WebElement> AfterDeletigCategoriesList = driver.findElements(By.xpath("//*[@id='form-category']/div/table/tbody/tr/td"));
		Integer AfterDeletingCategorysize=AfterDeletigCategoriesList.size();//BeforeDeletingCategorysize
		if(AfterDeletingCategorysize != BeforeDeletingCategorysize) {
			System.out.println("The Before Deleting list size is "+BeforeDeletingCategorysize+"After Deleting list size is"+AfterDeletingCategorysize);
		}
		else {
			Assert.fail();
		}
		
		System.out.println("Verifying the message after deleted is Success: You have modified categories! ");
		
		//Verifying the message after deleted is Success: You have modified categories!
		if(driver.findElement(By.xpath("//*[@id='content']/div[2]/div[1]")).isDisplayed())	{
			System.out.println("Success: You have modified categories! Message should be displayed form category list");
		}
		else {
			Assert.fail();
		}
	}
}	
