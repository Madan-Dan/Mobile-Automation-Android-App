package com.urbanise.force.feature_testsuite;
import java.util.*;

import org.testng.annotations.Test;

import com.urbanise.force_RegressionTests.RegressionTests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class JobDetailPage {
	
	public static String checkInJob(AppiumDriver<MobileElement> driver)
	{
		List<MobileElement> elms = null;
		elms = driver.findElementsById("button_check_out");		
		
		if((elms.size())==0)
		{
			clickCheckIn(driver);		
		
		//
		
//		 if((elms.size()) == 1)
//		 {
//			 elms.get(0).click();
//			 if(addTimesheetPopUp)
//				 clickDoItLater(driver);
//			 
//			 clickCheckIn(driver);
//		 }
//		 else 
//			 clickCheckIn(driver);
		 
		 List<MobileElement> elms1 = driver.findElementsById("force.urbanise.com.forceTest:id/alertTitle");
		 
		 if(elms1.size() == 0)
			 return null;
		 else
			 return RegressionTests.getAlertMsg(driver);
	    }
		return null;
	}
	
	public static void clickContact(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.ImageView").click();
	}
	
	public static void clickDoItLater(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("android:id/button2").click();
	}
	
	public static void clickCheckIn(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("button_check_in").click();
	}

	public static void clickMoreTab(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("moreTabLayout").click();
	}
	
	public static void raiseNewJob(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.view.ViewGroup/android.widget.ListView/android.widget.RelativeLayout[3]/android.widget.TextView").click();
	}
	
	public static void clickAssignToOnRaiseNewWO(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/assignPersonLayout").click();
	}
	
	public static void clickBack(AppiumDriver<MobileElement> driver) throws InterruptedException
	{
		driver.findElementByClassName("android.widget.ImageButton").click();
		Thread.sleep(2000);
	}
	
	public static void ClickAddJobToWO(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.view.ViewGroup/android.widget.ListView/android.widget.RelativeLayout[2]/android.widget.TextView").click();
	}
	
	public static MobileElement isViewTimeSheetDisplayed(AppiumDriver<MobileElement> driver)
	{
		List<MobileElement> elms = driver.findElementsByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.view.ViewGroup/android.widget.ListView/android.widget.RelativeLayout[6]/android.widget.TextView");
	    if(elms.size() == 1)
	    	return elms.get(0);
	    else
	    	return null;	    	
	}
	
	public static boolean isLocateJobPinDisplayed(AppiumDriver<MobileElement> driver)
	{
		return driver.findElementById("force.urbanise.com.forceTest:id/locate_job").isDisplayed();
	}
	
	public static void clickNotes(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.view.ViewGroup/android.widget.ListView/android.widget.RelativeLayout[2]").click();
	}
	
	public static void enterJobNotes(AppiumDriver<MobileElement> driver, String text)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/note").sendKeys(text);
	}
	
	public static void clickAddNote(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/button").click();
	}
	
	public static String getConfirmationMsg(AppiumDriver<MobileElement> driver)
	{
	   return driver.findElementById("android:id/message").getText().toLowerCase();	
	}
	
	public static void clickAttachment(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/attachmentTabLayout").click();
	}
	
	public static boolean isImageSaved(AppiumDriver<MobileElement> driver)
	{
		return driver.findElementById("force.urbanise.com.forceTest:id/photo").isDisplayed();
	}
	
	public static void clickClose(AppiumDriver<MobileElement> driver) throws InterruptedException	
	{
		driver.findElementById("force.urbanise.com.forceTest:id/closeButton").click();
		Thread.sleep(2000);
	}
	
	public static void clickCloseOnJobDetail(AppiumDriver<MobileElement> driver) throws Exception
	{
		driver.findElementById("force.urbanise.com.forceTest:id/button_close").click();
		Thread.sleep(1000);
	}
	
	public static void skipSignature(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/buttonSkip").click();
	}
	public static void closeJob(AppiumDriver<MobileElement> driver) throws Exception
	{
		clickCloseOnJobDetail(driver);
		skipSignature(driver);	
		Thread.sleep(1000);
	}
	public static int getNoOfImages(AppiumDriver<MobileElement> driver)
	{
		List<MobileElement> elms = driver.findElementsById("force.urbanise.com.forceTest:id/photo");
		return elms.size();
	}
	
	public static void clickAttachmentLink(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/attachAction").click();
	}
	
	public static void clickMedia(AppiumDriver<MobileElement> driver) throws InterruptedException
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.appcompat.widget.LinearLayoutCompat/android.widget.FrameLayout/android.widget.ListView/android.widget.TextView[1]").click();
		Thread.sleep(3000);
	}
		
	public static void clickUploadFile(AppiumDriver<MobileElement> driver, Boolean isSurveyJob) throws InterruptedException
	{
		if(isSurveyJob)
		{
			driver.findElementById("force.urbanise.com.forceTest:id/photoCaptureImageView").click();
		    Thread.sleep(1500);
		}else{   
		driver.findElementById("force.urbanise.com.forceTest:id/attachment_select_image_view").click();
		Thread.sleep(1500);}
	}
	
	public static void clickSave(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/button_save").click();
	}
	
	public static int getNoOfImagesOnWIAttachment(AppiumDriver<MobileElement> driver)
	{
		List<MobileElement> elms = driver.findElementsById("force.urbanise.com.forceTest:id/item_attachment_selector_image");
		return elms.size();
	}
	
	public static void clickAttachAsset(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.view.ViewGroup/android.widget.ListView/android.widget.RelativeLayout[5]/android.widget.TextView").click();
	}
	
	public static void clickOnSurvey(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.RelativeLayout[4]/android.widget.TextView").click();
	}
	
	public static void clickOnSurveyQuestionSet(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.RelativeLayout/android.widget.TextView[1]").click();
	}
	
	public static void clickPlus(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.ImageView[2]").click();
	}
	
	public static void clickRaiseJob(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.Button[3]").click();
	}
	
	public static void fillJobFields(AppiumDriver<MobileElement> driver, boolean isSurveyJob, String jobtemplate) throws Exception
	{
        RaiseJobTests.selectJobTemplate(driver, jobtemplate);
		
		RegressionTests.assignJobToMe(driver, isSurveyJob);
		
		JobDetailPage.clickUploadFile(driver, isSurveyJob);
		
		if(isSurveyJob == false)
		JobDetailPage.clickMedia(driver);
		
		RegressionTests.captureImage(driver);
	}
	
	public static void clickAll(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.view.ViewGroup[2]/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[1]").click();
	}
	
	public static String getJobDescription(AppiumDriver<MobileElement> driver)
	{
		return driver.findElementById("force.urbanise.com.forceTest:id/item_details").getText().toLowerCase();
	}
 }

