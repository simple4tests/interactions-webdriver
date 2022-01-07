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

import com.google.common.collect.ImmutableList;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.Collection;
import java.util.function.Function;

public class Wait {

    private final WebDriver driver;
    private Duration interval;
    private Duration timeout;
    private Collection<Class<? extends Throwable>> exceptions;
    private boolean doNotCatchTimeoutException;

    public static final Duration DEFAULT_INTERVAL = Duration.ofMillis(50);
    public static final Duration DEFAULT_TIMEOUT = Duration.ofMillis(10000);
    public static final Collection<Class<? extends Throwable>> DEFAULT_EXCEPTIONS = ImmutableList.of(
            NoSuchElementException.class,
            StaleElementReferenceException.class,
            NoSuchFrameException.class,
            NoAlertPresentException.class);

    public Wait(WebDriver driver) {
        this.driver = driver;
        this.interval = DEFAULT_INTERVAL;
        this.timeout = DEFAULT_TIMEOUT;
        this.exceptions = DEFAULT_EXCEPTIONS;
        this.doNotCatchTimeoutException = true;
    }

    public Wait pollingEvery(Duration interval) {
        this.interval = interval;
        return this;
    }

    public Wait withTimeout(Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    public Wait ignoreAll(Collection<Class<? extends Throwable>> exceptions) {
        this.exceptions = exceptions;
        return this;
    }

    public Wait catchTimeoutException() {
        doNotCatchTimeoutException = false;
        return this;
    }

    public Boolean elementToBePresent(By by) {
        return expectedCondition(input -> 0 < driver.findElements(by).size());
    }

    public <T> T expectedCondition(Function<WebDriver, T> expectedCondition) {
        if (doNotCatchTimeoutException) {
            return waitUntil(expectedCondition, driver, interval, timeout, exceptions);
        }
        doNotCatchTimeoutException = true;
        try {
            return waitUntil(expectedCondition, driver, interval, timeout, exceptions);
        } catch (TimeoutException e) {
            return expectedCondition.apply(driver);
        }
    }

    public static <T> T waitUntil(
            final Function<WebDriver, T> expectedCondition,
            final WebDriver driver,
            final Duration interval,
            final Duration timeout,
            final Collection<Class<? extends Throwable>> exceptions) {
        return new FluentWait<>(driver)
                .pollingEvery(interval)
                .withTimeout(timeout)
                .ignoreAll(exceptions)
                .until(expectedCondition);
    }
}
