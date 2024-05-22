package steps;

import io.cucumber.java.en.When;
import Controller.Controller;

public class MechanicSteps {
    Controller controller = Controller.getInstance();

    @When("a mechanic named {string} is created")
    public void a_mechanic_named_is_created(String name) {
        controller.create(name, "mechanic", 0, 0);
    }
}
