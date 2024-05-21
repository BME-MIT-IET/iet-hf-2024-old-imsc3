package steps;

import io.cucumber.java.en.*;
import Controller.Controller;


public class LoginSteps {

    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        // Code to navigate to the login page
        System.out.println("Navigated to the login page");
    }

    @When("the user enters valid credentials")
    public void the_user_enters_valid_credentials() {
        // Code to enter valid credentials
    }

    @Then("the user should be redirected to the dashboard")
    public void the_user_should_be_redirected_to_the_dashboard() {
        // Code to verify the user is redirected
    }

    @When("the user enters invalid credentials")
    public void the_user_enters_invalid_credentials() {
        // Code to enter invalid credentials
    }

    @Then("an error message should be displayed")
    public void an_error_message_should_be_displayed() {
        // Code to verify the error message is displayed
    }
}
