package tests.mobile;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DriverFactory;

public class ExampleMobileTest {

    private AndroidDriver<MobileElement> androidDriver;
    private IOSDriver<MobileElement> iosDriver;

    @BeforeClass
    public void setUp() {
        androidDriver = DriverFactory.getAndroidDriver();
        iosDriver = DriverFactory.getIOSDriver();
    }

    @Test
    public void testAndroidMobileAutomation() {
        androidDriver.launchApp();
        MobileElement element = androidDriver.findElementById("someElementId");
        element.click();
        String result = androidDriver.findElementById("resultId").getText();
        System.out.println("Android Test Result: " + result);
        Assert.assertTrue(result.contains("Expected Text"), "Android test failed!");
    }

    @Test
    public void testIOSMobileAutomation() {
        iosDriver.launchApp();
        MobileElement element = iosDriver.findElementById("someElementId");
        element.click();
        String result = iosDriver.findElementById("resultId").getText();
        System.out.println("iOS Test Result: " + result);
        Assert.assertTrue(result.contains("Expected Text"), "iOS test failed!");
    }

    @AfterClass
    public void tearDown() {
        if (androidDriver != null) {
            androidDriver.quit();
        }
        if (iosDriver != null) {
            iosDriver.quit();
        }
    }
}
