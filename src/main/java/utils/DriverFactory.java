package utils;

import com.microsoft.playwright.*;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.MutableCapabilities;

import java.net.URL;
import java.util.List;

public class DriverFactory {

    private static Browser browser;
    private static BrowserContext context;
    private static AndroidDriver<MobileElement> androidDriver;
    private static IOSDriver<MobileElement> iosDriver;

    public static Browser getBrowser() {
        if (browser == null) {
            Playwright playwright = Playwright.create();
            BrowserType browserType = playwright.chromium();
            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                    .setArgs(List.of("--no-sandbox"))
                    .setHeadless(true); // Set to false if you want to see the browser UI
            browser = browserType.launch(options);
        }
        return browser;
    }

    public static BrowserContext getContext() {
        if (context == null) {
            context = getBrowser().newContext();
        }
        return context;
    }

    // Get page for Web Automation
    public static Page getPage() {
        return getContext().newPage();
    }

    public static AndroidDriver<MobileElement> getAndroidDriver() {
        if (androidDriver == null) {
            try {
                MutableCapabilities capabilities = new MutableCapabilities();
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.0"); // Set correct version
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Perfecto Android Device ID"); // Use the correct device ID
                capabilities.setCapability(MobileCapabilityType.APP, "cloud:your-app.apk"); // Path to the app in Perfecto Cloud
                capabilities.setCapability("perfecto:securityToken", "YOUR_PERFECTO_API_KEY"); // Replace with your API key
                capabilities.setCapability("appiumVersion", "1.22.0"); // Set Appium version if needed
                capabilities.setCapability("deviceOrientation", "PORTRAIT"); // Optional, set orientation as needed
                capabilities.setCapability("perfecto:deviceGroup", "YOUR_DEVICE_GROUP"); // Optional, set device group if needed

                androidDriver = new AndroidDriver<MobileElement>(new URL("https://YOUR_PERFECTO_CLOUD_URL"), capabilities);
            } catch (Exception e) {
                System.err.println("Error initializing Android driver: " + e.getMessage());
            }
        }
        return androidDriver;
    }

    public static IOSDriver<MobileElement> getIOSDriver() {
        if (iosDriver == null) {
            try {
                MutableCapabilities capabilities = new MutableCapabilities();
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "15.0"); // Set correct version
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Perfecto iOS Device ID"); // Use the correct device ID
                capabilities.setCapability(MobileCapabilityType.APP, "cloud:your-ios-app.ipa"); // Path to the app in Perfecto Cloud
                capabilities.setCapability("perfecto:securityToken", "YOUR_PERFECTO_API_KEY"); // Replace with your API key
                capabilities.setCapability("appiumVersion", "1.22.0"); // Set Appium version if needed
                capabilities.setCapability("deviceOrientation", "PORTRAIT"); // Optional, set orientation as needed
                capabilities.setCapability("perfecto:deviceGroup", "YOUR_DEVICE_GROUP"); // Optional, set device group if needed

                iosDriver = new IOSDriver<MobileElement>(new URL("https://YOUR_PERFECTO_CLOUD_URL"), capabilities);
            } catch (Exception e) {
                System.err.println("Error initializing iOS driver: " + e.getMessage());
            }
        }
        return iosDriver;
    }

    public static void close() {
        if (browser != null) {
            browser.close();
            browser = null;
            context = null;
        }
        if (androidDriver != null) {
            androidDriver.quit();
            androidDriver = null;
        }
        if (iosDriver != null) {
            iosDriver.quit();
            iosDriver = null;
        }
    }
}
