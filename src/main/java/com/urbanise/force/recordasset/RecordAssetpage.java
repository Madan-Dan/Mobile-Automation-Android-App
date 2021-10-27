package com.urbanise.force.recordasset;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.urbanise.force.andriod.base.BaseTest;
import com.urbanise.force.feature_testsuite.JobDetailPage;
import com.urbanise.force_RegressionTests.RegressionTests;

import org.openqa.selenium.interactions.Actions;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class RecordAssetpage extends BaseTest
{
	AppiumDriver<MobileElement> driver = null;
	   
	   public static int getCountOfImagesRecorded(AppiumDriver<MobileElement> driver)
	   {
		   return driver.findElementsById("force.urbanise.com.forceTest:id/item_image_selector_image").size();
	   }
   
	   public static String getLocation(AppiumDriver<MobileElement> driver)
	   {
		   return driver.findElementById("force.urbanise.com.forceTest:id/by_name").getText();
	   }
	   
	   public static String getElecCode(AppiumDriver<MobileElement> driver)
		{
			return driver.findElementById("force.urbanise.com.forceTest:id/barcode").getText();
		}
	   
	   public static String _getManufactureSerialNo(AppiumDriver<MobileElement> driver)
	   {
		   return driver.findElementById("force.urbanise.com.forceTest:id/serial").getText();
	   }
	   
	  public static String getCondition(AppiumDriver<MobileElement> driver)
	  {
		  return driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout[4]/android.widget.TextView[2]").getText();
	  }
	
   private void clickUpdateAsset() 
   {
	
   }

public static void clickRegionIcon(AppiumDriver<MobileElement> driver)
   {
	   driver.findElementById("force.urbanise.com.forceTest:id/action_region").click();
   }
   
   public static boolean isElectronicCodeReaderExists(AppiumDriver<MobileElement> driver)
   {
	   return driver.findElementById("force.urbanise.com.forceTest:id/barcode").isDisplayed();
   }
	
   public static String getTitleText(AppiumDriver<MobileElement> driver)
   {
	   String titlText = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.TextView").getText().toLowerCase();
	   return titlText;
   }
   
   public static boolean isSerialNoBarCodeReaderExists(AppiumDriver<MobileElement> driver)
   {
	  return driver.findElementById("force.urbanise.com.forceTest:id/serial").isDisplayed();
   }
   
   public static void clickBack(AppiumDriver<MobileElement> driver)
   {
	   driver.findElementByClassName("android.widget.ImageButton").click();
   }
   
   public static void clickGpsPin(AppiumDriver<MobileElement> driver)
   {
	   driver.findElementById("force.urbanise.com.forceTest:id/gpsLocationButton").click();
   }
   
   public static boolean doesGpsInfoExists(AppiumDriver<MobileElement> driver)
   {
	  String gpsinfo = driver.findElementById("force.urbanise.com.forceTest:id/gps").getText();
	  if(gpsinfo != null)
		  return true;
	  else
		  return false;	   
   }
   
   public static void clickX(AppiumDriver<MobileElement> driver)
   {
	   driver.findElementById("").click();
   }
   
   public static void searchAssetNo(AppiumDriver<MobileElement> driver, String AssetNo, Boolean _enterAssetNo, String _newValue) throws InterruptedException
   {
	   if(_enterAssetNo)
	   {   
	   clickSearchIcon(driver); 
	    Thread.sleep(1500);
	    enterAssetno(driver, RegressionTests.AssetNumber);
	   } 
	  else
		  enterAssetno(driver, RegressionTests.FillText+_newValue);
	  clickSearch(driver);
	  Thread.sleep(2000);
   }
   
   public static void clickSearchIcon(AppiumDriver<MobileElement> driver)
   {
	   driver.findElementById("force.urbanise.com.forceTest:id/action_search").click();
   }
   
   public static void enterAssetno(AppiumDriver<MobileElement> driver, String AssetNo)
   {
	   driver.findElementById("force.urbanise.com.forceTest:id/etAssetCode").sendKeys(AssetNo);
   }
   
   public static void clickSearch(AppiumDriver<MobileElement> driver)
   {
	   driver.findElementById("force.urbanise.com.forceTest:id/btnSearch").click();
   }
   
   public static String assetNumberFound(AppiumDriver<MobileElement> driver)
   {
	  return driver.findElementById("force.urbanise.com.forceTest:id/number").getText().toLowerCase();
   }
   
   public static void clickRecordNewAsset(AppiumDriver<MobileElement> driver) throws InterruptedException
   {
	   driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.appcompat.widget.LinearLayoutCompat/android.widget.FrameLayout/android.widget.ListView/android.widget.TextView[1]").click();
       Thread.sleep(2000);
   }
   
   public static void clickRecordNewOnPopUp(AppiumDriver<MobileElement> driver)
   {
	   driver.findElementById("force.urbanise.com.forceTest:id/positive").click();
   }
   
   public static void clickFindAsset(AppiumDriver<MobileElement> driver)
   {
	   driver.findElementById("force.urbanise.com.forceTest:id/positive").click();
   }     
}

