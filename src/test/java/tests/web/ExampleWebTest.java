package tests.web;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DriverFactory;
import webPageObjects.LoginPage;

public class ExampleWebTest {
    private Browser browser;
    private final LoginPage loginPageObject;
    private static final Logger logger = LogManager.getLogger(ExampleWebTest.class);

    public ExampleWebTest() {
        this.loginPageObject = new LoginPage();
    }

    @BeforeClass
    public void setup() {
        this.browser = DriverFactory.getBrowser();
    }

    @Test
    public void testWebPageTitle() {
        Page page = browser.newPage();
        page.navigate(loginPageObject.loginUrl);
        String pageTitle = page.title();
        logger.info(pageTitle);
        Assert.assertEquals(pageTitle, "Example Domain", "Page title doesn't match!");
    }

    @Test
    public void testWebPageTitleWithSpecificBrowser() {
        Browser firefoxBrowser = DriverFactory.getBrowser("firefox");
        Page page = firefoxBrowser.newPage();
        page.navigate(loginPageObject.loginUrl);
        String pageTitle = page.title();
        logger.info(pageTitle);
        Assert.assertEquals(pageTitle, "Example Domain", "Page title doesn't match!");
    }

    @Test
    public void testWebPageTitleWithSpecificBrowserNoHeadLess() {
        Browser firefoxBrowser = DriverFactory.getBrowser("firefox", false);
        Page page = firefoxBrowser.newPage();
        page.navigate(loginPageObject.loginUrl);
        String pageTitle = page.title();
        logger.info(pageTitle);
        Assert.assertEquals(pageTitle, "Example Domain", "Page title doesn't match!");
    }

    @AfterClass
    public void tearDown() {
        if (browser != null) {
            browser.close();
        }
    }
}
