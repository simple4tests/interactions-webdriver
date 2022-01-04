# simple4tests
The goal of simple4tests is to provide methodology and simple tools to support developers and testers while building maintainable and reliable automated tests.

## Package interactions-webdriver

Interactions-webdriver is a set of functions you can integrate in your selenium test automation framework.
Interactions-webdriver manages automaticaly several technical stuff like waits, scrolling and much more.

You have less to care about it, keeping the code clean and focused on the test purpose. 

Inside the box:
- WebDriverInteractions.* : DOM interactions
- WebDriverInteractions.browser.* : Browser interactions like url navigation, switching and closing tabs, scrolling, ...
- WebDriverInteractions.javaScript.* : Execute javascript
- WebDriverInteractions.wait.* : Wait until a condition comes true, or not, and continue

## Quick guide
1. Add dependency on your pom.xml file

```markdown
    <dependency>
        <groupId>io.github.simple4tests</groupId>
        <artifactId>interactions-webdriver</artifactId>
        <version>...</version>
    </dependency>
```

3. Instanciate a WebDriverInteractions object

```markdown
    WebDriver driver;
    ...
    driver = new ChromeDriver();
    ...
    WebDriverInteractions ui = new WebDriverInteractions(driver);
```

4. Just use it

```markdown
    ui.browser.navigateTo(...);
    ui.click(...);
    ui.set(...);
    ...
```

## More info

### Documentation
For more details see [wdi](https://simple4tests.github.io/interactions-webdriver/) pages

### Examples of implementation
#### wdi-ex-junit
#### wdi-ex-cucumber
