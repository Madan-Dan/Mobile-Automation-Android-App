package com.urbanise.force.recordasset;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.urbanise.force.andriod.base.BaseTest;
import com.urbanise.force_RegressionTests.RegressionTests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool.Record;

public class RecordAssetTests extends BaseTest
{
	AppiumDriver<MobileElement> driver = null;	

	String setPackage ="force.urbanise.com.forceTest";
	Boolean isHeadless = false;
	Boolean _addTimeSheetPopOnCheckOut = true;
	
	@BeforeTest	
	public AppiumDriver<MobileElement> OneTimeSetUp() throws Exception
		{	
		this.driver =  BaseTest.initialise(isHeadless, setPackage);
		BaseTest.changeSite(driver, siteUrl);
		BaseTest.logIn(driver,true, username, password);
	   
		   RegressionTests.goToRecordAssetPage(driver);
		   RegressionTests.clickOk(driver);
		   Thread.sleep(2000);
		   
		   RegressionTests.clickAssetHistoryIcon(driver);
		   String warningmessage= RegressionTests.getWarningText(driver).toLowerCase();
	   
	  if(warningmessage.equalsIgnoreCase("no assets recorded"))
	    {RecordAssetpage.clickBack(driver);
		  Thread.sleep(1000);
		  RegressionTests.fillSystemAttributes(driver);
		  
		  RegressionTests.enterAssetNumber(RegressionTests.AssetNumber, driver);
		  
		  RegressionTests.scrollDownPageToNextPage(driver);
		  Thread.sleep(1000);
		  
		 //Fill with First Location in the List
		  RegressionTests.downloadFirstLoc(driver);
		  RegressionTests.scrollDownPageToNextPage(driver);
		  Thread.sleep(2000);
		  
		  String _updateValue = "";		  
		  RegressionTests.fillCustomAttributes(driver, _updateValue); //, null);
		  
		  Thread.sleep(800); // adding a wait for the app to finish uploading images 
		  RegressionTests.scrollDownPageToNextPage(driver);
		  Thread.sleep(1000);
		  
		  RegressionTests.selectCondition(driver, 1);
		  RegressionTests.selectTrivial(driver);
		  Thread.sleep(500);	 
			 
			Thread.sleep(800);
			RegressionTests.scrollDownPage(driver);
			 
			//Takes 10 pictures and Saves
			RegressionTests.captureAndUploadMediaFile(driver);
			 
			Thread.sleep(800);
			RegressionTests.scrollDownPageToNextPage(driver);
			 
			RegressionTests.addTag(RegressionTests.Tag, driver);
			RegressionTests.addNotes(RegressionTests.FillText, driver);
			 
			RegressionTests.clickAddRecordbutton(driver);
			RegressionTests.clickOk(driver);	
			Thread.sleep(2000);
			 
			RecordAssetpage.clickRecordNewOnPopUp(driver);
			
			RecordAssetpage.searchAssetNo(driver, RegressionTests.AssetNumber, true, _updateValue);
	    }			
	return driver;	
			
	}
	   @Test (priority = 1)
	   public void RecordAndSearchAssetByAssetNo() throws Exception 
	   {			  
		  String _assetNumberFound = RecordAssetpage.assetNumberFound(driver);
		  Assert.assertEquals(RegressionTests.AssetNumber.toLowerCase(), _assetNumberFound, "Cannot find the recorded asset no");
    }
	   
	   @Test (priority = 2)
	   public void SearchAssetByElecCode() throws InterruptedException
	   {
		   String _updateValue = "";
		   RecordAssetpage.searchAssetNo(driver, RegressionTests.FillText, true, _updateValue);
		   
		   String _assetNumberFound = RecordAssetpage.assetNumberFound(driver);
		   Assert.assertEquals(RegressionTests.AssetNumber.toLowerCase(), _assetNumberFound, "Cannot find the recorded asset no");
	   	}
	   
	   @Test (priority =3)
	   public void UpdateAssetAndVerify() throws InterruptedException
	   {	  
		   String _updateValue = "updated";
		   String _newAssetNo = RegressionTests.AssetNumber+_updateValue;
		   
			 RegressionTests.enterAssetNumber(_newAssetNo, driver); // Updating the asset no to new
			  
			  RegressionTests.scrollDownPageToNextPage(driver);
			  Thread.sleep(1000);
			  				
			  RegressionTests.fillCustomAttributes(driver, _updateValue);
			  Thread.sleep(800); // adding a wait for the app to finish uploading images 
			  RegressionTests.scrollDownPageToNextPage(driver);
			  Thread.sleep(1000);
			  
			  RegressionTests.selectCondition(driver, 2);
			  RegressionTests.selectTrivial(driver);
			  Thread.sleep(500);
			  
				Thread.sleep(800);
				RegressionTests.scrollDownPage(driver);
				 
				//Takes 10 pictures and Saves
				RegressionTests.captureAndUploadMediaFile(driver);
				 
				Thread.sleep(800);
				RegressionTests.scrollDownPageToNextPage(driver);
			  
			    RegressionTests.addNotes(_updateValue, driver);  
		  
				RegressionTests.clickAddRecordbutton(driver);
				RegressionTests.clickOk(driver);	
				Thread.sleep(3000);
				
				RecordAssetpage.clickFindAsset(driver);
				Thread.sleep(4000);
				
				RecordAssetpage.searchAssetNo(driver, RegressionTests.AssetNumber, false, _updateValue);
				//RegressionTest.enterAssetNumberAfterUpdating(_newAssetNo, driver);
				String _assetNoFound = RecordAssetpage.assetNumberFound(driver);
				Assert.assertEquals(_assetNoFound, _newAssetNo.toLowerCase(), "Error incorrect asset no found after updating");
				
				String _location = RecordAssetpage.getLocation(driver);
				Assert.assertNotNull(_location, "location recorded when recording asset is not showing on the update asset page");
				
                String _elecCode = RecordAssetpage.getElecCode(driver);
                Assert.assertEquals(RegressionTests.FillText+_updateValue, _elecCode, "The Elec code is not updated pls check");
				
				RegressionTests.scrollDownPageToNextPage(driver);
				Thread.sleep(1000);
				
				String _manufactureSerialNo = RecordAssetpage._getManufactureSerialNo(driver);
				Assert.assertEquals(RegressionTests.FillText+_updateValue, _manufactureSerialNo, "The Manufacturer serial no is not updated");
				
				String _condition = RecordAssetpage.getCondition(driver);
				Assert.assertEquals(_condition, "Good");
				
				RegressionTests.scrollDownPageLittle(driver);
				Thread.sleep(1500);
				
				int _countOfImages = RecordAssetpage.getCountOfImagesRecorded(driver);
				Assert.assertEquals(_countOfImages, 20, "Expected to find 20 images");
	   } 	
	   
	   @AfterTest
	   public void driverQuit()
	   {
		   driver.quit();
	   } 
}
