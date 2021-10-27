package com.urbanise.force.feature_testsuite;

import org.apache.http.client.RedirectHandler;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.urbanise.force.andriod.base.BaseTest;
import com.urbanise.force_RegressionTests.RegressionTests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

// ------Test Data Set up ---------
// This test will require a Planned Job with Survey, so 
//1. Ensure that there is a specific _surveyUser for this survey
//2. Ensure that there is a Planned Job on the Planned tab
//3. Ensure there is Survey on this Planned Job
//4. Ensure the Survey has 4 line items  (Text type question, Yes No type question, Date type question, List type question)
//5. Ensure that tenant has the job template: Auto_Construction_job_template


public class SurveyTests extends BaseTest
{	
	AppiumDriver<MobileElement> driver = null;
	String siteUrl ="Master3.urbanisehq.com";
	String password = "admin123";
	
	@BeforeTest
	public void OneTimeSetup() throws Exception
	{
		this.driver = BaseTest.initialise(BaseTest.IsHeadless, BaseTest.setPackage);
		BaseTest.changeSite(driver, siteUrl);
		
		String _surveyUser = "t9@ex.com";
		BaseTest.logIn(driver, true, _surveyUser, password);		
	}
	
	@Test (priority = 1)
	public void RaiseJobFromSurvey() throws Exception
	{
		Boolean isSurveyJob = true;
		
		//RegressionTest.checkInToFirstPlannedJob(driver);
		RegressionTests.clickOnAssignedJob(driver);
		RaiseJobTests.clickOnSearchIcon(driver);
		RaiseJobTests.searchJobNo(driver);
		JobDetailPage.clickOnSurvey(driver);
		
		JobDetailPage.clickOnSurveyQuestionSet(driver);
		JobDetailPage.clickPlus(driver);
		
		JobDetailPage.clickRaiseJob(driver);
		
		JobDetailPage.fillJobFields(driver, isSurveyJob, RegressionTests.jobtemplate);
				
		JobDetailPage.enterJobNotes(driver, RegressionTests.text);		 
		RaiseJobTests.clickRaiseJobButton(driver);
		
		Thread.sleep(2000);
		String jobnumber = RaiseJobTests.getJobNumber(driver);
		
		RegressionTests.clickOk(driver);
		     
		RegressionTests.goToReactiveJobList(driver);
		
		String _jobNoFound = RegressionTests.getCreatedJobNo(driver, isSurveyJob);
		Assert.assertTrue(jobnumber.toLowerCase().contains(_jobNoFound.toLowerCase()), "Error: Created Job Number" + jobnumber + " found on Assigned Job list:"+_jobNoFound); 
		
		RegressionTests.clickFirstAssignedJobNo(driver);		
		JobDetailPage.clickCheckIn(driver);
		
		JobDetailPage.closeJob(driver);	
		
        resetFilterOnAssignedJobList();		
	}
	
	public void resetFilterOnAssignedJobList()
	{
		RaiseJobTests.clickRaiseJobButton(driver);
		JobDetailPage.clickAll(driver);
	}
}
