package tests.web;

import org.testng.annotations.*;

import com.microsoft.playwright.Page;
import utils.WebDriverFactory;
import utils.Waits;
import webPageObjects.HomePage;

public class ExampleWebTest {

    private Page page;
    private Waits waits;

    @Test
    public void runTestWithDefaultBrowser() {
        WebDriverFactory.initialize();
        page = WebDriverFactory.getPage();
        waits = new Waits(page);
        page.navigate(HomePage.url);
    }

    @Test
    public void runTestWithFirefox() {
        WebDriverFactory.initialize(WebDriverFactory.BrowserTypeOption.FIREFOX, false);
        page = WebDriverFactory.getPage();
        waits = new Waits(page);
        page.navigate(HomePage.url);
        waits.waitForVisibility(HomePage.pageTitle);
    }

    @Test
    public void runTestWithFirefoxNoHeadless() {
        WebDriverFactory.initialize(WebDriverFactory.BrowserTypeOption.FIREFOX);
        page = WebDriverFactory.getPage();
        waits = new Waits(page);
        page.navigate(HomePage.url);
        waits.waitForVisibility(HomePage.pageTitle);
    }

    @Test
    public void runTestWithWebKit() {
        WebDriverFactory.initialize(WebDriverFactory.BrowserTypeOption.WEBKIT);
        page = WebDriverFactory.getPage();
        waits = new Waits(page);
        page.navigate(HomePage.url);
        waits.waitForPageLoadState(com.microsoft.playwright.options.LoadState.LOAD);
    }

    @Test
    public void runTestWithWebKitNoHeadless() {
        WebDriverFactory.initialize(WebDriverFactory.BrowserTypeOption.WEBKIT, false);
        page = WebDriverFactory.getPage();
        waits = new Waits(page);
        page.navigate(HomePage.url);
        waits.waitForPageLoadState(com.microsoft.playwright.options.LoadState.LOAD);
    }

    @AfterClass
    public void tearDown() {
        WebDriverFactory.close();
    }
}
