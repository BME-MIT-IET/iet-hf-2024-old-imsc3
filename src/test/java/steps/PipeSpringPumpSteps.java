package steps;

import Model.Player;
import Model.Steppable;
import io.cucumber.java.en.*;
import Controller.Controller;
import Model.Pipe;
import static org.junit.Assert.*;

public class PipeSpringPumpSteps {
    Controller controller = Controller.getInstance();

    @When("a pipe named {string} is created")
    public void a_pipe_named_is_created(String name) {
        controller.create(name, "pipe", 0, 0);
    }

    @When("a cistern named {string} is created")
    public void a_cistern_named_is_created(String name) {
        controller.create(name, "cistern", 0, 0);
    }

    @When("a pump named {string} is created")
    public void a_pump_named_is_created(String name) {
        controller.create(name, "pump", 0, 0);
    }

    @When("a spring named {string} is created")
    public void a_spring_named_is_created(String name) {
        controller.create(name, "spring", 0, 0);
    }

    @When("{string} is connected to {string}")
    public void is_connected_to(String pipeName, String nodeName) {
        controller.connect(pipeName, nodeName);
    }

    @When("{string} moves to {string}")
    public void moves_to(String playerName, String locationName) {
        controller.move(playerName, locationName);
    }

    @When("{string} picks up {string}")
    public void picks_up(String playerName, String objectName) {
        controller.pickup(playerName, objectName);
        System.out.println(controller.getLastMessage());
    }

    @When("water flows from {string}")
    public void water_flows_from(String nodeName) {
        controller.waterFlow(nodeName);
    }

    @When("{string} places down {string}")
    public void places_down(String playerName, String objectName) {
        controller.placedown(playerName);
    }

    @When("the state of {string} is set to {string} with value {string}")
    public void the_state_of_is_set_to_with_value(String objectName, String state, String value) {
        controller.stateSet(objectName, state, value);
    }

    @Then("{string} should be in the game")
    public void should_be_in_the_game(String name) {
        Object obj = controller.getObjectCatalog().get(name);
        assertNotNull(obj);
    }

    @Then("{string} should be connected to {string}")
    public void should_be_connected_to(String pipeName, String nodeName) {
        Pipe pipe = (Pipe) controller.getObjectCatalog().get(pipeName);
        Object node = controller.getObjectCatalog().get(nodeName);
        assertTrue(pipe.getNodes().contains(node));
    }

    @Then("{string} should be connected to {string} and {string}")
    public void should_be_connected_to_and(String pipeName, String nodeName1, String nodeName2) {
        Pipe pipe = (Pipe) controller.getObjectCatalog().get(pipeName);
        Object node1 = controller.getObjectCatalog().get(nodeName1);
        Object node2 = controller.getObjectCatalog().get(nodeName2);
        assertTrue(pipe.getNodes().contains(node1));
        assertTrue(pipe.getNodes().contains(node2));
    }

    @Then("{string} should be marked as broken")
    public void should_be_marked_as_broken(String pipeName) {
        Pipe pipe = (Pipe) controller.getObjectCatalog().get(pipeName);
        assertTrue(pipe.isBroken());
    }

    @Then("{string} should have {int} water held")
    public void should_have_water_held(String pipeName, int waterAmount) {
        Pipe pipe = (Pipe) controller.getObjectCatalog().get(pipeName);
        assertEquals(waterAmount, pipe.getHeldWater());
    }

    @Then("the saboteur's points should be {int}")
    public void the_saboteur_s_points_should_be(int points) {
        Object counter_saboteur = controller.stateGet("counter", "saboteurPoints", true);
        assertEquals("counter.saboteurPoints = " + points, counter_saboteur.toString());
    }

    @Then("{string} should be marked as glued")
    public void should_be_marked_as_glued(String pipeName) {
        Pipe pipe = (Pipe) controller.getObjectCatalog().get(pipeName);
        assertTrue(pipe.isGlued());
    }

    @Then("{string} should be on {string}")
    public void should_be_on(String playerName, String locationName) {
        Player player = (Player) controller.getObjectCatalog().get(playerName);
        Steppable location = (Steppable) controller.getObjectCatalog().get(locationName);
        assertEquals(location, player.getStandingOn());
    }

    @Then("the message {string} should be shown")
    public void the_message_should_be_shown(String expectedMessage) {
        String actualMessage = controller.getLastMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @When("{string} is broken")
    public void pipe_is_broken(String arg0) {
        controller.stateSet(arg0, "broken", "true");
        assertEquals("pipe1.broken = true", controller.getLastMessage());
    }

    @When("{string} gains {int} water")
    public void pipe_gains_water(String pipeName, int waterAmount) {
        controller.stateSet(pipeName, "heldWater", Integer.toString(waterAmount));
    }

    @Then("{string} should be marked as being held")
    public void should_be_marked_as_being_held(String pipeName) {
        Pipe pipe = (Pipe) controller.getObjectCatalog().get(pipeName);
        assertTrue(pipe.isBeingHeld());
    }

    @Then("{string} should be marked as not broken")
    public void pipe_should_be_marked_as_not_broken(String pipeName) {
        Pipe pipe = (Pipe) controller.getObjectCatalog().get(pipeName);
        assertFalse(pipe.isBroken());
    }

    @Then("{string} should be marked as not being held")
    public void should_be_marked_as_not_being_held(String pipeName) {
        Pipe pipe = (Pipe) controller.getObjectCatalog().get(pipeName);
        assertFalse(pipe.isBeingHeld());
    }
}
