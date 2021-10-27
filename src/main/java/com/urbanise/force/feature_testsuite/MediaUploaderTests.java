package com.urbanise.force.feature_testsuite;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;
import com.urbanise.force.andriod.base.BaseTest;
import com.urbanise.force_RegressionTests.RegressionTests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class MediaUploaderTests extends BaseTest
{
  AppiumDriver <MobileElement> driver = null;

 @BeforeTest
 public void OneTimeSetup() throws Exception
 {
	 this.driver = BaseTest.initialise(IsHeadless, setPackage);
	 BaseTest.changeSite(driver, siteUrl);
	 BaseTest.logIn(driver, true, username, password);
 }
 
 @Test (priority =1)
 public void VerifyImageSavesOnRaiseJob() throws InterruptedException
 {
	 RaiseJobTests.clickOnRaiseJob(driver);
	 RegressionTests.captureImage(driver);
	 
	 RegressionTests.scrollDownPageLittle(driver);
	 Thread.sleep(1500);
	 
	 int _imagesFound = JobDetailPage.getNoOfImages(driver);
	 Assert.assertEquals(_imagesFound, 1, "Error: The captured image is not saved on raise job page");
	 
	 JobDetailPage.clickBack(driver); 
 }
 
 @Test (priority =2)
 public void VerifyImagesSaveonAdhocJob() throws Exception
 {
	 RegressionTests.checkInToAssignedJob(driver, null);
	 JobDetailPage.clickMoreTab(driver);
	 
	 JobDetailPage.ClickAddJobToWO(driver);
	 RegressionTests.captureImage(driver);
	 
	 int _imagesFound = JobDetailPage.getNoOfImages(driver);
	 Assert.assertEquals(_imagesFound, 1,"Expected to see 1 image attached but found:"+_imagesFound);
	 
	 JobDetailPage.clickBack(driver);
	 
	 JobDetailPage.clickBack(driver);
	 RegressionTests.clickNo(driver);
	 
	 JobDetailPage.clickBack(driver);
}
 
 @Test (priority =3)
 public void VerifyImagesSaveonAddJobtoExistingWO() throws Exception
 {
	 RegressionTests.checkInToAssignedJob(driver, null);
	 JobDetailPage.clickMoreTab(driver);
	 
	 JobDetailPage.ClickAddJobToWO(driver);
     RegressionTests.captureImage(driver);
	 
	 int _imagesFound = JobDetailPage.getNoOfImages(driver);
	 Assert.assertEquals(_imagesFound, 1,"Expected to see 1 image attached but found:"+_imagesFound);
	 
	 JobDetailPage.clickBack(driver);
	 
	 JobDetailPage.clickBack(driver);
	 RegressionTests.clickNo(driver);
	 
	 JobDetailPage.clickBack(driver);	
 }
 
 @Test(priority = 4)
 public void AddImageOnJobAttachments() throws Exception
 {
	 RegressionTests.checkInToAssignedJob(driver, null);
	 JobDetailPage.clickAttachment(driver);
	 
	 JobDetailPage.clickClose(driver);
	 
	 int _noOfImages = JobDetailPage.getNoOfImages(driver);

	 JobDetailPage.clickAttachmentLink(driver);
	 JobDetailPage.clickMedia(driver);
	 RegressionTests.captureImage(driver);
	 
	 int _imagesFound = JobDetailPage.getNoOfImages(driver);
	 
	 Assert.assertEquals(_imagesFound, _noOfImages +1, "Error: Could not attach image to Job attachment");
	 JobDetailPage.clickBack(driver);
	 RegressionTests.clickNo(driver);
	 
	 JobDetailPage.clickBack(driver);	 
 }
 
// ***Test Data Set up for running below tests***
// 1. Ensure that tenant has the following Job template :Auto_Construction_job_template 
// 2. Ensure that this job template has pre check in work instructions: Auto_Work Instruction set 1
// 3. Ensure that this work instruction has 5 checklist items 
//    (Pass / Fail, Yes/No, Number score, list(0,1), Text)
 
 public static String raiseJob_WithWorkInstrAnd_CheckIn(AppiumDriver<MobileElement> driver) throws Exception
 {
	 RaiseJobTests.clickOnRaiseJob(driver); 
	 
	 String _jobNumber = RegressionTests.raiseJob(driver, RegressionTests.text, RegressionTests.jobtemplate);
	 RegressionTests.checkInToAssignedJob(driver, null);

	 return _jobNumber;
 }
 
 @Test(priority= 5)
 public void VerifyAddingImageOnWI_ChecklistItem() throws Exception
 {
	 //raiseJob_WithWorkInstrAnd_CheckIn(driver);	
	 RegressionTests.clickOnAssignedJob(driver);
	 driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "Go"));
	 
	 WorkInstructionsPage.clickWI(driver); //CLicks on Work instruction on Job detail page
	 WorkInstructionsPage.clickOnCheklistItem_MoreLink(driver);
	 
	 JobDetailPage.clickUploadFile(driver, false);
	 JobDetailPage.clickMedia(driver);
	 
	 RegressionTests.captureImage(driver);
	 
	 int _imagesFound = JobDetailPage.getNoOfImagesOnWIAttachment(driver);
	 Assert.assertEquals(_imagesFound, 1,"Expected to see 1 image attached but found:"+_imagesFound);
	 
	 JobDetailPage.clickSave(driver);
	 JobDetailPage.clickBack(driver);
	 
	 JobDetailPage.clickAttachment(driver);
	 JobDetailPage.clickClose(driver);
	 
	 int _imagesFoundOnAtt = JobDetailPage.getNoOfImages(driver);
	 Assert.assertEquals(_imagesFoundOnAtt, _imagesFoundOnAtt+1,"Expected to see 1 image attached but found:"+_imagesFound);	
 } 
}
