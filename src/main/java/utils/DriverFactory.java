package utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import java.util.Arrays;

public class DriverFactory {

    private static final boolean DEFAULT_HEADLESS = true;
    private static AndroidDriver<MobileElement> androidDriver;
    private static IOSDriver<MobileElement> iosDriver;

    public static Browser getBrowser(String browserName) {
        return getBrowser(browserName, DEFAULT_HEADLESS);
    }

    public static Browser getBrowser(String browserName, boolean headless) {
        Playwright playwright = Playwright.create();
        Browser browser;

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions();
        options.setHeadless(headless);

        options.setArgs(Arrays.asList(
                "--disable-gpu",
                "--disable-software-rasterizer",
                "--no-sandbox"
        ));

        switch (browserName.toLowerCase()) {
            case "chrome":
                browser = playwright.chromium().launch(options);
                break;
            case "firefox":
                browser = playwright.firefox().launch(options);
                break;
            case "webkit":
                browser = playwright.webkit().launch(options);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        return browser;
    }

    public static AndroidDriver<MobileElement> getAndroidDriver() {
        if (androidDriver == null) {
            try {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "RealDeviceName"); // Replace with your device name
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "yourAndroidVersion"); // Replace with Android version
                capabilities.setCapability(MobileCapabilityType.APP, "yourAppPathOrPerfectoURL"); // Replace with app path or Perfecto URL

                capabilities.setCapability("securityToken", "yourSecurityToken");
                capabilities.setCapability("cloudName", "yourPerfectoCloudName");

                androidDriver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return androidDriver;
    }

    public static IOSDriver<MobileElement> getIOSDriver() {
        if (iosDriver == null) {
            try {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "RealDeviceName"); // Replace with your device name
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "yourIOSVersion"); // Replace with iOS version
                capabilities.setCapability(MobileCapabilityType.APP, "yourAppPathOrPerfectoURL"); // Replace with app path or Perfecto URL

                capabilities.setCapability("securityToken", "yourSecurityToken");
                capabilities.setCapability("cloudName", "yourPerfectoCloudName");

                iosDriver = new IOSDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return iosDriver;
    }

    public static void close() {
        if (androidDriver != null) {
            androidDriver.quit();
        }
        if (iosDriver != null) {
            iosDriver.quit();
        }
    }
}
