package ApachePOI;




	import java.io.IOException;
	import java.util.concurrent.TimeUnit;

	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.testng.Assert;
	import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


	public class DataProviderTestNG {

		WebDriver driver;
		
		@BeforeClass
		public void setup()
		{
			System.setProperty("webdriver.chrome.driver","C:\\Selenium\\chromedriver_win32\\chromedriver.exe");
			driver=new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
			driver.manage().window().maximize();
			
		}
		
		@Test(dataProvider="LoginData")
		public void loginTest(String user,String pwd,String exp) throws InterruptedException
		{
			driver.get("https://admin-demo.nopcommerce.com/login");
			
			
			WebElement txtEmail=driver.findElement(By.id("Email"));
			txtEmail.clear();
			txtEmail.sendKeys(user);
			
			
			WebElement txtPassword=driver.findElement(By.id("Password"));
			txtPassword.clear();
			txtPassword.sendKeys(pwd);
			
			driver.findElement(By.xpath("//input[@value='Log in']")).click(); //Login  button
			
			String exp_title="Dashboard / nopCommerce administration";
			String act_title=driver.getTitle();
			
			if(exp.equals("Valid"))
			{
				if(exp_title.equals(act_title))
				{
					driver.findElement(By.linkText("Logout")).click();
					Assert.assertTrue(true);
					System.out.println("Testcase passed");
				}
				else
				{
					Assert.assertTrue(false);
					System.out.println("Testcase failed");
				}
			}
			else if(exp.equals("Invalid"))
			{
				if(exp_title.equals(act_title))
				{
					driver.findElement(By.linkText("Logout")).click();
					Assert.assertTrue(false);
					System.out.println("Testcase failed");
				}
				else
				{
					Assert.assertTrue(true);
					System.out.println("Testcase passed");
				}
			}
			
		}
		
		@DataProvider(name="LoginData")
		
		public String [][] getData() throws IOException
		{
			
			String path="C:\\Users\\91986\\eclipse-workspace\\MySeleniumSession\\TestData\\Login.xlsx";
			ExcelUtility xlutil=new ExcelUtility(path);
			
			int totalrows=xlutil.getRowCount("Sheet1");
			int totalcols=xlutil.getCellCount("Sheet1",1);	
					
			String loginData[][]=new String[totalrows][totalcols];
				
			
			for(int i=1;i<=totalrows;i++) //1
			{
				for(int j=0;j<totalcols;j++) //0
				{
					loginData[i-1][j]=xlutil.getCellData("Sheet1", i, j);
					
				}
					
			}
			
			return loginData;
		}
		
		@AfterClass
		void tearDown()
		{
			driver.close();
		}
		
		
		
		
	}




