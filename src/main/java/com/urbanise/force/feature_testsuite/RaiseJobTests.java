package com.urbanise.force.feature_testsuite;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.urbanise.force.andriod.base.BaseTest;
import com.urbanise.force.recordasset.RecordAssetpage;
import com.urbanise.force_RegressionTests.RegressionTests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class RaiseJobTests extends BaseTest
{		
	AppiumDriver<MobileElement> driver = null;
	public static String noassignedjobtext = "ASSIGNED 0";	
	static Boolean addTimesheetPopUpOnCheckOut = true;
	
	@BeforeTest	
	public AppiumDriver<MobileElement> OneTimeSetUp() throws Exception
		{	
		this.driver =  BaseTest.initialise(isHeadless, setPackage);
		BaseTest.changeSite(driver, siteUrl);
		BaseTest.logIn(driver,true, username, password);
		
		return driver;	
		}
	
	@Test (priority = 1)
	 public void RasieJobFromHomeScreen() throws Exception
	 {
	    RaiseJobTests.clickOnRaiseJob(driver); 
	  
	    String jobnumber = RegressionTests.raiseJob(driver, RegressionTests.text, null);
	    
	    String jobnumberFound=  getRecentAssignedJobNO(driver);   
	    Assert.assertTrue(jobnumber.toLowerCase().contains(jobnumberFound.toLowerCase()), "Error: Created Job Number" + jobnumber + " found on Assigned Job list:"+jobnumberFound); 
	    
	    verifyImageShowsOnJobAttachments(driver);
	 }
	
	 @Test (priority = 2)
	 public void RaiseAdhocJobFromMore() throws Exception
	 {   
		 RegressionTests.checkInToAssignedJob(driver, addTimesheetPopUpOnCheckOut);
		 
		 JobDetailPage.clickMoreTab(driver);	 
		 JobDetailPage.raiseNewJob(driver);	
	
		 RegressionTests.captureImage(driver);
		 Thread.sleep(1500);
		 RaiseJobTests.selectJobOnRaiseAdhocJobPage(driver);
		 
		 RaiseJobTests.enterJobDescription(driver, RegressionTests.text);
		 RegressionTests.assignJobToMe(driver, false);
	
		 RaiseJobTests.clickRaiseJobButton(driver);
		 
		 Thread.sleep(2000);
		 String jobnumber = RaiseJobTests.getJobNumber(driver);
		    
		 RegressionTests.clickOk(driver);	 
		 
		 JobDetailPage.clickBack(driver);
		 RegressionTests.clickNo(driver);
		 JobDetailPage.clickBack(driver);
		 
		 String jobnumberFound =  getRecentAssignedJobNO(driver);
		 	 
		 Assert.assertTrue(jobnumber.toLowerCase().contains(jobnumberFound.toLowerCase()), "Error: Created Job Number" + jobnumber + " found on Assigned Job list:"+jobnumberFound); 
	     
		 verifyImageShowsOnJobAttachments(driver);		
	 }
	 
	 @Test (priority = 3)
	 public void AddJobToExisitngWO() throws Exception
	 {
		 RegressionTests.checkInToAssignedJob(driver, addTimesheetPopUpOnCheckOut);
		 JobDetailPage.clickMoreTab(driver);
		 
		 JobDetailPage.ClickAddJobToWO(driver);
		 RegressionTests.captureImage(driver);
		 Thread.sleep(1500);
		 
		 RaiseJobTests.selectJobOnRaiseAdhocJobPage(driver);
		 
		 RegressionTests.assignJobToMe(driver, false);
		 RegressionTests.enterMoreDetails(driver);
		 
	     RaiseJobTests.clickRaiseJobButton(driver);	 
		 Thread.sleep(2000);
		 String jobnumber = RaiseJobTests.getJobNumber(driver);
		    
		 RegressionTests.clickOk(driver);	 
		
		 JobDetailPage.clickBack(driver);
		 RegressionTests.clickNo(driver);
		 JobDetailPage.clickBack(driver);
		 
		 String jobnumberFound =  RegressionTests.getCreatedJobNo(driver, false); 
		 	 
		 Assert.assertTrue(jobnumber.toLowerCase().contains(jobnumberFound.toLowerCase()), "Error: Created Job Number" + jobnumber + " found on Assigned Job list:"+jobnumberFound); 
		 verifyImageShowsOnJobAttachments(driver);
	 }	
	 
	 public static void verifyImageShowsOnJobAttachments(AppiumDriver<MobileElement> driver) throws InterruptedException
	 {
		 RegressionTests.clickFirstAssignedJobNo(driver);
		    JobDetailPage.clickAttachment(driver);
		    
		    int _imagesfound = JobDetailPage.getNoOfImages(driver);
		    Assert.assertEquals(_imagesfound, 1,"Expected to find 1 image on Job attachment but found:"+_imagesfound);
		    
		    JobDetailPage.clickBack(driver);
		    		    
		    JobDetailPage.clickBack(driver);
	 }
	 	
	 public static String getRecentAssignedJobNO(AppiumDriver<MobileElement> driver) throws Exception
	  {
		 RegressionTests.clickOnAssignedJob(driver);
		 RegressionTests.clickSortByFilter(driver);
		    
		 String jobnumberFound =  RegressionTests.assignedJobNumber(driver);
		 return jobnumberFound;
	  }
	
	public static void clickOnRaiseJob(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("ButtonJob").click();
	}
	
	@SuppressWarnings("deprecation")
	public static void selectLocationByArea(AppiumDriver<MobileElement> driver) throws InterruptedException
	{
		try {
			clickAreaButton(driver);
		}     catch (Exception e) {
			    e.printStackTrace();
		}
		
		clickfirstOneInList(driver);
		Thread.sleep(2000);
		
		selectFirstAccountInList(driver);		
	}
	
	public static void selectFirstAccountInList(AppiumDriver<MobileElement> driver) throws InterruptedException
	{
		clickDropDown(driver);
		Thread.sleep(1500);
		selectFirstOne(driver);	  
		Thread.sleep(1000);
	}
	
	public static void selectFirstOne(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/icon").click();
	}
	
	public static void clickDropDown(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Spinner/android.widget.RelativeLayout/android.widget.TextView").click();
	}
	
	public static void clickfirstOneInList(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/icon").click();
	}
		
	public static void enterLocation(AppiumDriver<MobileElement> driver, String locationText)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/search").sendKeys(locationText);
	}
	
	public static void clickAreaButton(AppiumDriver<MobileElement> driver) throws Exception
	{
		driver.findElementById("force.urbanise.com.forceTest:id/by_area").click();
        Thread.sleep(1000);	//waiting for the page to load
	}
	
	public static void locationDetails(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/locationResult");
	}

	public static void selectAndReturnAccountDetails(AppiumDriver<MobileElement> driver, String searchtext) throws Exception
	{
		clickAccountField(driver);
		Thread.sleep(1000);
		
		enterAccountAndSelectTheFirstOne(driver, searchtext);
		//String accountText = 
		GetAccountText(driver);
		//return accountText;
	}
	
	public static void enterAccountAndSelectTheFirstOne(AppiumDriver<MobileElement> driver, String searchtext) throws Exception
	{
		driver.findElementById("force.urbanise.com.forceTest:id/search").sendKeys(searchtext);	    
		//driver.findElementById("force.urbanise.com.forceTest:id/search").click();
		Thread.sleep(3000);
		selectFirstLocation(driver);
	 }
	
	public static void selectFirstLocation(AppiumDriver<MobileElement> driver)	
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.LinearLayout[1]/android.widget.ImageView").click();
	}	
	
	public static String GetAccountText(AppiumDriver<MobileElement> driver) 
	{
		String accounttext = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.TextView[2]").getText().toLowerCase();
        return accounttext;
	}

	public static void clickAccountField(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/selectAccountLayout").click();
	}
	
	public static void selectJobTemplate(AppiumDriver<MobileElement> driver, String jobtemplate) throws Exception
	{
		clickJobField(driver);
		Thread.sleep(2000);
		
		if(jobtemplate == null)
		{  selectFirstJobTemplate(driver);
		  Thread.sleep(2000);
	    }
		 else{
			 enterJobTemplate(driver, jobtemplate);	  
			 selectFirstJobTemplate(driver);
		     Thread.sleep(2000);   }	
	}
	
	public static void enterJobTemplate(AppiumDriver<MobileElement> driver, String jobtemplate)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/search").sendKeys(jobtemplate);
	}
	public static void selectJobOnRaiseAdhocJobPage(AppiumDriver<MobileElement> driver) throws InterruptedException
	{
		clickSelectJob(driver);
		Thread.sleep(1200);
		
		selectFirstJobTemplate(driver);
		Thread.sleep(2000);
	}
	
	public static void clickSelectJob(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/selectJobLayout").click();
	}
	
	public static void clickJobField(AppiumDriver<MobileElement> driver)
	{
		//driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout[3]/android.widget.TextView[2]").click();
	    driver.findElementById("selectJobLayout").click();
	}
	
	public static void selectFirstJobTemplate(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.LinearLayout[1]/android.widget.RelativeLayout/android.widget.TextView").click();
	}
	
	public static String jObSelected(AppiumDriver<MobileElement> driver)
	{
		return driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout[3]/android.widget.TextView[2]").getText();
	}
	
	public static void clickAssignToField(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("assignPersonLayout").click();
		//driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout[4]/android.widget.TextView[2]").click();;
	}
	
	public static void clickAssignUserField(AppiumDriver<MobileElement>driver)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/assignUserLayout").click();
	}
	
	public static void clickAssignToFieldOnRaiseJob(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("assignUserLayout").click();
	}
	
	public static void clickAssignToMeButton(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/assignMe").click();
	}
	
	public static void clickRaiseJobButton(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/raiseWork").click();
	}
	
	public static void enterJobDescription(AppiumDriver<MobileElement> driver, String text)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/inputEditText").sendKeys(text);
	}
	
	public static String getJobNumber(AppiumDriver<MobileElement> driver)
	{
		return driver.findElementById("android:id/message").getText();
	}
	
	public static void clickLocation(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("").click();
	}
	
	public static String getAssignedJobCount(AppiumDriver<MobileElement> driver)
	{
		return driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.view.ViewGroup[1]/android.widget.TextView").getText();
	}	
	
	public static String getJobAddr(AppiumDriver<MobileElement> driver)
	{
		return driver.findElementById("force.urbanise.com.forceTest:id/text_address").getText().toLowerCase();
	}
	
	public static void clickOnSearchIcon(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/action_search").click();
	}
	
	public static void searchJobNo(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/search_src_text").sendKeys("JOB-00236254");
		driver.getKeyboard().sendKeys(Keys.ENTER);
		//driver.longPressKeyCode(66);
	}
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
	
	// ***Test Data Set up for running below 2 tests***
	// 1. Ensure that tenant has the following Job template :Auto_Construction_job_template 
	// 2. Ensure that this job template has pre check in work instructions: Auto_Work Instruction set 1
	// 3. Ensure that this work instruction has 5 checklist items 
    //	  (Pass / Fail, Yes/No, Number score, list(0,1), Text)
	// 4. Ensure that there is the Job template set up : 
	
	@Test (priority = 4)
	public void RaiseJobFromWorkInstructions() throws Exception
	{
		MediaUploaderTests.raiseJob_WithWorkInstrAnd_CheckIn(driver);
		
		WorkInstructionsPage.clickWI(driver); //CLicks on Work instruction on Job detail page
		WorkInstructionsPage.clickOnCheklistItem_MoreLink(driver);
		
		enterIssue(driver, WorkInstructionsPage._issueText);
		clickYes_NeedJob(driver);
		
		JobDetailPage.fillJobFields(driver, false, RegressionTests.jobtemplate);
		
		clickSaveOnWIJob(driver);	
		Thread.sleep(1500);
		
		String _ConfirmationMsg = RegressionTests.getValidationMessageText(driver);
		Assert.assertEquals(_ConfirmationMsg, "Job created successfully", "Expected comfrmation message that Job is added but did not find it");
		
        RegressionTests.clickOk(driver);
        
        for (int i=0; i < 3; i++)
        {
          if(i == 2)
        	  RegressionTests.clickNo(driver);
           RegressionTests.clickNavigateBack(driver);
           Thread.sleep(2000);
        }
 
        String _jobnumbercreated =  RegressionTests.getCreatedJobNo(driver, false); 
        System.out.print(_jobnumbercreated); 
        
        RegressionTests.clickFirstAssignedJob(driver);
        String _jobTextFound = JobDetailPage.getJobDescription(driver);
        Assert.assertEquals(_jobTextFound, "checklist item 1 - task 1. "+WorkInstructionsPage._issueText.toLowerCase());
        
        JobDetailPage.clickCheckIn(driver);
        JobDetailPage.closeJob(driver);
	}
	
	//public static String _assetno = RegressionTest.AssetNumber;
	
	@Test (priority = 5)
	public void VerifyAttachJobAsset_RecordNewAssetAndAttach() throws Exception
	{		
		RegressionTests.checkInToAssignedJob(driver, RegressionTests.addTimesheetPopUpOnCheckOut);
		JobDetailPage.clickMoreTab(driver);
		
		JobDetailPage.clickAttachAsset(driver);	
		
		RecordAssetpage.searchAssetNo(driver, RegressionTests.AssetNumber, false, null);
		RecordAssetpage.clickRecordNewAsset(driver);
		
		 //Fill System Attributes
	     RegressionTests.fillSystemAttributes(driver);
		 
		 //Fill Asset Number 
	     RegressionTests.enterAssetNumber(RegressionTests.AssetNumber, driver);
		 
	     RegressionTests.scrollDownPageToNextPage(driver);
		 Thread.sleep(1000);
		 
		RegressionTests.clickAddRecordbutton(driver);
		RegressionTests.clickOk(driver);	
		Thread.sleep(2000);
		
		String _successMsg = RegressionTests.getValidationMessageText(driver);
		Assert.assertEquals(_successMsg, "Asset successfully attached to the job", "Error: Asset is not attached to job pls check");
	}
	
	public static void clickSaveOnWIJob(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/button_save").click();
	}
	
	public static void enterIssue(AppiumDriver<MobileElement> driver, String _issueText)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/description").sendKeys(_issueText);
	}
	
	public void clickYes_NeedJob(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/button_yes").click();
	}	
	
	@AfterTest
	public void driverQuit()
	{
		driver.quit();		
	}

}