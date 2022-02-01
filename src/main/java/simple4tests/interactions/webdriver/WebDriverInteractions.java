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

package simple4tests.interactions.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class WebDriverInteractions {

    protected boolean clear;
    protected boolean alwaysClear;

    public WebDriver driver;
    public Wait wait;
    public JavaScript javaScript;
    public Browser browser;

    public WebDriverInteractions(WebDriver driver) {
        this.driver = driver;
        this.clear = false;
        this.alwaysClear = false;

        wait = new Wait(driver);
        javaScript = new JavaScript(driver);
        browser = new Browser(driver, wait, javaScript);
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

    public WebDriverInteractions clear() {
        clear = true;
        return this;
    }

    public void setClearOption(boolean alwaysClear) {
        this.alwaysClear = alwaysClear;
    }

    public WebElement getElement(By by) {
        wait.elementToBePresent(by);
        return driver.findElement(by);
    }

    public WebElement getInteractableElement(By by) {
        WebElement element = getElement(by);
        wait.until(input -> element.isDisplayed());
        wait.until(input -> element.isEnabled());
        browser.scrollIntoView(element);
        return element;
    }

    public boolean isElementPresent(By by) {
        return 0 < countElements(by);
    }

    public int countElements(By by) {
        return isNull(by) ? 0 : driver.findElements(by).size();
    }

    public void clear(By by) {
        if (isNull(by)) {
            return;
        }
        WebElement element = getInteractableElement(by);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        element.clear();
    }

    public void set(By by, CharSequence... value) {
        if (clear || alwaysClear) {
            clear = false;
            clear(by);
        }
        if (isNull(by) || isNull(value)) {
            return;
        }
        if (0 == value.length) {
            clear(by);
            return;
        }
        getInteractableElement(by).sendKeys(value);
    }

    public void select(By by, Boolean value) {
        if (isNull(by) || isNull(value)) {
            return;
        }
        WebElement element = getInteractableElement(by);
        if (!value.equals(element.isSelected())) {
            element.click();
        }
    }

    public void selectByVisibleText(By by, String text) {
        if (isNull(by) || isNull(text)) {
            return;
        }
        WebElement element = getInteractableElement(by);
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        new Select(element).selectByVisibleText(text);
    }

    public void click(By by) {
        if (isNull(by)) {
            return;
        }
        getInteractableElement(by).click();
    }

    public void doubleClick(By by) {
        if (isNull(by)) {
            return;
        }
        javaScript.execute(
                "var evObj = new MouseEvent('dblclick', {bubbles: true, cancelable: true, view: window});arguments[0].dispatchEvent(evObj);",
                getInteractableElement(by)
        );
    }

    public void upload(By by, String fileAbsolutePath) {
        if (isNull(by) || isNull(fileAbsolutePath) || fileAbsolutePath.isEmpty()) {
            return;
        }
        getInteractableElement(by).sendKeys(fileAbsolutePath);
    }

    public String getText(By by) {
        if (isNull(by)) {
            return "";
        }
        return getElement(by).getText();
    }

    public String getAttribute(By by, String attribute) {
        if (isNull(by) || isNull(attribute)) {
            return "";
        }
        return getElement(by).getAttribute(attribute);
    }

    public void switchToMainFrame() {
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
}
