package com.lottoland.interview.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public abstract class BasePage extends PageObject {

    WebDriver driver;

    public BasePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void open(String url) {
        driver.manage().window().maximize();
        driver.navigate().to(url);
    }

    public void fluentWaitForElementVisible(WebElement element) {

        new FluentWait<>(driver)
                .withTimeout(getImplicitWaitTimeout().in(SECONDS), SECONDS)
                .pollingEvery(250, MILLISECONDS)
                .ignoring(NoSuchElementException.class, TimeoutException.class)
                .ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(element));
    }

    public void fluentWaitForElementClickable(WebElement element) {

        new FluentWait<>(driver)
                .withTimeout(getImplicitWaitTimeout().in(SECONDS), SECONDS)
                .pollingEvery(250, MILLISECONDS)
                .ignoring(NoSuchElementException.class, TimeoutException.class)
                .ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(element));
    }

}