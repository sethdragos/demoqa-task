package com.lottoland.interview.steps;

import com.lottoland.interview.common.User;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.apache.commons.lang3.RandomUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.lottoland.interview.common.Constants.REGISTRATION_SUCCESS_MESSAGE;
import static com.lottoland.interview.common.Constants.REGISTRATION_URL;


@RunWith(SerenityRunner.class)
public class RegistrationTest extends BaseTest {

    private static List<User> userList = new ArrayList<>(
            Arrays.asList(
                    new User("Jan", "van Dam"), new User("Chack", "Norris"), new User("Klark", "n Kent"),
                    new User("John", "Daw"), new User("Bat", "Man"), new User("Tim", "Los"),
                    new User("Dave", "o Core"), new User("Pay", "Pal"), new User("Lazy", "Cat"),
                    new User("Jack", "& Johnes")
            )
    );

    @Steps
    private RegistrationSteps registrationSteps;

    private int randomIndex;

    @Before
    public void verifyPageUrl() {

        // 1. Open the page http://demoqa.com/registration/ and validate that the URL is the same as we navigated to. If not - fail the test.
        registrationSteps.open_page(REGISTRATION_URL);
        registrationSteps.verify_page_url_is(REGISTRATION_URL);
    }

    @Test
    public void registrationTest() {

        // 2. Register 5 RANDOM!! users from the list above with the random data except First Name and Second Name. Do not repeat the users.
        for (int i=0; i<5; i++) {
            registrationSteps.open_page(REGISTRATION_URL);

            int randomIndex = RandomUtils.nextInt(0, userList.size());

            User luckyUser = userList.get(randomIndex);
            registerUser(luckyUser.getFirstName(), luckyUser.getLastName(), REGISTRATION_SUCCESS_MESSAGE);

            userList.remove(luckyUser);
        }
    }

    @AfterClass
    public static void printUserList() {

        // 3. Display in the output the given list of the users but WITHOUT already registered users.
        String output = "{";
        for (User unluckyUser : userList) {
            output = output + "\"" + unluckyUser.getFirstName() + " " + unluckyUser.getLastName()+ "\"" + ",";
        }
        if (output.length() > 1) {
            output = output.substring(0, output.length() - 1);
        }
        output += "}";

        System.out.println("Remaining users list: " + output);
    }

    private void registerUser(String firstName, String lastName, String expectedMessage) {

        registrationSteps.select_random_marital_status();
        registrationSteps.select_random_hobby();
        registrationSteps.select_random_country();
        registrationSteps.select_random_date_of_birth();
        registrationSteps.fill_random_phone_number();
        registrationSteps.fill_random_username();
        registrationSteps.fill_random_email();
        registrationSteps.fill_random_description();
        registrationSteps.fill_confirm_random_password();
        registrationSteps.fill_first_and_last_name(firstName, lastName);

        registrationSteps.submit_registration_form(expectedMessage);
    }
}
