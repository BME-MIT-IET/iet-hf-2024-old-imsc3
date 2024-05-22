package steps;

import io.cucumber.java.en.*;
import Controller.Controller;
import Model.Pipe;
import Model.Pump;
import Model.Spring;
import Model.Saboteur;
import static org.junit.Assert.*;

public class PipeSpringPumpSteps {
    Controller controller;

    @Given("the program is running")
    public void the_program_is_running() {
        controller = Controller.getInstance();
    }

    @When("a pipe named {string} is created")
    public void a_pipe_named_is_created(String name) {
        controller.create(name, "pipe", 0, 0);
    }

    @Then("{string} should be in the game")
    public void should_be_in_the_game(String name) {
        Object obj = controller.getObjectCatalog().get(name);
        assertNotNull(obj);
    }

    @When("a spring named {string} is created")
    public void a_spring_named_is_created(String name) {
        controller.create(name, "spring", 0, 0);
    }

    @When("a pump named {string} is created")
    public void a_pump_named_is_created(String name) {
        controller.create(name, "pump", 0, 0);
    }

    @When("a saboteur named {string} is created")
    public void a_saboteur_named_is_created(String name) {
        controller.create(name, "saboteur", 0, 0);
    }

    @When("{string} is connected to {string}")
    public void is_connected_to(String pipeName, String nodeName) {
        controller.connect(pipeName, nodeName);
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

    @When("{string} is broken")
    public void is_broken(String pipeName) {
        controller.stateSet(pipeName, "broken", "true");
    }

    @Then("{string} should be marked as broken")
    public void should_be_marked_as_broken(String pipeName) {
        Pipe pipe = (Pipe) controller.getObjectCatalog().get(pipeName);
        assertTrue(pipe.isBroken());
    }

    @When("{string} gains {int} water")
    public void gains_water(String pipeName, int waterAmount) {
        controller.stateSet(pipeName, "heldWater", Integer.toString(waterAmount));
    }

    @Then("{string} should have {int} water held")
    public void should_have_water_held(String pipeName, int waterAmount) {
        Pipe pipe = (Pipe) controller.getObjectCatalog().get(pipeName);
        assertEquals(waterAmount, pipe.getHeldWater());
    }

    @When("the saboteur gains a point")
    public void the_saboteur_gains_a_point() {
        controller.getCounter().AddSaboteurPoints(1);
    }

    @Then("the saboteur's points should be {int}")
    public void the_saboteur_s_points_should_be(int points) {
        Object counter_saboteur = controller.stateGet("counter", "saboteurPoints", true);
        assertEquals("counter.saboteurPoints = " + points, counter_saboteur.toString());
    }

    @When("the state of {string} is set to {string} with value {string}")
    public void the_state_of_is_set_to_with_value(String objectName, String state, String value) {
        controller.stateSet(objectName, state, value);
    }

    @Then("{string} should be marked as glued")
    public void should_be_marked_as_glued(String pipeName) {
        Pipe pipe = (Pipe) controller.getObjectCatalog().get(pipeName);
        assertTrue(pipe.isGlued());
    }

    @When("{string} moves to {string}")
    public void moves_to(String saboteurName, String locationName) {
        controller.move(saboteurName, locationName);
    }

    @When("a saboteur named {string} is created on {string}")
    public void a_saboteur_named_is_created_on(String saboteurName, String locationName) {
        controller.create(saboteurName, "saboteur", 0, 0);
        controller.move(saboteurName, locationName);
    }

    @Then("{string} should be on {string}")
    public void should_be_on(String saboteurName, String locationName) {
        Saboteur saboteur = (Saboteur) controller.getObjectCatalog().get(saboteurName);
        Object location = controller.getObjectCatalog().get(locationName);
        assertEquals(location, saboteur.getLocation());
    }

    @Then("the message {string} should be shown")
    public void the_message_should_be_shown(String expectedMessage) {
        String actualMessage = controller.getLastMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}
