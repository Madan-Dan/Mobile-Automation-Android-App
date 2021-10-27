package com.urbanise.force_RegressionTests;
import org.testng.annotations.Test;

import org.testng.AssertJUnit;

import com.urbanise.force.andriod.base.BaseTest;
import com.urbanise.force.feature_testsuite.JobDetailPage;
import com.urbanise.force.feature_testsuite.RaiseJobTests;
import com.urbanise.force.recordasset.RecordAssetpage;

import org.testng.annotations.Test;
import org.testng.Assert;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.touch.TouchActions;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import org.testng.annotations.BeforeTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

//---------------------------------------------------------------------------------------------------------------------------
//##Test Set up for running the tests:

//1. Ensure the following is entered in the BaseTest class:
// siteUrl = "Master1.urbanisehq.com";
// setPackage = "force.urbanise.com.forceTest"; // corresponds to the test app pushed from the app center

//2. Ensure that there is an active supplier agent and most of the skills are added to the supllier agent. 
// Please note if the supplier does not have relevant skill
// to run these tests, the tests will fail and inform which skill is missing in the failure logs. So, simply add that skill
// Ensure the following supplier agent details is entered correctly in the BaseTest class:
// username = "xxxxx";
// password = "xxxxx";

//3. Ensure there is atleast 1 active location and
// Location has atleast 1 Area and 2 accounts

//4. Make sure Insurance capability is turned OFF

//----------------------------------------------------------------------------------------------------------------------------

public class RegressionTests extends BaseTest
{
	AppiumDriver<MobileElement> driver= null;
	String setPackage = "force.urbanise.com.forceTest";
	Boolean isHeadless= true;
	public static Boolean addTimesheetPopUpOnCheckOut = true;
	public static String jobtemplate = "Auto_Construction_job_template";
	
@BeforeTest	
public AppiumDriver<MobileElement> OneTimeSetUp() throws Exception
	{	
	this.driver =  BaseTest.initialise(isHeadless, setPackage);
	BaseTest.changeSite(driver, siteUrl);
	BaseTest.logIn(driver,true, username, password);
	
	return driver;	
	}

@Test (priority = 1)
public void SetupTenantSite() throws Exception
	  {		
		System.out.println("Started SetupTenantSite Test");
	
		HomePage.goToChangeSite(driver);
		
		changeSite(driver, siteUrl );
		Thread.sleep(2000); // There is lag in showing the Log in screen so adding a wait here
														
		String tenantLogotxt= GetTenantLogoText(driver);
		Assert.assertTrue(tenantLogotxt.contains("trial"), "Error: Logged into Incorrect Tenant. the tenant. The following tenant is set : " + tenantLogotxt);
	    System.out.println("Test Completed");
	    
	    BaseTest.logIn(driver,true, username, password); //log back in for the next test script to run
	  }	
 
@Test (priority = 2)
public void LogInToTenantSite() throws Exception
	  {	
		String tenantLogoText = GetTenantLogoText(driver);
		Assert.assertTrue(tenantLogoText.contains("trial"), "Error: Logged into Incorrect Tenant:" + tenantLogoText);			
		System.out.println("Test Completed");
	  }	 

 @Test (priority = 3)
	public void VerifySyncStatusMessage() throws Exception 
	{ 	
	    clickSignOut();
	    BaseTest.logIn(driver,true, username, password);
		String _syncStatusInitialMessage= GetSyncStatusMessage().toLowerCase();		
		Assert.assertEquals(_syncStatusInitialMessage, "syncing data in the background for offline use"); 
	    
		Thread.sleep(2500);
		String _syncStatusFinalMessage= GetSyncStatusMessage().toLowerCase();		
		Assert.assertEquals(_syncStatusFinalMessage, "syncing offline data completed successfully"); 
	}
 
 @Test (priority= 4)
 public void VerifyHomePageElementsDiplayed()
 	{
	 Boolean _isAppointmentsDisplayed = IsAppointmentsDisplayed();
	 Assert.assertTrue(_isAppointmentsDisplayed, "Error: Could not find Appointments tab on Home Page");
	 
	 Boolean _isPlannedDisplayed = IsPlannedDiplayed();
	 Assert.assertTrue(_isPlannedDisplayed, "Error: Could not find Planned tab on Home Page");
	 
	 Boolean _isAttendedDisplayed = IsAttendedDisplayed();
	 Assert.assertTrue(_isAttendedDisplayed, "Error: Could not find Attended tab on Home Page");
	 
	 Boolean _isAssignedDisplayed = IsAssignedDisplayed();
	 Assert.assertTrue(_isAssignedDisplayed, "Error: Could not find Assigned tab on Home Page");
	 
	 Boolean _isJobDisplayed = IsJobDisplayed();
	 Assert.assertTrue(_isJobDisplayed, "Error: Could not find Job tab on Home page"); 	 
 	}
 
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
 public static String text = "Auto Job Description text"; 
 
 @Test(priority= 5)
 public void VerifyJobAddressNJobNumber() throws Exception
 {
	 String _jobAddrExpected = "auto_area 1 - 380 queens street ciel cafe melbourne melbourne ";
	 RaiseJobTests.clickOnRaiseJob(driver);
	 String _jobNumber = RegressionTests.raiseJob(driver, FillText, jobtemplate);
	 
	 RegressionTests.clickOnAssignedJob(driver);
	 RegressionTests.clickSortByFilter(driver);
	 
	 String _jobAddrFound = RaiseJobTests.getJobAddr(driver);
	 Assert.assertEquals(_jobAddrFound, _jobAddrExpected, "Error: Unable to find the Job Address"+_jobAddrFound);
	 
	 String jobnumberFound =  RegressionTests.assignedJobNumber(driver);
	 Assert.assertNotNull(jobnumberFound, "Error: Cannot find Job number on Job list page");	 
 }
 
 public static String raiseJob(AppiumDriver<MobileElement> driver, String text, String jobtemplate) throws Exception
 {	    
	  captureImage(driver);
	  RaiseJobTests.selectLocationByArea(driver); 
		  
	  RaiseJobTests.selectJobTemplate(driver, jobtemplate);        
	  Thread.sleep(2000);
	  
	  scrollDownPage(driver);
	  RaiseJobTests.enterJobDescription(driver, text);
	  Thread.sleep(1000);
	  
	  scrollDownPage(driver);
	  
      RaiseJobTests.clickAssignToFieldOnRaiseJob(driver);
	  RaiseJobTests.clickAssignToMeButton(driver);
	  Thread.sleep(2000);
					    
	  RaiseJobTests.clickRaiseJobButton(driver);
	  Thread.sleep(2000);
	  String jobnumber = RaiseJobTests.getJobNumber(driver);
	    
	  clickOk(driver);
	  return jobnumber;
 }
 
 public static void checkInToAssignedJob(AppiumDriver<MobileElement> driver, Boolean x) throws Exception
 {
	 clickOnAssignedJob(driver);
	 Thread.sleep(4000);
	 String _assignedjobCount = RaiseJobTests.getAssignedJobCount(driver);
	 
	 if(_assignedjobCount.contentEquals(RaiseJobTests.noassignedjobtext))
	 {	
		clickNavigateBack(driver);
	    RaiseJobTests.clickOnRaiseJob(driver);	 
	    
	    raiseJob(driver, text, null);	        
	    clickOnAssignedJob(driver);
	 }
	 
	 clickSortByFilter(driver);
	 clickFirstAssignedJob(driver);
	 
	 String _alertMsg = verifyAlertMessageExists(driver);
	 Assert.assertNull(_alertMsg, "Error with logged in Supplier agent: " + _alertMsg);
	 
	 String _checkInstructioExist = JobDetailPage.checkInJob(driver);
	 Assert.assertNull(_checkInstructioExist, "Error while check in to Job:"+_checkInstructioExist);
	 Thread.sleep(1000);
 }
 
 public static void checkInToFirstPlannedJob(AppiumDriver<MobileElement> driver) throws Exception
 {
	 goToPlannedJob(driver);
	 clickFirstAssignedJob(driver);	 
	 
	 String _alertMsg = verifyAlertMessageExists(driver);
	 Assert.assertNull(_alertMsg, "Error with logged in Supplier agent: " + _alertMsg);
	 
	 String _checkInstructioExist = JobDetailPage.checkInJob(driver);
	 Assert.assertNull(_checkInstructioExist, "Error while check in to Job:"+_checkInstructioExist);
	 Thread.sleep(1000);
 }
 
 public static void goToPlannedJob(AppiumDriver<MobileElement> driver) throws Exception
 {
	 clickOnAssignedJob(driver);
	 Thread.sleep(2500);
	 
	 selectPlannedFilter(driver);
	 Thread.sleep(1000);
	 
	 clickSortByFilter(driver);
 }
 
 public static void goToReactiveJobList(AppiumDriver<MobileElement> driver) throws Exception
 {
	 clickOnAssignedJob(driver);
	 Thread.sleep(2500);
	 
	 clickOnReactiveFilter(driver);
	 Thread.sleep(1000);
	 
	 clickSortByFilter(driver);
 }
 
 public static void assignJobToMe(AppiumDriver<MobileElement> driver, Boolean isPlannedJob) throws InterruptedException
  {
	 if(isPlannedJob)
		 RaiseJobTests.clickAssignUserField(driver);
	 else
         RaiseJobTests.clickAssignToField(driver);
	 
	 RaiseJobTests.clickAssignToMeButton(driver);
	 Thread.sleep(2000);
  }
 
 public static void captureImage(AppiumDriver<MobileElement> driver) throws InterruptedException
 {
	 try {
		clickCaptureButton(driver);
	} catch (InterruptedException e) {
			e.printStackTrace();
	}	    
     Thread.sleep(4000); // waiting for the App to save the captured image so adding a sleep
     clickSave(driver);
     Thread.sleep(2500);
 }
 
 public static String verifyAlertMessageExists(AppiumDriver<MobileElement> driver)
 {
	 List<MobileElement> elms = driver.findElementsById("force.urbanise.com.forceTest:id/alertTitle");

	 if((elms.size()) == 0)
		 return null;
		 else
			 return getAlertMsg(driver);
 }
 
 public static String getAlertMsg(AppiumDriver<MobileElement> driver)
 {
	 return driver.findElementById("android:id/message").getText();
 }

 public static LocalDateTime dateTime = LocalDateTime.now();  //Record Asset Script can be run many times as passing dynamic values each time
 
 static String Category= "Auto_Cat"+dateTime;
 static String Manufacturer = "Auto_Manufac"+dateTime;
 static String Model = "Auto_Model"+dateTime;
 public static String FillText = "Auto_"+dateTime.getSecond()+dateTime.getMinute();
 public static String AssetNumber = "AutoTest"+dateTime.getSecond()+dateTime.getDayOfYear();
 public static String Tag = "AUTO "; // Accepts only Capitals
 public static String Date = "01012022";
 String searchtext = "fon";
 
 public static void fillSystemAttributes(AppiumDriver<MobileElement> driver) throws Exception
 {
	 selectandAddNewCategory(driver, Category);
	 selectandAddNewManufacturer(driver, Manufacturer);
	 Thread.sleep(3500);
	 selectandAddNewModel(driver, Model);
 }
 
 public static void fillCustomAttributes(AppiumDriver<MobileElement> driver, String _updateValue)
 {
	 enterElectronicCode(FillText+_updateValue, driver);
	 enterInstallationDate(Date, driver);
	 enterSerialNumber(FillText+_updateValue, driver);
 }
 
 public static void downloadFirstLoc(AppiumDriver<MobileElement> driver) throws InterruptedException
 {
	 clickFind(driver);	 
	 selectFirstLocation(driver);
	 Thread.sleep(1500);
 }
  
 @Test //(priority = 5)
 public void RecordAssetTakingTenPhotos() throws Exception
 {
	 goToRecordAssetPage(driver);
	 
     String _message = getValidationMessageText(driver).toString().toLowerCase();
	 if(_message.contains("0"))
	 {
	 AssertJUnit.fail("Error:Downloaded 0 locations, please add Locations on Plaza and start the Test again");
	 }
	 else
	 {
	 clickOk(driver);
	 }
	 
	 Thread.sleep(1000);
	 fillSystemAttributes(driver);
	 
	 //Fill Asset Number 
	 enterAssetNumber(AssetNumber, driver);
	 
	 //Fill with First Location in the List
	 downloadFirstLoc(driver);
	 
	 scrollDownPageToNextPage(driver);
	 Thread.sleep(2000);
	
	 //Fill Custom Attributes
	 String _updateValue = "";
	 fillCustomAttributes(driver, _updateValue);
	 
	 Thread.sleep(800); // adding a wait for the app to finish uploading images 
	 scrollDownPageToNextPage(driver);
	 Thread.sleep(1000);
	 
	 selectCondition(driver, 1); //Enter the which condition to  select: 1, 2, 3, 4 
	 selectTrivial(driver);
	 Thread.sleep(500);	 
	 
	 Thread.sleep(800);
	 scrollDownPage(driver);
	 
	 //Takes 10 pictures and Saves
	 captureAndUploadMediaFile(driver);
	 
	 Thread.sleep(800);
	 scrollDownPageToNextPage(driver);
	 
	 addTag(Tag, driver);
	 addNotes(FillText, driver);
	 
	 clickAddRecordbutton(driver);
	 clickOk(driver);	
	 Thread.sleep(2500);
	 
	 String _lastMessage = getConfirmationMessage();
	 Assert.assertEquals(_lastMessage, "asset created successfully", "Error: Did not find Success validation message");
	 System.out.println("Assets are created for:"+ Category+Model+ Manufacturer+ AssetNumber);
	 System.out.println("Assets are created under following Tag:" + Tag);
	 
	 clickRecordSimilar(); //land the script on Home screen for other Tests to Run
	 	 
	 RecordAssetpage.searchAssetNo(driver, AssetNumber, true, null);
	 
	 String _assetNumberFound = RecordAssetpage.assetNumberFound(driver);
	 Assert.assertEquals(AssetNumber.toLowerCase(), _assetNumberFound, "Cannot find the recorded asset no");	
	 
	 clickNavigateBack(driver);
	 RegressionTests.clickOk(driver);	 
 } 
 
@Test //(priority = 6)
public void AssetHistoryShowRecordedAsset() throws Exception
{	 
     goToRecordAssetPage(driver);
     clickOk(driver);
     clickAssetHistoryIcon(driver);
     
     String warningmessage= getWarningText(driver).toLowerCase();
     Assert.assertEquals(warningmessage,"no assets recorded", "Expected No Assets recorded message but found:" + warningmessage);
     clickX();
     Thread.sleep(2000);
     
	 //Fill System Attributes
     fillSystemAttributes(driver);
	 
	 //Fill Asset Number 
	 enterAssetNumber(AssetNumber, driver);
	 
	 scrollDownPageToNextPage(driver);
	 Thread.sleep(1000);
	 
	 //Fill with First Location in the List
	 downloadFirstLoc(driver);
	 
	 scrollDownPageToNextPage(driver);
	 Thread.sleep(1000);
	 
	 captureAndUploadMediaFile(driver);
	 Thread.sleep(1000);
	 scrollDownPageToNextPage(driver);
	 Thread.sleep(1000);
	 clickAddRecordbutton(driver);
	 clickOk(driver);	
	 Thread.sleep(2000);
	 
//	 clickOk(driver);	
//	 Thread.sleep(4000);
	 
	 clickRecordSimilar();
	 
	 clickAssetHistoryIcon(driver);
	 
	 String assetname = getAssetName();
	 Assert.assertEquals(assetname, Category, "Incorrect Asset Name found on Asset History page");
	 
	 String assettype = getAssetType();
	 Assert.assertEquals(assettype, Category+" "+'|'+" "+Manufacturer+" "+'|'+" "+Model, "Incorrect Asset Type:"+ assettype);
	 
	 String assetnumber = getAssetNumber();
	 Assert.assertEquals(assetnumber, AssetNumber, "Incorrect Asset No found:" + AssetNumber);
}

@Test (priority = 7)
public void RecordAssetQuickLinks() throws Exception
{
     goToRecordAssetPage(driver);	 
     clickOk(driver);
	 
	 RecordAssetpage.clickRegionIcon(driver);
	 String _titleText = RecordAssetpage.getTitleText(driver);
	 Assert.assertEquals(_titleText, "choose regions", "Error: Choose regions page did not open");
	 
	 RecordAssetpage.clickBack(driver);
	 Thread.sleep(3000);
	 
	 scrollDownPageLittle(driver);	 
	 
	 RecordAssetpage.clickGpsPin(driver);
	 boolean gpsinfoexists = RecordAssetpage.doesGpsInfoExists(driver);
	 Assert.assertNotNull(gpsinfoexists, "Error: Gps location is not found when tapping on Gps pin");
	 
	 boolean _isExists = RecordAssetpage.isElectronicCodeReaderExists(driver);
	 Assert.assertTrue(_isExists, "Did not find Electronic code reader");
	 scrollDownPageLittle(driver);	 
	 
	 RecordAssetpage.isSerialNoBarCodeReaderExists(driver);
	 Assert.assertTrue(_isExists, "Did not find Serial code reader");
}

// This test case verifies Contact on Job detail page
@Test//(priority= 8)
public void VerifyContactDoesNotThrowException() throws Exception
{
	checkInToAssignedJob(driver, null);
	JobDetailPage.clickContact(driver);
	Thread.sleep(4000);
	
	boolean _locateJobPin = JobDetailPage.isLocateJobPinDisplayed(driver);
	
	Assert.assertTrue(_locateJobPin, "Error: cannot find locate Job pin . This may be due to COntact is taking to incorrect page");	
}

@Test // (priority = 9 )
public void AddJobNotes() throws Exception
{
	checkInToAssignedJob(driver, null);
	JobDetailPage.clickMoreTab(driver);
	
	JobDetailPage.clickNotes(driver);
    JobDetailPage.enterJobNotes(driver, text);
    
    JobDetailPage.clickAddNote(driver);
    
    String successMsg = JobDetailPage.getConfirmationMsg(driver);
    Assert.assertEquals(successMsg, "job note was successfully added");
    
    clickOk(driver);  	
}

@Test //(priority = 10)
public void VerifyTransferStock() throws Exception
{
	goToTransferStock();
	clickTransferSuppplyPoint();
	selectFirstSupplyPointInList();
	 
	enterAndSelectPartNo();
	clickTransferToSupplyPoint();
	
	selectSupplyPoint();
	
	clickTransferStockButton();		
}

@Test//(priority =11)
public void addTag() throws Exception
{
	goToRecordAssetPage(driver);
	clickOk(driver);
	Thread.sleep(2000);
	scrollDownPageToNextPage(driver);
	scrollDownPage(driver);
	scrollDownPageToNextPage(driver);
	Thread.sleep(2000);
	
	enterNewTag(driver, RegressionTests.FillText);
}
 
 @Test// (priority = 12)
 public void ForgotPassword() throws Exception
 {
	clickMenuHamburger(driver);	
    clickSignOut();
    
    BaseTest.clickForgotPasswordLink(driver);	

    MobileElement _usernamefield = BaseTest.usernameField(driver);
    _usernamefield.sendKeys("Test@test.com"); 
    
    BaseTest.clickResetButton(driver);
          
    String message = BaseTest.getMessage(driver); 
    AssertJUnit.assertEquals(message, "please check your email");      
 }	 

//Methods:
	public String GetSyncStatusMessage() 
	{
		String _syncstatusmessage = driver.findElement(By.id("snackbar_text")).getText().toLowerCase();		
		return _syncstatusmessage;
	}
	
	public boolean IsAppointmentsDisplayed()
	{
		return driver.findElementById("ButtonAppointment").isDisplayed();				
	}
	
	public boolean IsPlannedDiplayed()
	{
		return driver.findElementById("ButtonPlanned").isDisplayed();
	}
	
	public static void selectPlannedFilter(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.view.ViewGroup[2]/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.TextView").click();
	}
	
	public static void clickOnReactiveFilter(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.view.ViewGroup[2]/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[3]/android.widget.TextView").click();
	}
	
	public static void clickOnAssignedJob(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("ButtonUnscheduled").click();
	}
	
	public boolean IsAttendedDisplayed()
	{
		return driver.findElementById("ButtonAttended").isDisplayed();
	}
	
	public boolean IsAssignedDisplayed()
	{
		return driver.findElementById("ButtonUnscheduled").isDisplayed();
	}
	
	public boolean IsJobDisplayed()
	{
		return driver.findElementById("ButtonJob").isDisplayed();
	}
	
	public static void clickSortByFilter(AppiumDriver<MobileElement> driver) throws Exception
	{
		driver.findElementById("action_filter").click();
	    Thread.sleep(1600);
	}
	
	public static String elm_jobno = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.view.ViewGroup[2]/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[1]/android.widget.FrameLayout/android.view.ViewGroup/android.widget.LinearLayout/android.widget.TextView[4]";
	
	public static String assignedJobNumber(AppiumDriver<MobileElement> driver)
	{		
		return driver.findElementByXPath(elm_jobno).getText();
	}
	
	public static void clickFirstAssignedJobNo(AppiumDriver<MobileElement> driver) throws InterruptedException
	{
		driver.findElementByXPath(elm_jobno).click();
		Thread.sleep(1500);
	}
	
	public static String getCreatedJobNo(AppiumDriver<MobileElement> driver, Boolean isSurveyJob) throws Exception
	{
		if(isSurveyJob == false)
		{
		RegressionTests.clickOnAssignedJob(driver);
		RegressionTests.clickSortByFilter(driver);
		}		
		return driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.view.ViewGroup[2]/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[1]/android.widget.FrameLayout/android.view.ViewGroup/android.widget.LinearLayout/android.widget.TextView[5]").getText();
	}
	
	public static void clickMenuHamburger(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByClassName("android.widget.ImageButton").click();	   
	}
	
	public void clickSignOut() throws Exception
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.RelativeLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[8]").click();
	}
	
	public static void clickRecordAssetMenuItem(AppiumDriver<MobileElement> driver) throws InterruptedException
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.RelativeLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[3]/android.widget.TextView").click();	

//Need to work on this later, so commenting out for now --			
//		List<MobileElement> list = driver.findElementsByClassName("android.widget.LinearLayout");// force.urbanise.com.forceTest:id/recyclerView
//		Thread.sleep(1000);
//		for (int i=0; i < list.size();  i++)
//	    {
//						
//	    	if(list.get(i).findElementByClassName("android.widget.ImageView").getText().toLowerCase().contains("record"))
//	    	{
//	    		list.get(i).click();
//	    		break;
//	    	}
//	    }		
	}
		
	public static void selectFirstRegion(AppiumDriver<MobileElement> driver) throws Exception
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.RelativeLayout[1]").click();
	    Thread.sleep(2000);
	}
	
	public static void clickDownload(AppiumDriver<MobileElement> driver) throws Exception
	{
		driver.findElementById("download").click();
		Thread.sleep(3000);
	}
	
	public static String getValidationMessageText(AppiumDriver<MobileElement> driver)
	{
		String _message = driver.findElementById("android:id/message").getText();
		//String _message = driver.findElementById("force.urbanise.com.forceTest:id/message").getText();
		return _message;
	}
	
	public static void clickOk(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("android:id/button1").click();
	}
	
	public static void clickYes(AppiumDriver<MobileElement> driver) throws InterruptedException
	{
		driver.findElementById("android:id/button1").click();
		Thread.sleep(500); //Adding a wait for the 
	}
	
	public static void clickNo(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("android:id/button2").click();
	}
	
	public static void clickCategory(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[2]").click();
	}
	
	public static void enterValueInSearchField(AppiumDriver<MobileElement> driver, String _text)
	{
		driver.findElementById("search").sendKeys(_text);		
	}
	
	public static void clickCreateNewButton(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("buttonCreateCategory").click();
	}
	
	public static void clickCreateNewButton1(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("buttonCreateNew").click();
	}
	
	public static void selectandAddNewCategory(AppiumDriver<MobileElement> driver, String Category)
	{
		clickCategory(driver);
		enterValueInSearchField(driver, Category);
		clickCreateNewButton(driver);			
	}
	
	public static void clickManufacturer(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.TextView[2]").click();
	}
	
	public static void selectandAddNewManufacturer(AppiumDriver<MobileElement> driver, String _text)
	{
		clickManufacturer(driver);
		enterValueInSearchField(driver, _text);
		clickCreateNewButton1(driver);	
	}
	
	public static void clickModel(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout[3]/android.widget.TextView[2]").click();
	}	

	public static void selectandAddNewModel(AppiumDriver<MobileElement> driver, String _text) throws Exception
	{		
		clickModel(driver);
		Thread.sleep(4000);
		enterValueInSearchField(driver, _text);
		clickCreateNewButton1(driver);	
	}
	
	public static void enterAssetNumber(String AssetNumber, AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("number").sendKeys(AssetNumber);
	}
	
	public static void enterAssetNumberAfterUpdating(String _newAssetNumber, AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("number").sendKeys(_newAssetNumber);
	}
	
	public static void clickFind(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("by_name").click();
	}
	
	public static void selectFirstLocation(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[1]/android.widget.ImageView").click();
	}
	
	public void clickGpsLocationPin()
	{
		driver.findElementById("gpsLocationButton").click();
	}
	
	public static void enterElectronicCode(String Filltext, AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("barcode").sendKeys(Filltext);
	}
	
	public static void enterInstallationDate(String FillText, AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("date").sendKeys(FillText);
	}
	
	public static void enterSerialNumber(String FillText, AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("serial").sendKeys(FillText);
	}
	
	public static void clickCondition(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("selectCondition").click();
	}
	
	public static void selectRadioButton(AppiumDriver<MobileElement> driver, int _number)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout["+_number+"]/android.widget.ImageView").click();
	}
	
	public static void selectCondition(AppiumDriver<MobileElement> driver, int _number)
	{		
		clickCondition(driver);
		selectRadioButton(driver, _number);		
	}	
	
	public static void selectTrivial(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("trivial").click();	
	}
	
	public static void captureAndUploadMediaFile(AppiumDriver<MobileElement> driver) throws InterruptedException 
	{
		clickCameraIcon(driver);
		
		//Taking 10 photos
		for(int i=0; i < 10; i++)
		  {
		  try {
			clickCaptureButton(driver);
			Thread.sleep(2500);
		  } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }		    
		 }

		String msg = getValidationMessageText(driver);
		Assert.assertEquals(msg, "You can choose maximum 10 items", "Error: Missing validation message");
		
		clickOk(driver);
		Thread.sleep(1000); //Need to wait for the Save Button 
		clickSave(driver);
		
		Thread.sleep(3000); // Need to wait for the captured photos to save
	}
	
	public static void captureAndUpload1MediaFile(AppiumDriver<MobileElement> driver) throws InterruptedException 
	{
		clickCameraIcon(driver);
		  try {
			clickCaptureButton(driver);
			Thread.sleep(1500);
		  } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			  	    
		 }
		  
		  clickSave(driver);
	}
	
	public static void clickCameraIcon(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("photoCaptureImageView").click();
	}
	
	public static void clickCaptureButton(AppiumDriver<MobileElement> driver) throws InterruptedException
	{
		driver.findElementById("captureButton").click();
		Thread.sleep(1500);
	}
	
	public static void clickSave(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("saveButton").click();
	}
	
	public static void goToRecordAssetPage(AppiumDriver<MobileElement> driver) throws Exception
	{
		 clickMenuHamburger(driver);
		 clickRecordAssetMenuItem(driver);
		 Thread.sleep(2000);
		 
		 String _titleText = RecordAssetpage.getTitleText(driver);
		 
		 if (_titleText != "RECORD ASSET") 
		 {
			 downloadFirstLocation(driver);
			 Thread.sleep(3000);
		 }	 
	}
	
	public static void scrollDownPage(AppiumDriver<MobileElement> driver)
	{
		{
		    //The viewing size of the device
		    Dimension size = driver.manage().window().getSize();

		    //x position set to mid-screen horizontally
		    int width = size.width / 2;

		    //Starting y location set to 80% of the height (near bottom)
		    int startPoint = (int) (size.getHeight() * 0.30);

		    //Ending y location set to 20% of the height (near top)
		    int endPoint = (int) (size.getHeight() * 0.20);

		    new TouchAction(driver).press(PointOption.point(width, startPoint)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(width, endPoint)).release().perform();
		}	
	}
	
	public static void scrollDownPageToNextPage(AppiumDriver<MobileElement> driver)
	{
		{
		    //The viewing size of the device
		    Dimension size = driver.manage().window().getSize();

		    //x position set to mid-screen horizontally
		    int width = size.width / 2;

		    //Starting y location set to 80% of the height (near bottom)
		    int startPoint = (int) (size.getHeight() * 0.80);

		    //Ending y location set to 20% of the height (near top)
		    int endPoint = (int) (size.getHeight() * 0.20);

		    new TouchAction(driver).press(PointOption.point(width, startPoint)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(width, endPoint)).release().perform();
		}	
	}
	
	//need to scroll page up only little as 2 fields are hidden
	public static void scrollDownPageLittle(AppiumDriver<MobileElement> driver)
	{
		{
		    //The viewing size of the device
		    Dimension size = driver.manage().window().getSize();

		    //x position set to mid-screen horizontally
		    int width = size.width / 2;

		    //Starting y location set to 80% of the height (near bottom)
		    int startPoint = (int) (size.getHeight() * 0.50);

		    //Ending y location set to 20% of the height (near top)
		    int endPoint = (int) (size.getHeight() * 0.20);

		    new TouchAction(driver).press(PointOption.point(width, startPoint)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(width, endPoint)).release().perform();
		}	
	}
	
	 public static void enterMoreDetails(AppiumDriver<MobileElement> driver)
	 {
		 driver.findElementById("force.urbanise.com.forceTest:id/visionCompleteTextView").sendKeys(RegressionTests.text);
	 }
	 
	public static void addTag(String Tag, AppiumDriver<MobileElement> driver) throws InterruptedException
	{
		clickTag(driver);
		Thread.sleep(1500);
		enterNewTag(driver, Tag); //New Tags are added when u hit space
		Thread.sleep(3000); //wait for the new Tag to be created else it will not be creates
		clickDone(driver);
	}
	
	public static void clickTag(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout[5]/android.widget.RelativeLayout").click();
	}
	
	public static void enterNewTag(AppiumDriver<MobileElement> driver, String FillText) throws InterruptedException
	{	    

		MobileElement elm = driver.findElementById("force.urbanise.com.forceTest:id/searchTagsView");
		
		elm.click();
		elm.sendKeys("T ");


		int x = elm.getLocation().getX() + 440;
		int y = elm.getLocation().getY() + 70;


		System.out.println("X value: "+x+ "Y value: "+y);
		Thread.sleep(2000);

		//TouchAction action = new TouchAction(driver); action.press(PointOption.point(x,y)).release().perform(); //action.perform();

        
        new TouchAction(driver).press(PointOption.point(x, y)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).release().perform();

        elm.sendKeys("Auto ");

//		TouchAction action = new TouchAction(driver);
//		    int height = driver.manage().window().getSize().height;
//		    int width = driver.manage().window().getSize().width;
//		    action.press(PointOption.point(width / 2, height / 10))
//		            .moveTo(PointOption.point(width / 2, height * 3 / 4)).release().perform();	
		
		
		
		//MobileElement elm = driver.findElementById("searchTagsView");
//		TouchActions action = new TouchActions(driver);
//		int x = elm.getCenter().getX();
//		int y = elm.getCenter().getY() + 50;
//		
//		action.singleTap(elm); action.perform();
		
		//new TouchAction(driver).tap(1,x,y,300);
		
		//driver.findElementById("searchTagsView").sendKeys(FillText+" ");
		//driver.findElementById("searchTagsView").sendKeys(" 1");
	}
	
	public static void clickDone(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("done").click();
	}
	
	public static void clickAddRecordbutton(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("addRecordButton").click();
	}
	
	public static void addNotes(String FillText, AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("commentEditText").sendKeys(FillText);
	}
	
	public void clickRecordSimilar()
	{
		driver.findElementById("force.urbanise.com.forceTest:id/neutral").click();
	}		
	
	 public static void clickAssetHistoryIcon(AppiumDriver<MobileElement> driver)
	 {
		 driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.view.ViewGroup/androidx.appcompat.widget.LinearLayoutCompat/android.widget.FrameLayout/android.widget.ImageView").click();		 
	 }
	 
	 public static String getWarningText(AppiumDriver<MobileElement> driver)
	 {
		String warningtext = driver.findElementById("force.urbanise.com.forceTest:id/emptyWarningText").getText().toLowerCase();
		return warningtext;
	 }
	 
	 public String getAssetName()
	 {
		 String assetname = driver.findElementById("force.urbanise.com.forceTest:id/name").getText(); 
		 return assetname;
	 }
	 
	 public String getAssetType()
	 {
		 String assettype = driver.findElementById("force.urbanise.com.forceTest:id/type").getText();
		 return assettype;
	 }
	 
	 public String getAssetNumber()
	 {
		 String assettype = driver.findElementById("force.urbanise.com.forceTest:id/asset_number").getText();
		 return assettype;
	 }
	 
	 public void clickX()
	 {
		 driver.findElementByClassName("android.widget.ImageButton").click();
	 }
	 
	 public static void downloadFirstLocation(AppiumDriver<MobileElement> driver) throws Exception
	 {
		 selectFirstRegion(driver);
		 Thread.sleep(4000); //waiting for the locations to load
		 clickDownload(driver);
	 }
	 
	 public void clickClose()
	 {
		 driver.findElementById("force.urbanise.com.forceTest:id/closeButton").click();
		 
	 }
	 
	 public static void clickNavigateBack(AppiumDriver<MobileElement> driver)
	 {
		 driver.findElementByClassName("android.widget.ImageButton") .click();
	 }
	 
	 public static void clickFirstAssignedJob(AppiumDriver<MobileElement> driver) throws InterruptedException
	 {
		 driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.view.ViewGroup[2]/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[1]/android.widget.FrameLayout/android.view.ViewGroup/android.widget.LinearLayout").click();
		 Thread.sleep(1500);
	 }
	 
	 public void goToTransferStock() throws Exception
	 {
		 clickMenuHamburger(driver);
		 clickTransferStock();
		 Thread.sleep(1000);	 
	 }
	 
	 public void clickTransferStock()
	 {
		 driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.RelativeLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[4]/android.widget.TextView").click();
	 }
	 
	 public void clickTransferSuppplyPoint()
	 {
		 driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.Spinner/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView").click();
	 }
	 
	 public void selectFirstSupplyPointInList()
	 {
		 driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.TextView").click();
	 }
	 
	 public void enterAndSelectPartNo()
	 {
		 driver.findElementById("force.urbanise.com.forceTest:id/selectPartNumber").click();
		 driver.findElementById("force.urbanise.com.forceTest:id/search").sendKeys("PART01");
		 
		 //driver.pressKeyCode(AndroidKeyCode.ENTER, AndroidKeyMetastate.META_SHIFT_ON);
		 
		 driver.findElementById("force.urbanise.com.forceTest:id/subtitle").click();
	}
	 
	 public void clickTransferToSupplyPoint()
	 {
		 driver.findElementById("force.urbanise.com.forceTest:id/selectToSupplyPoint").click();

     }
	 
	 public void selectSupplyPoint()
	 {
		 driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView").click();
	 }
	 
	 public void clickTransferStockButton()
	 {
	    driver.findElementById("force.urbanise.com.forceTest:id/transferStockButton").click(); 
	 }
	 
	 public static void clickTimesheet(AppiumDriver<MobileElement> driver) throws InterruptedException
	 {
		 driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.RelativeLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[2]").click();
		 Thread.sleep(1000);
	 }
	 
	 public String getConfirmationMessage()
	 {
		 return driver.findElementById("force.urbanise.com.forceTest:id/message").getText().toLowerCase();
	 }
//	 @AfterTest
//	 public void End()
//	 {
//	  driver.quit();
//	 }
}


