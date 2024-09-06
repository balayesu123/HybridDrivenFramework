package testBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager; // Log4j
import org.apache.logging.log4j.Logger;  // Log4j
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseClass {
	
	public static WebDriver driver;
	public Logger logger;  // Log4j
	public Properties p;
	
	@BeforeMethod(groups= {"Sanity", "Regression", "Master"})
	@Parameters({"os","browser"})
	public void initializeBrowser(String os, String browser) throws Exception
	{
		
		// loading config.properties file
		
		FileReader file = new FileReader("./src/test/resources/config.properties");
		p=new Properties();
		p.load(file);
		
		// creates a logger instance for representing the runtime class of the current object.
		
		logger = LogManager.getLogger(this.getClass());
		
		// launch required browser at run time
		if(browser.toLowerCase().equals("chrome"))
		{
			driver = new ChromeDriver();
		}
		else if(browser.toLowerCase().equals("edge"))
		{
			driver = new EdgeDriver();
		}
		else if(browser.toLowerCase().equals("firefox"))
		{
			driver = new FirefoxDriver();
		}
		else
		{
			System.out.println("Invali browser name..");
		}
				
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(p.getProperty("appURL"));
		driver.manage().window().maximize();
	}
	
	@AfterMethod(groups= {"Sanity", "Regression", "Master"})
	public void closeBrowser() {
		driver.quit();
	}
	
	public String randomString()
	{
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}
	
	public String randomNumber()
	{
		String generatedNumber = RandomStringUtils.randomNumeric(10);
		return generatedNumber;
	}
	
	public String randomAlphaNumaric()
	{
		String generatedString = RandomStringUtils.randomAlphabetic(3);
		String generatedNumber = RandomStringUtils.randomNumeric(3);
		return (generatedString+"@"+generatedNumber);
	}
	
	public String captureScreen(String tname)
	{
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		TakesScreenshot takesScreenshot = (TakesScreenshot)driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		// This statement constructs a file path for saving a PNG image in a screenshots directory located within the current working directory.
		// The file name includes a dynamic part based on tname and timeStamp, allowing for unique file names each time the path is generated.		
		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname +"_"+timeStamp + ".png";
		File targetFile = new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		
		return targetFilePath;
	}
	

}
