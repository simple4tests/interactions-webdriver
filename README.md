# simple4tests

The goal of simple4tests is to provide methodology and simple tools to support developers and testers while building
maintainable and reliable automated tests.

## Package interactions-webdriver

WebDriverInteractions (wdi) is a set of functions you can integrate in your selenium test automation framework to manage
automaticaly several technical stuff like waits, scrolling and much more.

You have less to care about it, keeping the code clean and focused on the test purpose.

## Usage

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
    WebDriverInteractions wdi = new WebDriverInteractions(driver);
```

3. Just use it

```
    wdi.browser.navigateTo(...);
    wdi.click(...);
    wdi.set(...);
    ...
```

## More info

### Documentation

For more details see [WebDriver Interactions documentation](https://simple4tests.github.io/interactions-webdriver/)

### Examples of implementation

For junit examples, checkout [wdi-ex-junit](https://github.com/simple4tests/wdi-ex-junit) on gitHub

For full examples based on cucumber and including the 'webdriver-overload' package,
checkout [wdi-ex-cucumber](https://github.com/simple4tests/wdi-ex-cucumber) on GitHub

## License

```
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
```
