package tests.web;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import org.testng.annotations.Test;
import utils.DriverFactory;

public class ExampleWebTest {

    @Test
    public void testWebPageTitle () {
        Browser browser = DriverFactory.getBrowser("chrome");
        Page page = browser.newPage();
        page.navigate("https://www.example.com");
        System.out.println(page.title());
        browser.close();
    }
}
