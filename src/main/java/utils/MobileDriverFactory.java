package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class MobileDriverFactory {

    private static final ThreadLocal<AppiumDriver<MobileElement>> driverThreadLocal = new ThreadLocal<>();

    public enum PlatformType {
        ANDROID,
        IOS
    }

    public enum DeviceType {
        LOCAL,
        PERFECTO
    }

    private static final String PERFECTO_URL = ConfigLoader.getProperty("perfectoUrl");
    private static final String PERFECTO_SECURITY_TOKEN = ConfigLoader.getProperty("perfectoSecurityToken");

    private static final String LOCAL_DEVICE_NAME = ConfigLoader.getProperty("localDeviceName");
    private static final String LOCAL_PLATFORM_VERSION = ConfigLoader.getProperty("localPlatformVersion");
    private static final String LOCAL_APP_PATH = ConfigLoader.getProperty("localAppPath");

    private static final PlatformType DEFAULT_PLATFORM = PlatformType.valueOf(ConfigLoader.getProperty("platform").toUpperCase());
    private static final DeviceType DEFAULT_DEVICE = DeviceType.valueOf(ConfigLoader.getProperty("device").toUpperCase());

    public static void initialize() {
        initialize(DEFAULT_PLATFORM, DEFAULT_DEVICE);
    }

    public static void initialize(PlatformType platformType, DeviceType deviceType) {
        if (driverThreadLocal.get() == null) {
            try {
                DesiredCapabilities capabilities = new DesiredCapabilities();

                if (deviceType == DeviceType.PERFECTO) {
                    capabilities.setCapability("platformName", platformType == PlatformType.ANDROID ? "Android" : "iOS");
                    capabilities.setCapability("deviceName", "Perfecto Virtual Device");
                    capabilities.setCapability("platformVersion", "latest");
                    capabilities.setCapability("app", ConfigLoader.getProperty("perfecto.appUrl"));
                    capabilities.setCapability("perfecto:securityToken", PERFECTO_SECURITY_TOKEN);
                    capabilities.setCapability("automationName", "Appium");

                    URL perfectoURL = new URL(PERFECTO_URL);
                    if (platformType == PlatformType.ANDROID) {
                        driverThreadLocal.set(new AndroidDriver<>(perfectoURL, capabilities));
                    } else if (platformType == PlatformType.IOS) {
                        driverThreadLocal.set(new IOSDriver<>(perfectoURL, capabilities));
                    }
                } else if (deviceType == DeviceType.LOCAL) {
                    capabilities.setCapability("platformName", platformType == PlatformType.ANDROID ? "Android" : "iOS");

                    if (platformType == PlatformType.ANDROID) {
                        capabilities.setCapability("deviceName", LOCAL_DEVICE_NAME);
                        capabilities.setCapability("platformVersion", LOCAL_PLATFORM_VERSION);
                        capabilities.setCapability("app", LOCAL_APP_PATH);
                        capabilities.setCapability("noReset", true);
                    } else if (platformType == PlatformType.IOS) {
                        capabilities.setCapability("deviceName", LOCAL_DEVICE_NAME);
                        capabilities.setCapability("platformVersion", LOCAL_PLATFORM_VERSION);
                        capabilities.setCapability("app", LOCAL_APP_PATH);
                        capabilities.setCapability("noReset", true);
                    }

                    URL localURL = new URL(ConfigLoader.getProperty("appiumLocalUrl"));
                    if (platformType == PlatformType.ANDROID) {
                        driverThreadLocal.set(new AndroidDriver<>(localURL, capabilities));
                    } else if (platformType == PlatformType.IOS) {
                        driverThreadLocal.set(new IOSDriver<>(localURL, capabilities));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static AppiumDriver<MobileElement> getDriver() {
        return driverThreadLocal.get();
    }

    public static void close() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }
}
