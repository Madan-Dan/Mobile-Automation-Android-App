package com.urbanise.force.feature_testsuite;
import java.awt.List;

import com.urbanise.force.andriod.base.BaseTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class TimesheetsPages extends BaseTest
{
    
	public static void clickNewTimesheet(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("newTimeSheetButton").click();
	}
	
	public static  void selectFirstCategory(AppiumDriver<MobileElement> driver)
	{
		clickCategoryType(driver);
		selectFirstInList(driver);
	}
	
	public static void selectFirstInList(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.RelativeLayout[2]/android.widget.ImageView").click();
	}
	
	public static void enterDescription(AppiumDriver<MobileElement> driver, String _nonjobtimesheetdescription)
	{
		driver.findElementById("description").sendKeys(_nonjobtimesheetdescription);
	}
	
	public static void enterBilledHrs(AppiumDriver<MobileElement> driver, String s) throws Exception
	{
		driver.findElementById("monHours").sendKeys(s);
		Thread.sleep(1500);
	}
	
	public static void clickSubmitButton(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("save_button").click();
	}
	
	public static void clickMon(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.LinearLayout/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.TextView[1]").click();
	}
	
	public static String getEntryDate(AppiumDriver<MobileElement> driver)
	{
		java.util.List<MobileElement> _entrydates =  driver.findElementsById("entry_date");
		String _first_entrydate = _entrydates.get(0).getText().toLowerCase();
		return _first_entrydate;		
	}
	
	public static String getfirst_BiilldTime(AppiumDriver<MobileElement> driver)
	{
		java.util.List<MobileElement> _billedtimes =  driver.findElementsById("billed_time");
		String _first_billedtime = _billedtimes.get(0).getText().toLowerCase();
		return _first_billedtime;
	}
	
	public static void selectApprovelReqCategory(AppiumDriver<MobileElement> driver) throws InterruptedException
	{
		clickCategoryType(driver);
		Thread.sleep(1500);
		java.util.List<MobileElement> _categorytypes =  driver.findElementsById("title");
		int ss = _categorytypes.size();
		
		for(int i=0; i <= ss; i++ )
		{
			String value = _categorytypes.get(i).getText().toLowerCase();
			if(value.equals("auto_non job category_ approval required"))
			{
				_categorytypes.get(i).click();
				break;
			}
		}
	}
	
	public static void clickCategoryType(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("row_icon").click();
	}
	
	public static String getTimeSheetEntryStatus(AppiumDriver<MobileElement> driver)
	{
		return driver.findElementById("snackbar_text").getText().toLowerCase();
	}
	
	public static void clickStatusIcon(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.LinearLayout/android.view.ViewGroup/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.ImageView").click();
	}
}
