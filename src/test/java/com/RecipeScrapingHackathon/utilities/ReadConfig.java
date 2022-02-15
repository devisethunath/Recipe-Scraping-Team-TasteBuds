package com.RecipeScrapingHackathon.utilities;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;


/*ReadConfig class is used to read properties file */
public class ReadConfig {
	
	Properties pro;
	
	public ReadConfig()
	{
		/*Reading properties file through file input*/
		File src= new File("./Configuration/readconfig.properties");
		try {FileInputStream fis = new FileInputStream(src) ;
		pro= new Properties();
		pro.load(fis);

		} 
		catch (Exception e) {
			System.out.print("exception is" +e.getMessage());
		} 
	}

	/*Method to get Application URL from properties file*/
	public String getApplicationURL()
	{
		String url=pro.getProperty("baseURL");
		return url;
	}
	
	/*Method to get ChromePath from properties file*/
	public String getChromePath()
	{
		String chromepath=pro.getProperty("chromepath");
		return chromepath;
	}

	/*Method to get firefox path from properties file*/
	public String getFirefoxPath()
	{
		String firefoxpath=pro.getProperty("firefoxpath");
		return firefoxpath;
	}
	/*Method to get browser name from properties file*/
	public String getBrowser()
	{
		String browser=pro.getProperty("browser");
		return browser;
	}

}
