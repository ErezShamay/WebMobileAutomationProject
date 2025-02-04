package tests.web;

import org.testng.annotations.*;
import com.microsoft.playwright.*;
import utils.WebDriverFactory;
import utils.Waits;
import webPageObjects.HomePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

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
        WebDriverFactory.initialize(WebDriverFactory.BrowserTypeOption.FIREFOX, false);
        page = WebDriverFactory.getPage();
        waits = new Waits(page);
        page.navigate(HomePage.url);
        waits.waitForVisibility(HomePage.pageTitle);
    }

    @Test
    public void runTestWithWebKit() {
        WebDriverFactory.initialize(WebDriverFactory.BrowserTypeOption.WEBKIT, false);
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

    @Test
    public void testValidateTabName() {
        WebDriverFactory.initialize(WebDriverFactory.BrowserTypeOption.FIREFOX, false);
        page = WebDriverFactory.getPage();
        waits = new Waits(page);
        page.navigate(HomePage.url);
        waits.waitForPageLoadState(com.microsoft.playwright.options.LoadState.LOAD);
        assertThat(page).hasTitle(HomePage.tabTitle);
        System.out.println("Tab name validated successfully: " + page.title());
    }

    @Test
    public void testValidateNavigation() {
        WebDriverFactory.initialize(WebDriverFactory.BrowserTypeOption.FIREFOX, false);
        page = WebDriverFactory.getPage();
        waits = new Waits(page);
        page.navigate(HomePage.url);
        waits.waitForPageLoadState(com.microsoft.playwright.options.LoadState.LOAD);
        Locator buttonClick = page.locator(HomePage.elementsCard);
        buttonClick.click();
        waits.waitForPageLoadState(com.microsoft.playwright.options.LoadState.LOAD);
    }

    @AfterClass
    public void tearDown() {
        WebDriverFactory.close();
    }
}
