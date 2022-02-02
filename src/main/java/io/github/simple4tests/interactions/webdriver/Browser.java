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

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Browser {

    private final WebDriver driver;
    private final Wait wait;
    private final JavaScript javaScript;

    private String behavior;
    private String block;
    private String inline;

    public static final String DEFAULT_SCROLL_BEHAVIOR = "auto";
    public static final String DEFAULT_SCROLL_BLOCK = "center";
    public static final String DEFAULT_SCROLL_INLINE = "center";

    public Browser(WebDriver driver, Wait wait, JavaScript javaScript) {
        this.driver = driver;
        this.wait = wait;
        this.javaScript = javaScript;
        this.behavior = DEFAULT_SCROLL_BEHAVIOR;
        this.block = DEFAULT_SCROLL_BLOCK;
        this.inline = DEFAULT_SCROLL_INLINE;
    }

    public void setScrollIntoViewOptions(String behavior, String block, String inline) {
        this.behavior = behavior;
        this.block = block;
        this.inline = inline;
    }

    public void get(String url) {
        driver.get(url);
    }

    public void navigateTo(String url) {
        driver.navigate().to(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public Alert getAlert() {
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public void scrollIntoView(WebElement webElement) {
        scrollIntoView(webElement, behavior, block, inline);
    }

    public void scrollIntoView(WebElement webElement, String behavior, String block, String inline) {
        javaScript.execute(
                String.format("arguments[0].scrollIntoView({behavior: '%s', block: '%s', inline: '%s'});", behavior, block, inline),
                webElement
        );
    }

    public void switchToFirstTab() {
        switchToTab(0);
    }

    public void switchToTab(int index) {
        wait.until(input -> index < driver.getWindowHandles().size());
        driver.switchTo().window(driver.getWindowHandles().toArray()[index].toString());
    }

    public void closeTab() {
        driver.close();
    }

    public void quit() {
        driver.quit();
    }
}
