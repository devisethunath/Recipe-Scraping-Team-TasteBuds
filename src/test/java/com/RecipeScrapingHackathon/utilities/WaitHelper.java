package com.RecipeScrapingHackathon.utilities;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*Wait Helper Class is used to set explicit wait*/
public class WaitHelper {
	
public WebDriver driver;
	
	public  WaitHelper (WebDriver driver)
	{
		this.driver=driver;
	}
	/*Method to set up explicit wait for element*/
	public void WaitForElement(WebElement element,long timeOutInSeconds)
	{
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

}
