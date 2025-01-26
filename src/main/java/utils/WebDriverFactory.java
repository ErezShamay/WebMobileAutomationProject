package utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverFactory {
    private static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);
    private static ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

    public enum BrowserTypeOption {
        CHROMIUM,
        FIREFOX,
        WEBKIT
    }

    private static final String DEFAULT_BROWSER = ConfigLoader.getProperty("browser", "CHROMIUM");
    private static final boolean IS_HEADLESS = Boolean.parseBoolean(ConfigLoader.getProperty("headless", "true"));

    public static void initialize(BrowserTypeOption browserOption, boolean headless) {
        logger.info("Initializing browser: {} with headless: {}", browserOption, headless);
        if (browserThreadLocal.get() == null) {
            Playwright playwright = Playwright.create();
            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(headless);

            switch (browserOption) {
                case FIREFOX:
                    browserThreadLocal.set(playwright.firefox().launch(options));
                    break;
                case WEBKIT:
                    browserThreadLocal.set(playwright.webkit().launch(options));
                    break;
                case CHROMIUM:
                default:
                    browserThreadLocal.set(playwright.chromium().launch(options));
                    break;
            }

            pageThreadLocal.set(browserThreadLocal.get().newPage());
        }
    }

    public static void initialize(BrowserTypeOption browserOption) {
        initialize(browserOption, IS_HEADLESS);
    }

    public static void initialize() {
        initialize(BrowserTypeOption.valueOf(DEFAULT_BROWSER.toUpperCase()), IS_HEADLESS);
    }

    public static void initialize(boolean headless) {
        initialize(BrowserTypeOption.valueOf(DEFAULT_BROWSER.toUpperCase()), headless);
    }

    public static Page getPage() {
        return pageThreadLocal.get();
    }

    public static void close() {
        if (browserThreadLocal.get() != null) {
            browserThreadLocal.get().close();
            browserThreadLocal.remove();
            pageThreadLocal.remove();
        }
    }
}
