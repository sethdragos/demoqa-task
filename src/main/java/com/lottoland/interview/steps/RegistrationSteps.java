package com.lottoland.interview.steps;

import com.lottoland.interview.pages.RegistrationPage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;

public class RegistrationSteps extends ScenarioSteps {

    private RegistrationPage registrationPage;

    @Step("Open the registration page: {0}")
    public void open_page(String url) {
        registrationPage.open(url);
    }

    @Step("Verify the page url is: {0}")
    public void verify_page_url_is(String expectedUrl) {
        Assert.assertEquals("Current page URL is NOT as expected", expectedUrl, getDriver().getCurrentUrl());
    }

    @Step("Fill the first name '{0}' and last name '{1}'")
    public void fill_first_and_last_name(String firstName, String lastName) {
        registrationPage.fillFirstName(firstName);
        registrationPage.fillLastName(lastName);
    }

    @Step("Select random marital status")
    public void select_random_marital_status() {
        registrationPage.selectMaritalStatus(RandomUtils.nextInt(0, 3));
    }

    @Step("Select random hobby")
    public void select_random_hobby() {
        registrationPage.selectHobby(RandomUtils.nextInt(0, 3));
    }

    @Step("Select random country")
    public void select_random_country() {
        registrationPage.selectRandomCountry();
    }

    @Step("Select random date of birth")
    public void select_random_date_of_birth() {
        registrationPage.selectRandomDateOfBirth();
    }

    @Step("Fill random phone number")
    public void fill_random_phone_number() {
        registrationPage.fillPhoneNumber("00" + RandomStringUtils.randomNumeric(10));
    }

    @Step("Fill random username")
    public void fill_random_username() {
        registrationPage.fillUserName(RandomStringUtils.randomAlphabetic(30));
    }

    @Step("Fill random email")
    public void fill_random_email() {
        StringBuilder sb = new StringBuilder(35);
        sb.append(RandomStringUtils.randomAlphabetic(1))
                .append(RandomStringUtils.randomAlphanumeric(20))
                .append("@")
                .append(RandomStringUtils.randomAlphanumeric(9))
                .append(".")
                .append(RandomStringUtils.randomAlphabetic(3));

        registrationPage.fillEmail(sb.toString());
    }

    @Step("Fill random description")
    public void fill_random_description() {
        registrationPage.fillDescription(RandomStringUtils.randomAlphanumeric(100));
    }

    @Step("Fill and confirm random password")
    public void fill_confirm_random_password() {
        String randomPassword = RandomStringUtils.randomAlphanumeric(30);
        registrationPage.fillPassword(randomPassword);
        registrationPage.fillConfirmPassword(randomPassword);
    }

    @Step("Submit registration form expecting: {0}")
    public void submit_registration_form(String expectedMessage) {
        registrationPage.submitForm();
        Assert.assertEquals("Registration message is NOT as expected",
                expectedMessage, registrationPage.getRegistrationMessage());
    }

}