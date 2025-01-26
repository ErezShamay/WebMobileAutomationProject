package tests.web;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import com.microsoft.playwright.Page;
import utils.WebDriverFactory;

public class ExampleWebTest {

    private Page page;

    @Test
    public void runTestWithDefaultBrowser() {
        WebDriverFactory.initialize();
        page = WebDriverFactory.getPage();
        page.navigate("https://example.com");

    }

    @Test
    public void runTestWithFirefox() {
        WebDriverFactory.initialize(WebDriverFactory.BrowserTypeOption.FIREFOX);
        page = WebDriverFactory.getPage();
        page.navigate("https://example.com");

    }

    @Test
    public void runTestWithWebKit() {
        WebDriverFactory.initialize(WebDriverFactory.BrowserTypeOption.WEBKIT);
        page = WebDriverFactory.getPage();
        page.navigate("https://example.com");
    }

    @AfterClass
    public void tearDown() {
        WebDriverFactory.close();
    }
}
