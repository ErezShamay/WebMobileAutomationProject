package tests.web;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DriverFactory;
import webPageObjects.Login;

public class ExampleWebTest {
    private Browser browser;
    private final Login loginPageObject;

    public ExampleWebTest() {
        this.loginPageObject = new Login();
    }

    @BeforeClass
    public void setup() {
        this.browser = DriverFactory.getBrowser();
    }

    @Test
    public void testWebPageTitle() {
        Page page = browser.newPage();
        page.navigate(loginPageObject.loginUrl);
        System.out.println(page.title());
    }

    @Test
    public void testWithSpecificBrowser() {
        Browser firefoxBrowser = DriverFactory.getBrowser("firefox");
        Page page = firefoxBrowser.newPage();
        page.navigate(loginPageObject.loginUrl);
        System.out.println(page.title());
    }

    @Test
    public void testWithSpecificBrowserNoHeadLess() {
        Browser firefoxBrowser = DriverFactory.getBrowser("firefox", false);
        Page page = firefoxBrowser.newPage();
        page.navigate(loginPageObject.loginUrl);
        System.out.println(page.title());
    }

    @AfterClass
    public void tearDown() {
        if (browser != null) {
            browser.close();
        }
    }
}
