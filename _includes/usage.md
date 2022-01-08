---
---

1. Add dependency on your pom.xml file

    ```
    <dependency>
        <groupId>io.github.simple4tests</groupId>
        <artifactId>interactions-webdriver</artifactId>
        <version>...</version>
    </dependency>
    ```

2. Instanciate a WebDriverInteractions object

    ```
    WebDriver driver;
    ...
    driver = new ChromeDriver();
    ...
    WebDriverInteractions ui = new WebDriverInteractions(driver);
    ```

3. Just use it

    ```
    ui.browser.navigateTo(...);
    ui.click(...);
    ui.set(...);
    ...
    ```
