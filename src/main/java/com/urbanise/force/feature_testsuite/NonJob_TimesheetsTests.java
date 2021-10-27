package com.urbanise.force.feature_testsuite;

import java.sql.Time;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.urbanise.force.andriod.base.BaseTest;
import com.urbanise.forceRegression_devicefarm.Timesheets;
import com.urbanise.force_RegressionTests.RegressionTests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

// ***Test Data Set ***//
//Ensure that there are 2 categories on Timesheet settings page  
// 1. Auto_Non Job Category_ Approval required 
// 2. Auto_Non Job Category_ No Approval required

public class NonJob_TimesheetsTests extends BaseTest
{
  AppiumDriver<MobileElement> driver = null; 
  String setPackage = "force.urbanise.com.forcePOC";
  //String setPackage ="force.urbanise.com.forceTest";
  Boolean isHeadless = true;
  Boolean _addTimeSheetPopOnCheckOut = true;
  
  String _nonjobtimesheetdescription ="Auto_Non job timesheets description";
  Double _billedhours = 1.15;
  String _rounded_billedhrs = "1.25h";
  String _billedhrs = Double.toString(_billedhours);

@BeforeTest
public AppiumDriver<MobileElement> OneTimeSetUp() throws Exception
{	
  this.driver =  BaseTest.initialise(isHeadless, setPackage);
  BaseTest.changeSite(driver, siteUrl);
  BaseTest.logIn(driver,true, username, password);  
  return driver;	
}

// Methods
  public void goToTimesheets() throws InterruptedException
  {
		TimesheetsTests.goToMenuTimesheet(driver);
		Timesheets.swipeIfPopUpShows(driver);
		
		TimesheetsPages.clickNewTimesheet(driver);		
  }

	@Test(priority = 1)
	public void VerifyNonJobTimeSheet_Approvalneeded() throws Exception
	{
		goToTimesheets();
		TimesheetsPages.selectApprovelReqCategory(driver);		
		TimesheetsPages.enterDescription(driver, _nonjobtimesheetdescription);
		TimesheetsPages.enterBilledHrs(driver, _billedhrs);
		
		TimesheetsPages.clickSubmitButton(driver);
		RegressionTests.clickOk(driver);
		
		TimesheetsPages.clickMon(driver);
		Timesheets.swipeIfPopUpShows(driver);
		TimesheetsPages.clickStatusIcon(driver);
		String _timesheestatus = TimesheetsPages.getTimeSheetEntryStatus(driver);
		
		Assert.assertEquals(_timesheestatus, "timesheet status is submitted", "Error");
	}

@Test(priority = 2)
public void AddNonJobTimeShteetEntry() throws Exception
{
	TimesheetsTests.goToMenuTimesheet(driver);
	driver.findElementById("ok_button").click();
	
	TimesheetsPages.clickNewTimesheet(driver);
		
	TimesheetsPages.selectFirstCategory(driver);
	TimesheetsPages.enterDescription(driver, _nonjobtimesheetdescription);
	TimesheetsPages.enterBilledHrs(driver, _billedhrs);
	
	TimesheetsPages.clickSubmitButton(driver);
	RegressionTests.clickOk(driver);
	
	TimesheetsPages.clickMon(driver);
	
	String _entryDate = TimesheetsPages.getEntryDate(driver);
	Assert.assertEquals("11 oct   ", _entryDate, "Error: Incorrect entry date found");
	
	String _billedtimefound = TimesheetsPages.getfirst_BiilldTime(driver);
	Assert.assertEquals(_billedtimefound, _rounded_billedhrs, "Error with billed time:"+_billedtimefound);
}

}
