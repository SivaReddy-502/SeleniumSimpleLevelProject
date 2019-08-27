package com.training.pom;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DeleteCatogoryPOM {
	private WebDriver driver; 
	
	public DeleteCatogoryPOM(WebDriver driver) {
		this.driver = driver; 
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="input-username")
	private WebElement userName; 
	
	@FindBy(id="input-password")
	private WebElement password;
	
	@FindBy(xpath="//*[@class='btn btn-primary' and @type='submit']")
	private WebElement loginBtn; 
			
	@FindBy(xpath="//*[@id='catalog']/ul/li[1]/a")
	private WebElement Categorieslink;
	
	@FindBy(xpath="//*[@id='form-category']/div/table/tbody/tr[4]/td[1]")
	private WebElement CategoryListCheckbox;
	
	@FindBy(xpath="//*[@id='content']/div[1]/div/div/button")
	private WebElement RemoveButton;

	
	public void sendUserName(String userName) {
		this.userName.clear();
		this.userName.sendKeys(userName);
	}
	
	public void sendPassword(String password) {
		this.password.clear(); 
		this.password.sendKeys(password); 
	}
	
	public void clickLoginBtn() {
		this.loginBtn.click(); 
	}
		
	public void clickCategorieslink() {
		this.Categorieslink.click();
	}
	
	public void clickCatogoryCheckbox() {
		this.CategoryListCheckbox.click();
	}
	
	public void clickRemovebutton() {
		this.RemoveButton.click();
	}

	
	
	
	
}



