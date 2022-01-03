package simple4tests.interactions.webdriver;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Browser {

    private WebDriver driver;
    private Wait wait;
    private JavaScript javaScript;

    public Browser(WebDriver driver, Wait wait, JavaScript javaScript) {
        this.driver = driver;
        this.wait = wait;
        this.javaScript = javaScript;
    }

    public void get(final String url) {
        driver.get(url);
    }

    public void navigateTo(final String url) {
        driver.navigate().to(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public Alert getAlert() {
        return wait.expectedCondition(ExpectedConditions.alertIsPresent());
    }

    public void scrollIntoView(final WebElement webElement, final String behavior, final String block, final String inline) {
        javaScript.execute(
                String.format("arguments[0].scrollIntoView({behavior: '%s', block: '%s', inline: '%s'});", behavior, block, inline),
                webElement
        );
    }

    public void switchToFirstTab() {
        switchToTab(0);
    }

    public void switchToTab(final int index) {
        driver.switchTo().window(driver.getWindowHandles().toArray()[index].toString());
    }

    public void closeTab() {
        driver.close();
    }

    public void switchToMainFrame() {
        driver.switchTo().defaultContent();
    }

    public void switchToParentFrame() {
        driver.switchTo().parentFrame();
    }

    public void switchToFrame(final By by) {
        wait.expectedCondition(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
    }

    public void switchToFrame(final int index) {
        wait.expectedCondition(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
    }

    public void switchToFrame(final String nameOrId) {
        wait.expectedCondition(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrId));
    }

    public void quit() {
        driver.quit();
    }
}
