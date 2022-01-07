package simple4tests.interactions.webdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class JavaScript {

    private final WebDriver driver;

    public JavaScript(WebDriver driver) {
        this.driver = driver;
    }

    public void execute(String script, Object... args) {
        ((JavascriptExecutor) driver).executeScript(script, args);
    }
}
