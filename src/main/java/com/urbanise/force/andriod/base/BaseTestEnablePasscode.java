//package com.urbanise.force.andriod.base;
//import com.urbanise.force.andriod.base.BaseTest;
//import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.MobileElement;
//
//import java.util.List;
//
//import org.springframework.util.Assert;
//import org.testng.annotations.Test;
//
//public class BaseTestEnablePasscode extends BaseTest
//{   
//	AppiumDriver<MobileElement> driver= null;
//	String siteUrl = "Master3.urbanisehq.com";
//
//    @Test (priority = 1)
//	 public void VerifyPasscode() throws Exception
//	  {	
//	   Boolean isHeadless= false;
//	   
//	   this.driver = initialise(isHeadless, BaseTest.setPackage);	
//		
//	   changeSite(driver, siteUrl );
//		
//	   logIn(driver, false, username, password);	
//	   enterPasscode();
//	   Thread.sleep(1500);
//	   reEnterPasscode();
//	   
//	   Thread.sleep(2000);   
//	   String tenantLogoText = GetTenantLogoText(driver);
//	   Assert.isTrue(tenantLogoText.contains("master"), "Error: Unable to Log in after entering Passcode"); //Verfiying if logging in to correct Tenant is succesful or not
//	   
//	   clickMenuHamburger();
//	   clickSignOut();
//	   
//	   enterPasscode();
//	   Thread.sleep(2000);
//	   Assert.isTrue(tenantLogoText.contains("master"), "Error: Unable to Log in after entering Passcode"); //Verfiying if logging in to correct Tenant is succesful or not
//	   
//	   clickMenuHamburger();
//	   clickSignOut();
//	   
//	   clickForgotPasscodeLink();
//	   String _message = getValidationText();
//	   Assert.isTrue(_message.equalsIgnoreCase("tap 'ok' to login with username and password. then you will be able to create a new secure code."), "Error: Expected to find validation message pop up but did not find");
//	   clickOk(); //To land the script on Log in Page for Next tests to start
//	  }	
//  
//  public void enterPasscode()
//  {
//	  selectDigit1();
//	  selectDigit2();
//	  selectDigit3();
//	  selectDigit4();
//  }
//  
//  public void reEnterPasscode()
//  {
//	  enterPasscode();
//  }  
//  
// public void selectDigit1() 
// {
//	 driver.findElementById("pin_code_button_1").click();
// }
// 
// public void selectDigit2()
// {
//	 driver.findElementById("pin_code_button_2").click();
// }
// 
// public void selectDigit3()
// {
//	 driver.findElementById("pin_code_button_3").click();
// }
// 
// public void selectDigit4()
// {
//	 driver.findElementById("pin_code_button_4").click();
// }
// 
//public void clickMenuHamburger()
//{
//	 driver.findElementByClassName("android.widget.ImageButton").click();	   
//}
//	
//public void clickSignOut() throws Exception
//{
//	driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.RelativeLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[8]").click();
//}
//
//public void clickForgotPasscodeLink()
//{
//	driver.findElementById("pin_code_forgot_textview").click();	
//}
//
//public String getValidationText()
//{
//	String _message = driver.findElementById("android:id/message").getText().toLowerCase();
//	return _message;
//}
//
//public void clickOk()
//{
//	driver.findElementById("android:id/button1").click();	
//}
//
//}
