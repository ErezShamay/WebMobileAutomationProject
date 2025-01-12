package tests.mobile;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.DriverFactory;

public class ExampleMobileTest {

    @Test
    public void testAndroidRealDevice() {
        AndroidDriver<MobileElement> driver = DriverFactory.getAndroidDriver();
        driver.launchApp();
        Assert.assertEquals(driver.findElementById("com.example.yourapp:id/textView").getText(), "Expected Text");
        DriverFactory.close();
    }

    @Test
    public void testIOSRealDevice() {
        IOSDriver<MobileElement> driver = DriverFactory.getIOSDriver();
        driver.launchApp();
        Assert.assertEquals(driver.findElementById("com.example.yourapp:id/textView").getText(), "Expected Text");
        DriverFactory.close();
    }
}
