package steps;

import io.cucumber.java.en.When;
import Controller.Controller;

public class MechanicSteps {
    Controller controller = Controller.getInstance();

    @When("a mechanic named {string} is created")
    public void a_mechanic_named_is_created(String name) {
        controller.create(name, "mechanic", 0, 0);
    }

    @When("{string} repairs {string}")
    public void mechanic_repairs_pipe(String mechanicName, String pipeName) {
        controller.repair(mechanicName);
    }

}
