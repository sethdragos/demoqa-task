package com.lottoland.interview.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage extends BasePage {

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "[name=first_name]")
    private WebElement firstNameInput;

    @FindBy(css = "[name=last_name]")
    private WebElement lastNameInput;

    @FindBy(css = "[value=single]")
    private WebElement singleCheckBox;

    @FindBy(css = "[value=married]")
    private WebElement marriedCheckBox;

    @FindBy(css = "[value=divorced]")
    private WebElement divorcedCheckBox;

    @FindBy(css = "[value=dance]")
    private WebElement danceCheckBox;

    @FindBy(css = "[value=reading]")
    private WebElement readingCheckBox;

    @FindBy(css = "[value~=cricket]")
    private WebElement cricketCheckBox;

    @FindBy(id = "phone_9")
    private WebElement phoneInput;

    @FindBy(css = "[name=username]")
    private WebElement usernameInput;

    @FindBy(css = "[name=e_mail]")
    private WebElement emailInput;

    @FindBy(css = "[name=description]")
    private WebElement descriptionInput;

    @FindBy(css = "[name=password]")
    private WebElement passwordInput;

    @FindBy(id = "confirm_password_password_2")
    private WebElement confirmPasswordInput;

    @FindBy(css = "[type=submit]")
    private WebElement submitButton;

    @FindBy(css = ".piereg_message")
    private WebElement message;

    public Select countrySelect, monthSelect, daySelect, yearSelect;

    private static final By COUNTRY_SELECTOR = new By.ById("dropdown_7");
    private static final By MONTH_SELECTOR = new By.ById("mm_date_8");
    private static final By DAY_SELECTOR = new By.ById("dd_date_8");
    private static final By YEAR_SELECTOR = new By.ById("yy_date_8");


    public void fillFirstName(String firstName) {
        fluentWaitForElementVisible(firstNameInput);
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
    }

    public void fillLastName(String lastName) {
        fluentWaitForElementVisible(lastNameInput);
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
    }

    public void selectMaritalStatus(int number) {
        switch (number) {
            case 0: {
                fluentWaitForElementClickable(singleCheckBox);
                singleCheckBox.click();
                break;
            }
            case 1: {
                fluentWaitForElementClickable(marriedCheckBox);
                marriedCheckBox.click();
                break;
            }
            case 2: {
                fluentWaitForElementClickable(divorcedCheckBox);
                divorcedCheckBox.click();
                break;
            }
        }
    }

    public void selectHobby(int number) {
        switch (number) {
            case 0: {
                fluentWaitForElementClickable(danceCheckBox);
                danceCheckBox.click();
                break;
            }
            case 1: {
                fluentWaitForElementClickable(readingCheckBox);
                readingCheckBox.click();
                break;
            }
            case 2: {
                fluentWaitForElementClickable(cricketCheckBox);
                cricketCheckBox.click();
                break;
            }
        }
    }

    public void selectRandomCountry() {
        countrySelect = new Select(getDriver().findElement(COUNTRY_SELECTOR));
        countrySelect.selectByIndex(RandomUtils.nextInt(0, countrySelect.getOptions().size()));
    }

    public void selectRandomDateOfBirth() {
        monthSelect = new Select(getDriver().findElement(MONTH_SELECTOR));
        daySelect = new Select(getDriver().findElement(DAY_SELECTOR));
        yearSelect = new Select(getDriver().findElement(YEAR_SELECTOR));
        monthSelect.selectByIndex(RandomUtils.nextInt(1, monthSelect.getOptions().size() - 1));
        daySelect.selectByIndex(RandomUtils.nextInt(1, daySelect.getOptions().size() - 1));
        yearSelect.selectByIndex(RandomUtils.nextInt(1, yearSelect.getOptions().size() - 1));
    }

    public void fillPhoneNumber(String phone) {
        fluentWaitForElementVisible(phoneInput);
        firstNameInput.clear();
        phoneInput.sendKeys(phone);
    }

    public void fillUserName(String username) {
        fluentWaitForElementVisible(usernameInput);
        firstNameInput.clear();
        usernameInput.sendKeys(username);
    }

    public void fillEmail(String email) {
        fluentWaitForElementVisible(emailInput);
        firstNameInput.clear();
        emailInput.sendKeys(email);
    }

    public void fillDescription(String description) {
        fluentWaitForElementVisible(descriptionInput);
        firstNameInput.clear();
        descriptionInput.sendKeys(description);
    }

    public void fillPassword(String password) {
        fluentWaitForElementVisible(passwordInput);
        firstNameInput.clear();
        passwordInput.sendKeys(password);
    }

    public void fillConfirmPassword(String confirmPassword) {
        fluentWaitForElementVisible(confirmPasswordInput);
        firstNameInput.clear();
        confirmPasswordInput.sendKeys(confirmPassword);
    }

    public void submitForm() {
        fluentWaitForElementClickable(submitButton);
        submitButton.click();
    }

    public String getRegistrationMessage() {
        fluentWaitForElementVisible(message);
        return message.getText();
    }
}
