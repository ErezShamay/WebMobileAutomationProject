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
    private static final String DEFAULT_BROWSER = "chrome";

    private static final ThreadLocal<Playwright> threadLocalPlaywright = ThreadLocal.withInitial(Playwright::create);
    private static final ThreadLocal<Browser> threadLocalBrowser = new ThreadLocal<>();
    private static final ThreadLocal<AndroidDriver<MobileElement>> threadLocalAndroidDriver = new ThreadLocal<>();
    private static final ThreadLocal<IOSDriver<MobileElement>> threadLocalIOSDriver = new ThreadLocal<>();

    public static Browser getBrowser() {
        return getBrowser(DEFAULT_BROWSER, DEFAULT_HEADLESS);
    }

    public static Browser getBrowser(String browserName) {
        return getBrowser(browserName == null || browserName.isEmpty() ? DEFAULT_BROWSER : browserName, DEFAULT_HEADLESS);
    }

    public static Browser getBrowser(String browserName, boolean headless) {
        if (threadLocalBrowser.get() == null) {
            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                    .setHeadless(headless)
                    .setArgs(Arrays.asList("--disable-gpu", "--disable-software-rasterizer", "--no-sandbox"));

            Playwright playwright = threadLocalPlaywright.get();
            Browser browser;

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

            threadLocalBrowser.set(browser);
        }
        return threadLocalBrowser.get();
    }

    public static AndroidDriver<MobileElement> getAndroidDriver() {
        if (threadLocalAndroidDriver.get() == null) {
            try {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "RealDeviceName");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "yourAndroidVersion");
                capabilities.setCapability(MobileCapabilityType.APP, "yourAppPathOrPerfectoURL");
                capabilities.setCapability("securityToken", "yourSecurityToken");
                capabilities.setCapability("cloudName", "yourPerfectoCloudName");

                AndroidDriver<MobileElement> driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);
                threadLocalAndroidDriver.set(driver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return threadLocalAndroidDriver.get();
    }

    // Get an IOSDriver instance
    public static IOSDriver<MobileElement> getIOSDriver() {
        if (threadLocalIOSDriver.get() == null) {
            try {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "RealDeviceName");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "yourIOSVersion");
                capabilities.setCapability(MobileCapabilityType.APP, "yourAppPathOrPerfectoURL");
                capabilities.setCapability("securityToken", "yourSecurityToken");
                capabilities.setCapability("cloudName", "yourPerfectoCloudName");

                IOSDriver<MobileElement> driver = new IOSDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);
                threadLocalIOSDriver.set(driver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return threadLocalIOSDriver.get();
    }

    public static void close() {
        Browser browser = threadLocalBrowser.get();
        if (browser != null) {
            try {
                if (browser.isConnected()) {
                    browser.close();
                }
            } catch (Exception e) {
                System.err.println("Error closing browser: " + e.getMessage());
            } finally {
                threadLocalBrowser.remove();
            }

            Playwright playwright = threadLocalPlaywright.get();
            if (playwright != null) {
                try {
                    playwright.close();
                } catch (Exception e) {
                    System.err.println("Error closing Playwright: " + e.getMessage());
                } finally {
                    threadLocalPlaywright.remove();
                }
            }

            AndroidDriver<MobileElement> androidDriver = threadLocalAndroidDriver.get();
            if (androidDriver != null) {
                try {
                    androidDriver.quit();
                } catch (Exception e) {
                    System.err.println("Error quitting AndroidDriver: " + e.getMessage());
                } finally {
                    threadLocalAndroidDriver.remove();
                }
            }

            IOSDriver<MobileElement> iosDriver = threadLocalIOSDriver.get();
            if (iosDriver != null) {
                try {
                    iosDriver.quit();
                } catch (Exception e) {
                    System.err.println("Error quitting IOSDriver: " + e.getMessage());
                } finally {
                    threadLocalIOSDriver.remove();
                }
            }
        }
    }
}
