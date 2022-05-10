/*
MIT License

Copyright (c) 2022 simple4tests

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package io.github.simple4tests.interactions.webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class WebDriverInteractions {

    protected static final String DEFAULT_SCROLL_BEHAVIOR = "auto";
    protected static final String DEFAULT_SCROLL_BLOCK = "center";
    protected static final String DEFAULT_SCROLL_INLINE = "center";

    protected String scrollBehavior;
    protected String scrollBlock;
    protected String scrollInline;

    protected boolean clearAll;
    protected boolean clearNext;

    public WebDriver driver;
    public JavascriptExecutor jsExecutor;
    public Wait wait;

    public WebDriverInteractions(WebDriver driver) {
        this.scrollBehavior = DEFAULT_SCROLL_BEHAVIOR;
        this.scrollBlock = DEFAULT_SCROLL_BLOCK;
        this.scrollInline = DEFAULT_SCROLL_INLINE;

        this.driver = driver;
        this.jsExecutor = (JavascriptExecutor) driver;
        this.wait = new Wait(driver);

        clearAll(false);
    }

    protected boolean isNull(CharSequence... value) {
        if (null == value) {
            return true;
        }
        for (CharSequence cs : value) {
            if (null == cs) {
                return true;
            }
        }
        return false;
    }

    protected boolean isNull(Boolean value) {
        return null == value;
    }

    protected boolean isNull(By by) {
        return null == by;
    }

    public Alert getAlert() {
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void switchToParentFrame() {
        driver.switchTo().parentFrame();
    }

    public void switchToFrame(By by) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
    }

    public void switchToFrame(int index) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
    }

    public void switchToFrame(String nameOrId) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrId));
    }

    /*
      Index starts at 0
     */
    public void switchToTab(int index) {
        wait.until(input -> index < driver.getWindowHandles().size());
        driver.switchTo().window(driver.getWindowHandles().toArray()[index].toString());
    }

    public void closeTab() {
        driver.close();
    }

    public void setScrollIntoViewOptions(String behavior, String block, String inline) {
        this.scrollBehavior = behavior;
        this.scrollBlock = block;
        this.scrollInline = inline;
    }

    public void scrollIntoView(WebElement webElement) {
        scrollIntoView(webElement, scrollBehavior, scrollBlock, scrollInline);
    }

    public void scrollIntoView(WebElement webElement, String behavior, String block, String inline) {
        jsExecutor.executeScript(
                String.format("arguments[0].scrollIntoView({behavior: '%s', block: '%s', inline: '%s'});", behavior, block, inline),
                webElement
        );
    }

    public WebElement getElement(By by) {
        waitToBePresent(by);
        return driver.findElement(by);
    }

    public WebElement getInteractableElement(By by) {
        return getInteractableElement(by, false, true, true);
    }

    public WebElement getInteractableElement(By by, boolean waitUntilElementIsDisplayed, boolean waitUntilElementIsEnabled, boolean scrollIntoView) {
        WebElement element = getElement(by);
        if (waitUntilElementIsDisplayed) wait.until(input -> element.isDisplayed());
        if (waitUntilElementIsEnabled) wait.until(input -> element.isEnabled());
        if (scrollIntoView) scrollIntoView(element);
        return element;
    }

    public int count(By by) {
        return isNull(by) ? 0 : driver.findElements(by).size();
    }

    public boolean isPresent(By by) {
        return 0 < count(by);
    }

    public boolean isAbsent(By by) {
        return 0 == count(by);
    }

    public void waitToBePresent(By by) {
        waitToBePresent(by, false);
    }

    public boolean waitToBePresent(By by, boolean ignoreTimeoutException) {
        if (ignoreTimeoutException)
            return wait.ignoreTimeoutException().until(input -> isPresent(by));
        return wait.until(input -> isPresent(by));
    }

    public void waitToBeAbsent(By by) {
        waitToBeAbsent(by, false);
    }

    public boolean waitToBeAbsent(By by, boolean ignoreTimeoutException) {
        if (ignoreTimeoutException)
            return wait.ignoreTimeoutException().until(input -> isAbsent(by));
        return wait.until(input -> isAbsent(by));
    }

    public void click(By by) {
        if (isNull(by)) {
            return;
        }
        try {
            getInteractableElement(by).click();
        } catch (ElementNotInteractableException e) {
            jsExecutor.executeScript(
                    "var evObj = new MouseEvent('click', {bubbles: true, cancelable: true, view: window});arguments[0].dispatchEvent(evObj);",
                    driver.findElement(by));
        }
    }

    public void dblclick(By by) {
        if (isNull(by)) {
            return;
        }
        jsExecutor.executeScript(
                "var evObj = new MouseEvent('dblclick', {bubbles: true, cancelable: true, view: window});arguments[0].dispatchEvent(evObj);",
                getInteractableElement(by)
        );
    }

    public void clearAll(boolean clearAll) {
        this.clearAll = this.clearNext = clearAll;
    }

    public WebDriverInteractions clearNext(boolean clearNext) {
        this.clearNext = clearNext;
        return this;
    }

    public void clear(By by) {
        if (isNull(by)) {
            return;
        }
        WebElement element = getInteractableElement(by);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        element.clear();
    }

    public void sendKeys(By by, CharSequence... value) {
        if (isNull(by) || isNull(value)) {
            return;
        }
        if (clearNext || 0 == value.length) {
            clear(by);
        }
        clearNext = clearAll;
        if (0 < value.length) {
            getInteractableElement(by).sendKeys(value);
        }
    }

    public void upload(By by, String fileAbsolutePath) {
        if (isNull(by) || isNull(fileAbsolutePath) || fileAbsolutePath.isEmpty()) {
            return;
        }
        getInteractableElement(by).sendKeys(fileAbsolutePath);
    }

    public void setSelected(By by, boolean select) {
        if (isNull(by) || isNull(select)) {
            return;
        }
        WebElement element = getInteractableElement(by);
        if (element.isSelected() != select) {
            element.click();
        }
    }

    public Select getSelect(By by) {
        if (isNull(by)) {
            return null;
        }
        return new Select(getInteractableElement(by));
    }

    private boolean visibleTextExists(Select select, String visibleText) {
        if (isNull(visibleText) || visibleText.isEmpty()) return true;
        for (WebElement option : select.getOptions())
            if (visibleText.trim().equals(option.getText().trim())) {
                return true;
            }
        return false;
    }

    private boolean valueExists(Select select, String value) {
        if (isNull(value) || value.isEmpty()) return true;
        for (WebElement option : select.getOptions())
            if (value.equals(option.getAttribute("value"))) {
                return true;
            }
        return false;
    }

    /*
      Index starts at 0
     */
    private boolean indexExists(Select select, int index) {
        if (index < 0) return true;
        for (WebElement option : select.getOptions())
            if (String.valueOf(index).equals(option.getAttribute("index"))) {
                return true;
            }
        return false;
    }

    private Select waitVisibleTextExists(Select select, String visibleText) {
        if (null != select) wait.ignoreTimeoutException().until(input -> visibleTextExists(select, visibleText));
        return select;
    }

    private Select waitValueExists(Select select, String value) {
        if (null != select) wait.ignoreTimeoutException().until(input -> valueExists(select, value));
        return select;
    }

    /*
      Index starts at 0
     */
    private Select waitIndexExists(Select select, int index) {
        if (null != select) wait.ignoreTimeoutException().until(input -> indexExists(select, index));
        return select;
    }

    public void selectByVisibleText(By by, String visibleText) {
        waitVisibleTextExists(getSelect(by), visibleText).selectByVisibleText(visibleText);
    }

    public void selectByValue(By by, String value) {
        waitValueExists(getSelect(by), value).selectByValue(value);
    }

    public void selectByIndex(By by, int index) {
        waitIndexExists(getSelect(by), index).selectByIndex(index);
    }
}
