package utils;

import com.microsoft.playwright.*;

public class WebDriverFactory {
    private static ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

    public enum BrowserTypeOption {
        CHROMIUM,
        FIREFOX,
        WEBKIT
    }

    private static final String DEFAULT_BROWSER = ConfigLoader.getProperty("browser", "CHROMIUM");
    private static final boolean IS_HEADLESS = Boolean.parseBoolean(ConfigLoader.getProperty("headless", "true"));

    public static void initialize(BrowserTypeOption browserOption) {
        if (browserThreadLocal.get() == null) {
            Playwright playwright = Playwright.create();
            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(IS_HEADLESS);

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

    public static void initialize() {
        initialize(BrowserTypeOption.valueOf(DEFAULT_BROWSER.toUpperCase()));
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
