package com.urbanise.forceRegression_devicefarm;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.urbanise.force.andriod.base.BaseTest;
import com.urbanise.force.feature_testsuite.JobDetailPage;
import com.urbanise.force_RegressionTests.RegressionTests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

//--------------------------------------------------------------------------------------------------------------------------
//##Test Set up for running the tests:

//1. Ensure the following is entered in the BaseTest class:
//siteUrl = "Master1.urbanisehq.com";
//setPackage = "force.urbanise.com.forceTest"; // corresponds to the test app pushed from the app center

//2. Ensure that there is an active supplier agent and most of the skills are added to the supllier agent. 
//Please note if the supplier does not have relevant skill
//to run these tests, the tests will fail and inform which skill is missing in the failure logs. So, simply add that skill
//Ensure the following supplier agent details is entered in the BaseTest class:
//username = "hri@example.com";
//password = "tester123";

//3. Ensure there is atleast 1 additional user agent

//--------------------------------------------------------------------------------------------------------------------------

public class Timesheets extends BaseTest
{
	AppiumDriver<MobileElement> driver = null;
	//String setPackage = "force.urbanise.com.forcePOC";
	String setPackage ="force.urbanise.com.forceTest";
	Boolean isHeadless = true;
	Boolean _addTimeSheetPopOnCheckOut = true;
	
	@BeforeTest	
	public AppiumDriver<MobileElement> OneTimeSetUp() throws Exception
		{	
		this.driver =  BaseTest.initialise(isHeadless, setPackage);
		BaseTest.changeSite(driver, siteUrl);
		BaseTest.logIn(driver,true, username, password);
		
		RegressionTests.checkInToAssignedJob(driver, _addTimeSheetPopOnCheckOut);
		JobDetailPage.clickMoreTab(driver);
		
		return driver;	
		}
	
    @Test (priority = 1)
	public void VerifyJobTimesheetAvailable() throws Exception
	{		
		MobileElement _isTimeSheetdisplayed = JobDetailPage.isViewTimeSheetDisplayed(driver);
		Assert.assertNotNull(_isTimeSheetdisplayed, "Expected View Timesheets on More but not found");
		_isTimeSheetdisplayed.click();
		Thread.sleep(1000);
		swipeIfPopUpShows(driver);
		
		String _getTitle = getTtile();
		Assert.assertEquals(_getTitle, "job timesheet", "Expected Job time sheet page but landed on "+_getTitle);
		JobDetailPage.clickBack(driver);
	}
    
    @Test(priority = 2)
    public void VerifyMenuTimesheetVisible() throws Exception
    {
       JobDetailPage.clickBack(driver);
       JobDetailPage.clickDoItLater(driver);
       Thread.sleep(1500);
        
       JobDetailPage.clickBack(driver);
        
       goToMenuTimesheet(driver);
       swipeIfPopUpShows(driver);
        
       Boolean _isTotalBilledHrsDisplayed = isTotalBilledHrdDisplayed(driver);
       Assert.assertNotNull(_isTotalBilledHrsDisplayed, "Error: Expected to find Total Billed hours but not found");        
	   JobDetailPage.clickBack(driver);  
	   
	   RegressionTests.checkInToAssignedJob(driver, _addTimeSheetPopOnCheckOut); // returning the driver back to jon time sheet to run the next test 
	   JobDetailPage.clickMoreTab(driver);
    }
	
    @Test (priority = 3)
	public void AddTimesheetEntryAndVerifyJobTimesheet() throws Exception
    {		
	   MobileElement _isTimeSheetdisplayed = JobDetailPage.isViewTimeSheetDisplayed(driver);
	   _isTimeSheetdisplayed.click();
	   Thread.sleep(1000);
		
	   swipeIfPopUpShows(driver); //Swipe left pop up shows once, so need to clear
       clickEntry(driver);
    	
       clickNext(driver);
        
       String  _additionalAgent = addAndReturnAdditionalAgent(driver);
       clickNext(driver);
       Thread.sleep(1500);
       clickNext(driver); //clicking on submit button
       Thread.sleep(1000);
        
       String _dateRecordedInitiallyText = getTtile(); //Date is recorded on the title section
       String _dateSubmitInitially = _dateRecordedInitiallyText.substring(19); //Removing the unwanted text

       String _billedhrsSubmitted = billedHrsSubmitted();
       
       clickNext(driver);       
       swipeIfPopUpShows(driver); //Swipe right pop up may appear so adding this here to clear    
             
       String _dateRecordedFinalText1 = getFinalDateFirstEntry().toLowerCase();
       String _dateRecordedFinallyLine1 = _dateRecordedFinalText1.substring(0,6);       
       Assert.assertEquals(_dateSubmitInitially, _dateRecordedFinallyLine1, "Incorrect date recorded found on First line Time sheet: "+ _dateRecordedFinallyLine1);
       
       String _dateRecordedFinalText2 = getFinalDateSeondEntry().toLowerCase();
       String _dateRecordedFinallyLine2 = _dateRecordedFinalText2.substring(0,6);
       Assert.assertEquals(_dateSubmitInitially, _dateRecordedFinallyLine2, "Incorrect date recorded found on Second line Time sheet: "+ _dateRecordedFinallyLine2);
       
       Boolean _additionalAgentFound = verifyIfAdditionalAgentFound(_additionalAgent);       
       Assert.assertTrue(_additionalAgentFound, "Incorrect additional agent found on Time sheet");
       
       String _billedhrsFound1stEntry = billedHrsFound_FirstEntry();
       Assert.assertEquals(_billedhrsSubmitted, _billedhrsFound1stEntry, "Incorrect Billed Hrs found on First Line Time sheet entry"+_billedhrsSubmitted+_billedhrsFound1stEntry);
       
       String _billedhrsFound2ndEntry = billedHrsFound_SecEntry();
       Assert.assertEquals(_billedhrsSubmitted, _billedhrsFound2ndEntry, "Incorrect Billed Hrs found on Second line Time sheet entry");
       
       JobDetailPage.clickBack(driver);
       JobDetailPage.clickBack(driver);
       
       RegressionTests.clickNo(driver);
       JobDetailPage.clickBack(driver);
       
	   RegressionTests.checkInToAssignedJob(driver, _addTimeSheetPopOnCheckOut); // returning the driver back to jon time sheet to run the next test 
	   JobDetailPage.clickMoreTab(driver);
    }
    
    @Test (priority =4)
    public void UpdateTimesheet() throws InterruptedException
    {
		MobileElement _isTimeSheetdisplayed = JobDetailPage.isViewTimeSheetDisplayed(driver);
		Assert.assertNotNull(_isTimeSheetdisplayed, "Expected View Timesheets on More but not found");
		_isTimeSheetdisplayed.click();
		Thread.sleep(1000);
		swipeIfPopUpShows(driver);
		
    	clickEntry(driver);
    	
        clickNext(driver);
        clickNext(driver);
        
        clickNext(driver);
        Thread.sleep(1000);
        
        String _dateRecorded = getTtile().toLowerCase();
        String _billedhrsSubmitted = billedHrsSubmitted();
        
        clickNext(driver);
    }
    
    public static void goToMenuTimesheet(AppiumDriver<MobileElement> driver) throws InterruptedException
    {
		RegressionTests.clickMenuHamburger(driver);
		RegressionTests.clickTimesheet(driver);	
    }
    
	public static void clickEntry(AppiumDriver<MobileElement> driver)
    {
    	driver.findElementById("action_new_entry").click();    	
    }

	public static void swipeIfPopUpShows(AppiumDriver<MobileElement> driver) throws InterruptedException
    {
    	List<MobileElement> elms = driver.findElementsById("ok_button");
    	
    	if(elms.size() == 1)
    	   elms.get(0).click();
    	   Thread.sleep(1600);
    }

	public String getTtile()
    {
		String _titleText = driver.findElementById("title").getText().toLowerCase();
        return _titleText;
    }
    
	public static void clickNext(AppiumDriver<MobileElement> driver)
    {
       driver.findElementById("next_button").click();
    }

	public static String addAndReturnAdditionalAgent(AppiumDriver<MobileElement> driver) throws InterruptedException
    {
    	driver.findElementById("add_more_agent").click();
    	String _additionalAgent = addAdditionalAgent(driver);
    	return _additionalAgent;
    }

	public static String addAdditionalAgent(AppiumDriver<MobileElement> driver) throws InterruptedException
    {
    	driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.Spinner/android.widget.RelativeLayout").click();
        Thread.sleep(500);
        driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.RelativeLayout[2]").click();
    	//driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.Spinner/android.widget.RelativeLayout/android.widget.ImageView").click();
        return driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.Spinner/android.widget.RelativeLayout/android.widget.TextView").getText().toLowerCase();
    }

	public String billedHrsSubmitted()
    {
    	return driver.findElementById("success_billed_time").getText().toLowerCase();
    }

	public String getFinalDateFirstEntry()
    {
    	return driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup[2]/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[1]/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.TextView[1]").getText();
    }
    
	public String getFinalDateSeondEntry()
    {
    	return driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup[2]/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[2]/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.TextView[1]").getText();
    }

	public String getFirstAdditionalAgent()
    {
    	return driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup[2]/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[1]/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.TextView[2]").getText().toLowerCase();
    }
	
	public String getSecAdditionalAgent()
	{
		return driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup[2]/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[2]/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.TextView[2]").getText().toLowerCase();
	}
	
	public Boolean verifyIfAdditionalAgentFound(String _additionalAgent)
	{
		String _1stAdditionalAgent = getFirstAdditionalAgent();
		String _2ndAdditionalAgent = getSecAdditionalAgent();
		
		if(_additionalAgent.equals(_1stAdditionalAgent) | _additionalAgent.equals(_2ndAdditionalAgent))
			return true;
		else
		    return false; 			
	}

	public String billedHrsFound_FirstEntry()
    {
    	String _s1 = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup[2]/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[1]/android.widget.RelativeLayout/android.widget.TextView").getText().toLowerCase();
        String _s1Min = _s1.substring(2);
        String s1Hrs = _s1.substring(0,1);
        
        if(_s1Min.equals("5h"))
        	_s1Min = "30min";
        else
        	_s1Min = "00min";
        
        return s1Hrs+"hr "+_s1Min;
    }

	public String billedHrsFound_SecEntry()
    {
		String _s2 = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup[2]/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[2]/android.widget.RelativeLayout/android.widget.TextView").getText().toLowerCase();
		String _s2Min = _s2.substring(2);
        String s2Hrs = _s2.substring(0,1);
        
        if(_s2Min.equals("5h"))
        	_s2Min = "30min";
        else
        	_s2Min = "00min";
        
        return s2Hrs+"hr "+_s2Min;
    }
	
	public boolean isTotalBilledHrdDisplayed(AppiumDriver<MobileElement> driver)
	{
		return driver.findElementById("sum_billed_title").isDisplayed();
	}
}
