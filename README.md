# Force-Mobile-Automation
Appium automated tests for the Plaza Force mobile

## Basic Setup for Mobile Automation

### Prerequisites:
Install Java on system
java version "1.8.0_241"
Java(TM) SE Runtime Environment (build 1.8.0_241-b07)
Java HotSpot(TM) 64-Bit Server VM (build 25.241-b07, mixed mode)
	
Set JAVA_HOME in environment variables
command to check: java -version
	
Set up Test App on any android mobile device OR Android Studio (Emulator)
Connect by passing in the UID of the android mobile device OR Android Studio (Emulator) 
ensure 200 MB to 1 GB of free space available on Mobile device

### Step by step guide to install the  Tools and Technologies:
	
Step 1: Download SDK tools
https://developer.android.com/studio
	
Step 2: Set environment variables
ANDROID_HOME = location of sdk folder
Path: append path of platform_tools folder

Step 3: Check command adb devices on command line

Step 4: Make device ready
enable developer mode on the mobile device	
enable USB Debugging on Android device

Run the following command along with bin folder location: cmd url
To find the bin folder location : Go to bin folder of the Android studio on ur pc and copy the url C:\Users\MadanKancharana\android-sdktools\tools\bin

Ex: C:\Users\MadanKancharana\android-sdktools\tools\bin 
	
Step 5: Install the relevant SDK packages:
Command to install SDK packages for the Android device 
sdkmanager "platform-tools" "platforms;android-28"	
Note : The Api level matching the device version is required. The Api level can be found here :
Url to download the packages: https://en.wikipedia.org/wiki/Android_version_history

Ex: If the Android o.s version is 9, the Api level is : 28
	
Step 5: Download and Install Eclipse IDE
Eclipse Java EE IDE for Web Developers.
Version: Neon.3 Release (4.6.3)
Build id: 20170314-1500
	
Step 6: Install TestNg libraries 
Url: https://www.seleniumeasy.com/testng-tutorials/how-to-install-testng-step-by-step
	
Step 7:Download and InstallAppium Desktop Client 
Set up the desired capabilities from base class on the Appium desktop client

### Framework:	
Page Object Model Framework
TestNg testing framework
	
### Tools to Push and Pull code to Git Repository:
Source Code resides in Git
SourceTree to push/ pull the code to Master



