package tests.web;

import org.testng.Assert;
import org.testng.annotations.*;
import com.microsoft.playwright.*;
import utils.WebDriverFactory;
import utils.Waits;
import webPageObjects.HomePage;
import webPageObjects.InnerPage;

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
        page.locator(HomePage.elementsCard).click();
        waits.waitForPageLoadState(com.microsoft.playwright.options.LoadState.LOAD);
    }

    @Test
    public void testValidateTextBoxOption() {
        WebDriverFactory.initialize(WebDriverFactory.BrowserTypeOption.FIREFOX, false);
        page = WebDriverFactory.getPage();
        waits = new Waits(page);
        page.navigate(HomePage.url);
        waits.waitForPageLoadState(com.microsoft.playwright.options.LoadState.LOAD);
        page.locator(HomePage.elementsCard).click();
        waits.waitForPageLoadState(com.microsoft.playwright.options.LoadState.LOAD);
        page.locator(InnerPage.textBoxOption).click();
        page.locator(InnerPage.userNameTextBox).fill(InnerPage.userName);
        page.locator(InnerPage.emailTextBox).fill(InnerPage.email);
        page.locator(InnerPage.currentAddressTextBox).fill(InnerPage.currentAddress);
        page.locator(InnerPage.permanentAddressTextBox).fill(InnerPage.permanentAddress);
        page.locator(InnerPage.submitButton).click();
        waits.waitForVisibility(InnerPage.outPutWindow);
        String name = page.locator(InnerPage.outPutWindowName).innerText();
        String email = page.locator(InnerPage.outPutWindowEmail).innerText();
        String currentAddress = page.locator(InnerPage.outPutWindowCurrentAddress).innerText();
        String permanentAddress = page.locator(InnerPage.outPutWindowPermanentAddress).innerText();
        Assert.assertTrue(name.contains(InnerPage.userName));
        Assert.assertTrue(email.contains(InnerPage.email));
        Assert.assertTrue(currentAddress.contains(InnerPage.currentAddress));
        Assert.assertTrue(permanentAddress.contains(InnerPage.permanentAddress));
    }

    @AfterClass
    public void tearDown() {
        WebDriverFactory.close();
    }
}
