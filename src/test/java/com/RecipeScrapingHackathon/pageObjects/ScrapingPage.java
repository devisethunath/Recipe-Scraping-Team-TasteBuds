package com.RecipeScrapingHackathon.pageObjects;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.RecipeScrapingHackathon.testCases.BaseClass;
import com.RecipeScrapingHackathon.utilities.WaitHelper;
import com.RecipeScrapingHackathon.utilities.XLUtils;

public class ScrapingPage extends BaseClass {

	WebDriver ldriver;
	WaitHelper waithelper;
	public WebElement cate=null;
	WebDriver driver=null;
	public String category=null;
	public String imageLink=null;
	public String protein=null;
	public String energy=null;
	public String carbo=null;
	public String fib=null;
	public String fat=null;
	public String cholesterol=null;
	public String sodium=null;
	public int count ;
	public char letter;
	public int pageCount;
	static int b=0;

	/* Constructor to get the driver instances from the main class 
	 * in test layer  and to initialise webelements(page objects) 
	 * declared in page class using page factory*/

	public ScrapingPage(WebDriver rdriver)
	{
		ldriver=rdriver;
		PageFactory.initElements(rdriver, this);
		waithelper=new WaitHelper(ldriver);
	}

	/* @FindBy annotation used in Page Factory to locate and 
	 * declarewebelemnts  using different locators*/

	@FindBy(xpath="//a[@title='Recipea A to Z']")

	WebElement lnkRecipeAtoZ;

	@FindBy(css="div:nth-child(1) div.breadcrumb span.breadcrumb-link-wrap:nth-child(9) a.cattraillinks:nth-child(1) > span:nth-child(1)")
	@CacheLookup
	WebElement txtCate1;

	@FindBy(className="rcc_recipename")
	List<WebElement> Recipes;

	@FindBy(className="rcc_recipename")
	List<WebElement> Recipes1;

	@FindBy(xpath="/html[1]/body[1]/div[2]/form[1]/div[3]/div[2]/div[1]/div[1]/div[2]")
	WebElement pageNos;

	@FindBy(css="#ctl00_cntrightpanel_imgRecipe")
	WebElement image;

	@FindBy(css="div:nth-child(1) div.breadcrumb span.breadcrumb-link-wrap:nth-child(7) a.cattraillinks:nth-child(1) > span:nth-child(1)")
	WebElement txtCate;

	@FindBy(css="#rcpinglist")
	WebElement txtIngredients;

	@FindBy(css="#recipe_small_steps")
	WebElement txtMethod;

	@FindBy(xpath="//body[1]/div[2]/form[1]/div[1]/div[2]/div[1]/main[1]/div[1]/div[1]/article[1]/div[2]/div[19]/div[1]/div[1]/span[1]/table[1]/tbody[1]/tr[1]/td[2]")
	WebElement txtEnergy;

	@FindBy(xpath="//body[1]/div[2]/form[1]/div[1]/div[2]/div[1]/main[1]/div[1]/div[1]/article[1]/div[2]/div[19]/div[1]/div[1]/span[1]/table[1]/tbody[1]/tr[2]/td[2]")
	WebElement txtProtein;

	@FindBy(xpath="//body[1]/div[2]/form[1]/div[1]/div[2]/div[1]/main[1]/div[1]/div[1]/article[1]/div[2]/div[19]/div[1]/div[1]/span[1]/table[1]/tbody[1]/tr[2]/td[2]")
	WebElement txtCarbo;

	@FindBy(xpath="//tbody/tr[4]/td[2]")
	WebElement txtFiber;

	@FindBy(xpath="//tbody/tr[5]/td[2]")
	WebElement txtFat;

	@FindBy(xpath="//tbody/tr[6]/td[2]")
	WebElement txtCholesterol;

	@FindBy(xpath="//tbody/tr[7]/td[2]")
	WebElement txtSodium;

	/* Method which contains code to performs Recipe Scraping action */

	public void recipeScraping() throws InterruptedException, IOException
	{
		/*Adding explicit webdriver wait*/
		WebDriverWait mywait=new WebDriverWait(ldriver,Duration.ofSeconds(15));

		/*This for loop ,  loops throgh each alphabet to get recipes starting
		 *  with that particular alphabet */
		for (char alphabet='A';alphabet<'B';++alphabet)
		{
			/* Code to skip the alphabet X since there is no recipes starting with 'X'*/
			if (alphabet=='X')
			{
				continue;
			}

			System.out.println(alphabet); 

			/* Code to click on each alphabet Link*/
			String linkTxt=String.valueOf(alphabet);
			ldriver.findElement(By.linkText(linkTxt)).click();

			/*Code to get the number of pages under each alphabet link*/
			List<WebElement> pageCount=pageNos.findElements(By.tagName("a"));
			System.out.println(pageCount.size()+"  page nos to scrape recipes");

			//Code To click on each page under each alphabet link
			for (int p=1;p<2;p++)//pageCount.size()
			{
				System.out.println(p);
				String text=String.valueOf(p);

				/*Clicks on each page numbers.Try Catch Block is used to handle unchecked exceptions 
				 * like Stale element reference exception or element not visible exception*/
				try
				{
					mywait.until(ExpectedConditions.visibilityOf(ldriver.findElement(By.linkText(text)))).click();
				}
				catch(Exception e)
				{
					continue;
				}

				/*To click on each recipes in the current page*/
				for(int i=0;i<Recipes.size();i++)

				{
					System.out.println("recipe no" +i);
					int RowNum=i+1+b;
					/*All the recipes in the current page is identified*/
					List<WebElement>Recipes1=mywait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("rcc_recipename")));

					/*Getting the count of total recipes in the particular page*/
					count=Recipes1.size();

					/*Getting the recipe title*/
					String title=Recipes1.get(i).getText();

					//Write title to excel sheet
					XLUtils.setCellData("Sheet1", RowNum, 0, title);

					/*Clicks on each recipes using index value*/
					Recipes1.get(i).click();
					Thread.sleep(6000);

					/*Getting Image link of the recipe*/
					try 
					{
						waithelper.WaitForElement(image, 15);
						imageLink=image.getAttribute("src");
						/*Writing the cell data to excel sheet using XLUtils class*/
						XLUtils.setCellData("Sheet1", RowNum, 5, imageLink);
					}
					catch(Exception e)
					{ 

						XLUtils.setCellData("Sheet1", RowNum, 5, imageLink);
					}

					/*Getting Recipe link of the recipe*/
					String recipeLink=ldriver.getCurrentUrl();

					/* Writing Recipe link to excel sheet*/
					XLUtils.setCellData("Sheet1", RowNum, 6, recipeLink);
					
					/*Getting category field for each recipe*/
					try
					{
						waithelper.WaitForElement(txtCate, 15);
						String category=txtCate.getText();

						//Category written to excel sheet
						XLUtils.setCellData("Sheet1", RowNum, 1, category);
					}
					catch(Exception e)
					{
						XLUtils.setCellData("Sheet1", RowNum, 1, "No Category Specified");
					}
					
					/*Getting Ingredients field for each recipe*/
					try
					{
						waithelper.WaitForElement(txtIngredients, 15);
						String ingredients=txtIngredients.getText();
						XLUtils.setCellData("Sheet1", RowNum, 2, ingredients);
					}
					catch(Exception e)
					{
						XLUtils.setCellData("Sheet1", RowNum, 2, "Null");
					}
					
					/*Getting Method/Steps for each recipe*/
					try
					{
						waithelper.WaitForElement(txtMethod, 30);
						String method =txtMethod.getText();
						//Method is written to to excel 
						XLUtils.setCellData("Sheet1", RowNum, 3, method);
					}
					catch(Exception e)
					{
						XLUtils.setCellData("Sheet1", RowNum, 3, "Null");
					}

	            	/*Getting nutritional values for ech recipe*/
					try
					{
						waithelper.WaitForElement(txtEnergy, 15);
						String energy=txtEnergy.getText();

						waithelper.WaitForElement(txtProtein, 15);
						String protein=txtProtein.getText();

						waithelper.WaitForElement(txtCarbo, 15);
						String carbo=txtCarbo.getText();

						waithelper.WaitForElement(txtFiber, 15);
						String fiber=txtFiber.getText();

						waithelper.WaitForElement(txtFat, 15);
						String fat=txtFat.getText();

						waithelper.WaitForElement(txtCholesterol, 15);
						String cholesterol=txtCholesterol.getText();

						waithelper.WaitForElement(txtSodium, 15);
						String sodium=txtSodium.getText();

                        /*Storing all  Nutritional value fields to a string*/
						String NutriValues="Energy/Total Calories:"+energy+"\n"+"Protein             :"+protein+"\n"+"Carbohydrates       :"+carbo+"\n"+"Fiber               :"+fiber+"\n"+"Fat                 :"+fat+"\n"+"Cholesterol         :"+cholesterol+"\n"+"Sodium              :"+sodium;

						//Nutritional values is written  to excel
						XLUtils.setCellData("Sheet1", RowNum, 4, NutriValues);
					}
					catch(Exception ex)
					{
						XLUtils.setCellData("Sheet1", RowNum, 4, "Nutritional values not present");
					}

					ldriver.navigate().back();
					Thread.sleep(10000);
				}
				b=b+count;
			}
		}
	}

    /*Method to click Recipe A to Z link*/
	public void clickRecipeAtoZ()
	{
		lnkRecipeAtoZ.click();
	}
	
    /*Method to set up Excel Header*/
	public void setupExcelHeader() throws IOException
	{
		String path="C:\\Users\\sethu\\eclipse-workspace\\RecipeScrapingHackathon\\src\\test\\java\\com\\RecipeScrapingHackathon\\testData\\Excel Recipe hackathon.xlsx";
		XLUtils xlutil=new XLUtils(path);

		/*Setting Excel Header*/
		XLUtils.setCellData("Sheet1", 0, 0, "Title");
		XLUtils.setCellData("Sheet1", 0, 1, "Category");
		XLUtils.setCellData("Sheet1", 0, 2, "Ingredients");
		XLUtils.setCellData("Sheet1", 0, 3, "Method/Recipe Steps");
		XLUtils.setCellData("Sheet1", 0, 4, "Nutrient Values With Total Calories");
		XLUtils.setCellData("Sheet1", 0, 5, "Recipe Image Link");
		XLUtils.setCellData("Sheet1", 0, 6, "Link to the Recipe");
	}
















}
