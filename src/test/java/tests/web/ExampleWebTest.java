package tests.web;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DriverFactory;

public class ExampleWebTest {

    private Page webPage;

    @BeforeClass
    public void setUp() {
        Browser browser = DriverFactory.getBrowser();
        BrowserContext context = DriverFactory.getContext();
        webPage = DriverFactory.getPage();
    }

    @Test
    public void testWebAutomation() {
        webPage.navigate("https://example.com");
        String title = webPage.title();
        System.out.println("Webpage Title: " + title);
        Assert.assertEquals(title, "Example Domain", "Title does not match expected value!");
    }

    @AfterClass
    public void tearDown() {
        DriverFactory.close();
    }
}
