package com.urbanise.force.feature_testsuite;

import java.time.LocalDateTime;

import com.urbanise.force.andriod.base.BaseTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class WorkInstructionsPage extends BaseTest
{
	static LocalDateTime dateTime = LocalDateTime.now();
	public static String _issueText = "Job raised from WI"+dateTime.getSecond()+dateTime.getDayOfYear();
	
	public static void clickWI(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/row_job_pre_work_instructions").click();
	}
	
	public static void clickOnCheklistItem_MoreLink(AppiumDriver<MobileElement> driver)
	{
		driver.findElementById("force.urbanise.com.forceTest:id/moreDetails").click();
	}
}
