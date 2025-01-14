package tests.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.MobileDriverFactory;

public class ExampleMobileTest {

    @BeforeMethod
    public void setUp() {
        // Default: Initialize the Appium driver for Android on Perfecto cloud
        MobileDriverFactory.initialize(MobileDriverFactory.PlatformType.ANDROID, MobileDriverFactory.DeviceType.PERFECTO);
    }

    @Test
    public void runTestOnPerfecto() {
        // Get the Appium driver instance for Perfecto
        AppiumDriver<MobileElement> driver = MobileDriverFactory.getDriver();

        // Perform actions on the mobile app using the driver
        MobileElement element = driver.findElementById("com.example:id/button");
        element.click();

        // Add your test steps here
    }

    @Test
    public void runTestOnRealDevice() {
        // Switch to a real device (local) for testing
        MobileDriverFactory.initialize(MobileDriverFactory.PlatformType.ANDROID, MobileDriverFactory.DeviceType.LOCAL);

        // Get the Appium driver instance for the real device
        AppiumDriver<MobileElement> driver = MobileDriverFactory.getDriver();

        // Perform actions on the mobile app using the driver
        MobileElement element = driver.findElementById("com.example:id/button");
        element.click();

        // Add your test steps here
    }

    @AfterMethod
    public void tearDown() {
        // Close the Appium driver after the test
        MobileDriverFactory.close();
    }
}
