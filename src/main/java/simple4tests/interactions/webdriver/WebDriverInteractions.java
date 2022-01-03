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
import org.openqa.selenium.support.ui.Select;

public class WebDriverInteractions {

    private static final String DEFAULT_SCROLL_BEHAVIOR = "auto";
    private static final String DEFAULT_SCROLL_BLOCK = "center";
    private static final String DEFAULT_SCROLL_INLINE = "center";

    private String behavior;
    private String block;
    private String inline;

    protected WebDriver driver;

    public Wait wait;
    public JavaScript javaScript;
    public Browser browser;

    public WebDriverInteractions(WebDriver driver) {
        this.driver = driver;
        this.behavior = DEFAULT_SCROLL_BEHAVIOR;
        this.block = DEFAULT_SCROLL_BLOCK;
        this.inline = DEFAULT_SCROLL_INLINE;

        wait = new Wait(driver);
        javaScript = new JavaScript(driver);
        browser = new Browser(driver, wait, javaScript);
    }

    public void setScrollOptions(String behavior, String block, String inline) {
        this.behavior = behavior;
        this.block = block;
        this.inline = inline;
    }

    protected boolean isNull(By by) {
        return null == by;
    }

    protected boolean isNull(Boolean value) {
        return null == value;
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

    protected WebElement getElementWhenPresent(By by) {
        wait.elementToBePresent(by);
        return driver.findElement(by);
    }

    protected WebElement getElementWhenDisplayed(By by) {
        WebElement element = getElementWhenPresent(by);
        browser.scrollIntoView(element, behavior, block, inline);
        return element;
    }

    protected WebElement getElementWhenEnabled(By by) {
        WebElement element = getElementWhenDisplayed(by);
        wait.elementToBeEnabled(element);
        return element;
    }

    public void clear(By by) {
        if (isNull(by)) {
            return;
        }
        WebElement element = getElementWhenEnabled(by);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        element.clear();
    }

    public void set(By by, CharSequence... value) {
        if (isNull(by) || isNull(value)) {
            return;
        }
        if (0 == value.length) {
            clear(by);
            return;
        }
        getElementWhenEnabled(by).sendKeys(value);
    }

    public void select(By by, Boolean value) {
        if (isNull(by) || isNull(value)) {
            return;
        }
        WebElement element = getElementWhenEnabled(by);
        if (!value.equals(element.isSelected())) {
            element.click();
        }
    }

    public void selectByVisibleText(By by, String text) {
        if (isNull(by) || isNull(text)) {
            return;
        }
        new Select(getElementWhenEnabled(by)).selectByVisibleText(text);
    }

    public void click(By by) {
        if (isNull(by)) {
            return;
        }
        getElementWhenEnabled(by).click();
    }

    public void doubleClick(By by) {
        if (isNull(by)) {
            return;
        }
        javaScript.execute(
                "var evObj = new MouseEvent('dblclick', {bubbles: true, cancelable: true, view: window});arguments[0].dispatchEvent(evObj);",
                getElementWhenEnabled(by)
        );
    }

    public void upload(By by, String fileAbsolutePath) {
        if (isNull(by) || isNull(fileAbsolutePath) || fileAbsolutePath.isEmpty()) {
            return;
        }
        getElementWhenEnabled(by).sendKeys(fileAbsolutePath);
    }

    public String getText(By by) {
        if (isNull(by)) {
            return "";
        }
        return getElementWhenDisplayed(by).getText();
    }

    public String getAttribute(By by, String attribute) {
        if (isNull(by) || isNull(attribute)) {
            return "";
        }
        return getElementWhenDisplayed(by).getAttribute(attribute);
    }

    public int countElements(By by) {
        if (isNull(by)) {
            return 0;
        }
        return driver.findElements(by).size();
    }
}
