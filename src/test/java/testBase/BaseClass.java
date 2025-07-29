package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
public class BaseClass {


	public static WebDriver driver;
	public Logger logger;
	public Properties p;
	
	@BeforeClass(groups = {"Regression","Master","Sanity"})
	@Parameters({"os","browser"})
	public void setup(String os,String br) throws IOException {
		
		
		FileReader file = new FileReader("./src/test/resources/config.properties");
		p = new Properties();
		p.load(file);
		
		
	   logger = LogManager.getLogger(this.getClass());   // log4j	
	   
	   
	   if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
	   {
	       DesiredCapabilities capabilities=new DesiredCapabilities();

	       //os
	       if(os.equalsIgnoreCase("windows"))
	       {
	           capabilities.setPlatform(Platform.WIN11);
	       }
	       else if (os.equalsIgnoreCase("linux"))
	       {
	           capabilities.setPlatform(Platform.LINUX);
	       }
	       else
	       {
	           System.out.println("No matching os");
	           return;
	       }

	       //browser
	       switch(br.toLowerCase())
	       {
	           case "chrome": capabilities.setBrowserName("chrome"); break;
	           case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
	           case "firefox": capabilities.setBrowserName("firefox"); break;
	           default: System.out.println("No matching browser"); return;
	       }
	       
	       
	       driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
	   }
	   
	   
	   if(p.getProperty("execution_env").equalsIgnoreCase("local")) {
		   
		   switch(br.toLowerCase()) {
		   
		   case "chrome" : driver = new ChromeDriver(); break;
		   case "edge" : driver = new EdgeDriver(); break;
		   case "firefox" : driver = new FirefoxDriver(); break;
		   default : System.out.println("Invalid browser ...."); return;
		   }
		   
	   }
	
	   
	   
		//	driver = new ChromeDriver();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			
			driver.get(p.getProperty("appURL"));  // reading url from properties file
			driver.manage().window().maximize();
	}
	
	@AfterClass(groups = {"Regression","Master","Sanity"})
	public void tearDown() {
		driver.quit();
	}
	
	

	public String randomNumber() {
	  return	RandomStringUtils.randomNumeric(10);
	}
	
	
	public String randomString() {
	  return	RandomStringUtils.randomAlphabetic(6);
	}
	
	public String randomAlphaNumeric() {
		  return	RandomStringUtils.randomAlphanumeric(10);
		}
	


	    public String captureScreen(String tname) throws IOException {
	        // 1. Create timestamp
	        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

	        // 2. Take screenshot
	        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
	        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

	        // 3. Define target path (fixed version)
	        String targetFilePath = System.getProperty("user.dir") + 
	                              "\\screenshots\\" + 
	                              tname + "_" + timeStamp + ".png";
	        File targetFile = new File(targetFilePath);

	        // 4. Create directory if missing
	        new File(System.getProperty("user.dir") + "\\screenshots\\").mkdirs();

	        // 5. Move file (original approach)
	        sourceFile.renameTo(targetFile);

	        return targetFilePath;
	    }
	
	
	
}
