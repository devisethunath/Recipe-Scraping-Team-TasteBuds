package com.RecipeScrapingHackathon.testCases;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.logging.LogManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.*;
//import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import com.RecipeScrapingHackathon.pageObjects.ScrapingPage;
import com.RecipeScrapingHackathon.utilities.ReadConfig;

public class BaseClass {

	public static WebDriver driver;
	public ReadConfig readconfig =new ReadConfig(); /*ReadConfig class object is created */
	public String baseUrl=readconfig.getApplicationURL(); /*Using ReadConfig class baseURL is read from properties file*/

	/*Set up Method to set up browser, loading config file*/
	@Parameters("browser") /*@Parameters annotation is used to pass values to the test methods as arguments using testNG.xml file*/
	@BeforeClass
	public void setup(String br) {
		
		  /* CrossBrowser Testing */
		if (br.equals("chrome"))
		{
			ScrapingPage sp=new ScrapingPage(driver);
			System.setProperty("webdriver.chrome.driver",readconfig.getChromePath());

			//Setting up ChromeOptions
			ChromeOptions chromeOptions = new ChromeOptions();
			/*Headless browser option is set as TRUE to peform headless testing*/
			chromeOptions.setHeadless(true);

			chromeOptions.setImplicitWaitTimeout(Duration.ofSeconds(10));/*Implicit wait set up*/
			driver= new ChromeDriver(chromeOptions);
		}
		else if (br.equals("firefox"))
		{
			ScrapingPage sp=new ScrapingPage(driver);
			System.setProperty("webdriver.gecko.driver",readconfig.getFirefoxPath());

			//Setting up ChromeOptions
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			/*Headless browser option is set as TRUE to peform headless testing*/
			firefoxOptions.setHeadless(true);

			firefoxOptions.setImplicitWaitTimeout(Duration.ofSeconds(10));/*Implicit wait set up*/
			driver= new FirefoxDriver(firefoxOptions);
		}}

	/*Tear down method*/

	@AfterClass
	public void tearDown()
	{
		driver.close();
	}

	public void captureScreen(WebDriver driver, String tname) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Screenshots/" + tname + ".png");
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot taken");

	}
}