package steps;

import io.cucumber.java.en.When;
import Controller.Controller;

public class SaboteurSteps {
    Controller controller = Controller.getInstance();

    @When("a saboteur named {string} is created")
    public void a_saboteur_named_is_created(String name) {
        controller.create(name, "saboteur", 0, 0);
    }

    @When("a saboteur named {string} is created on {string}")
    public void a_saboteur_named_is_created_on(String saboteurName, String locationName) {
        controller.create(saboteurName, "saboteur", 0, 0);
        controller.move(saboteurName, locationName);
    }

    @When("{string} pierces the pipe")
    public void pierces_the_pipe(String playerName) {
        controller.pierce(playerName);
    }

    @When("the saboteur gains a point")
    public void the_saboteur_gains_a_point() {
        controller.getCounter().AddSaboteurPoints(1);
    }
}
