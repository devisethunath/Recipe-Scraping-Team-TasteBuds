package com.RecipeScrapingHackathon.testCases;

import java.io.IOException;
import org.junit.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.RecipeScrapingHackathon.pageObjects.ScrapingPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

public class TC_001ScrapingPage extends BaseClass{
	
	
	
	/* @Description, @Epic ,@Feature,@ Story, @Step, @Severity*  are the Allure
	 * report Annotations 
	 * @Description: We can add detailed description for each test method/class using this annotation
	 * @Epic  :Epics areuseful as placeholders for larger requirements for your project.It can be divided into smaller User stories
	 * /@Feature:   Epic can be further divided to Features and user stories
	 * @Step:   We can annotate any method with any visibility modifier(public,private,protected) with step annotation
	 * @Severity:Severity annotation can have values like BLOCKER,CRITICAL,NORMAL,MINOR,TRIVIAL
	 * 
	 */
	
	
	
	
	@Test
	@Description("This method scrapes the recipesfrom the TarlaDalal Website")
	@Epic("EP001")
	@Feature("Feature1 : WebScraping")
	@Story("Story : Scrape Recipes")
	@Step("Perform webscraping")
	@Severity(SeverityLevel.NORMAL)
	
	
	public void ScrapingPageTest() throws IOException, InterruptedException
	{
		
		
		/* Created object for the Page Object class*/
		ScrapingPage sp=new ScrapingPage(driver);
		driver.get(baseUrl);
		Thread.sleep(5000);
		driver.manage().window().maximize();
		
		/* Calling Method to click RecipeAtoZ link */
		sp.clickRecipeAtoZ();
		String actual_title="Recipe begining with A | Page 1 of 22 | Tarladalal.com";
		Assert.assertEquals(actual_title, driver.getTitle());
		
		/*Call Method to Set up Excel HeaderValues*/
		sp.setupExcelHeader();
		
		/* Method to perform Recipe Scraping */
		sp.recipeScraping();
		
		
	}

}
