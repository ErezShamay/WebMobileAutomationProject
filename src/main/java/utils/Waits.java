package utils;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

public class Waits {
    private final Page page;
    private final int defaultTimeout;

    public Waits(Page page, int defaultTimeout) {
        this.page = page;
        this.defaultTimeout = defaultTimeout;
    }

    public Waits(Page page) {
        this(page, 5000);
    }

    public void waitForVisibility(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(defaultTimeout));
    }

    public void waitForInvisibility(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.HIDDEN)
                .setTimeout(defaultTimeout));
    }

    public void waitForElementAttached(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(defaultTimeout));
    }

    public void waitForElementDetached(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.DETACHED)
                .setTimeout(defaultTimeout));
    }

    public void waitForPageLoadState(LoadState loadState) {
        page.waitForLoadState(loadState, new Page.WaitForLoadStateOptions()
                .setTimeout(defaultTimeout));
    }

    public void waitForCondition(String script) {
        page.waitForFunction(script, new Page.WaitForFunctionOptions()
                .setTimeout(defaultTimeout));
    }
}
