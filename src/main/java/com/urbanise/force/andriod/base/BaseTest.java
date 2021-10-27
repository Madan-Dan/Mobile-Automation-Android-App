package com.urbanise.force.andriod.base;
import org.testng.annotations.Test;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.Assert;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

//-----------------------------------------------------------------------------------
//This is a Test Base Class which initialises the driver object and performs:
//1. Set up the capabilities that are required for setting a specific Android device 
//2. Launches the Force App on Android device

//-----------------------------------------------------------------------------------

public class BaseTest
{	    
	 public static Boolean IsHeadless = false;
	 AppiumDriver<MobileElement> driver = null;
	 public String siteUrl = "alpha3.urbanisehq.com";
	 public static String setPackage = "force.urbanise.com.forceTest";
	 
	 public static String username = "way@example.com";
	 public static String password = "admin123";
	 
	 public static Boolean isHeadless= true;
	
	//Methods:
    public static AppiumDriver<MobileElement> initialise(Boolean isHeadless, String setPackage) throws Exception
	{
      //Set up an Android device thru capabilities
		DesiredCapabilities cap = setupCapabilities(isHeadless, setPackage);		     	

	  //Launches the Force app on a Android device		    
		AppiumDriver<MobileElement> driver = setupMobileDriver(cap);
		//System.out.println("Launched Force App succesfully..");	
		
		return driver; 		
	}	
	
	public static DesiredCapabilities setupCapabilities(Boolean isHeadless, String setPackage) throws Exception
		{
	    //Below are the capabilities required for setting up a Android device
		    DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability("deviceName", "Android Emulator");
			cap.setCapability("udid", "emulator-5554");
			cap.setCapability("platformName", "Android");
			cap.setCapability("platformVersion", "9");
			cap.setCapability("appPackage", setPackage);
			
			cap.setCapability("appActivity", "force.urbanise.com.force.ActivitySplashScreen");
			cap.setCapability("appActivity", "force.urbanise.com.force.ActivityMain");
			cap.setCapability("capabilityName", "force.urbanise.com.force.ActivityTenant");
			cap.setCapability("capabilityName", "force.urbanise.com.force.ActivityLogin");
			cap.setCapability("capabilityName", "force.urbanise.com.force.ActivityMain");
			cap.setCapability("capabilityName", "force.urbanise.com.force.ActivityTasks");
			cap.setCapability("capabilityName", "force.urbanise.com.force.ActivityBase");
			cap.setCapability("capabilityName", "force.urbanise.com.force.ActivityViewer");
			cap.setCapability("autoGrantPermissions", true);
			cap.setCapability("unicodeKeyboard", "true");                                     
			cap.setCapability("resetKeyboard", "true");
			
			if(isHeadless)
			cap.setCapability("isHeadless", true);
			else
				cap.setCapability("isHeadless", false);	
			
			return cap;
		}
	
	public static AppiumDriver<MobileElement> setupMobileDriver(DesiredCapabilities cap) throws Exception
	{
		URL port = new URL("http://127.0.0.1:4723/wd/hub");
		AppiumDriver<MobileElement> driver = new AppiumDriver<MobileElement>(port, cap);							
		return driver;
	}
	
	public static void changeSite(AppiumDriver<MobileElement> driver ,String siteUrl) throws InterruptedException
	      {
	    	WebDriverWait wait = new WebDriverWait(driver,10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("site")));
			driver.findElement(By.id("site")).sendKeys(siteUrl);
			
			driver.findElement(By.id("setup")).click();	
			Thread.sleep(1500);
		  }	
	  
	  public static void logIn(AppiumDriver<MobileElement> driver, boolean skipPasscode, String username, String password) throws InterruptedException
	      {     
			WebDriverWait wait = new WebDriverWait(driver,10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
			driver.findElement(By.id("username")).sendKeys(username);
			driver.findElement(By.id("password")).sendKeys(password);
			driver.findElement(By.id("login")).click();	
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			
			if (skipPasscode)			
			driver.findElement(By.id("cancelButton")).click();	
			
			else
			{				
				driver.findElement(By.id("passcodeItem")).click();
			}
			
			Thread.sleep(2000);
	      }
	  
	  public static void clickForgotPasswordLink(AppiumDriver<MobileElement> driver)
	  {
		  driver.findElementById("resetPassword").click(); 
	  }

	  public static void clickResetButton(AppiumDriver<MobileElement> driver)
	  {
		 driver.findElementById("reset").click();
	  }
	  
	  public static MobileElement usernameField(AppiumDriver<MobileElement> driver)
	  {
		WebDriverWait wait = new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		MobileElement _username = driver.findElementById("username");
		return _username;
	  }
	  
	  public static String getMessage(AppiumDriver<MobileElement> driver)
	  {
		 WebDriverWait wait = new WebDriverWait(driver,2);
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("scrollView")));
		 String message = driver.findElementByClassName("android.widget.TextView").getText().toLowerCase(); //  ("message").getText().toLowerCase();
		 return message;		  
	  }	  
	  
	  public WebDriverWait wait;
	  
	  //Element Locators:	 	  
	  public String GetTenantLogoText(AppiumDriver<MobileElement> driver)
	  {
		String _tenantLogoText = driver.findElement(By.id("title")).getText().toLowerCase();
		return _tenantLogoText;
	  } 
	  
  } 

