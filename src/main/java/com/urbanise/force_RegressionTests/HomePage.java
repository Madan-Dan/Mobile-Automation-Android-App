package com.urbanise.force_RegressionTests;

import com.urbanise.force.andriod.base.BaseTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class HomePage
{	
	public static void goToChangeSite(AppiumDriver<MobileElement> driver) throws InterruptedException
	{
		RegressionTests.clickMenuHamburger(driver);

		clickChangeSiteMenuItem(driver);
	}
	
	public static void clickChangeSiteMenuItem(AppiumDriver<MobileElement> driver)
	{
		driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.RelativeLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[5]").click();
	}
}
