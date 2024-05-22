package steps;

import Controller.Controller;
import io.cucumber.java.en.Given;

public class CommonSteps {
    Controller controller;

    @Given("the program is running")
    public void the_program_is_running() {
        controller = Controller.getInstance();
    }
}
