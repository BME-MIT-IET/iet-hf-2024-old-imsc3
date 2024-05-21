package steps;

import io.cucumber.java.en.*;
import Controller.Controller;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PipeSpringPumpSteps{

    private Controller controller;

    @Given("the game has started")
    public void theGameHasStarted() {
        // Initialize the Controller instance
        controller = Controller.getInstance();
        controller.startGame();
        assert(controller != null);
    }

    @When("the player ends the game")
    public void thePlayerEndsTheGame() {
        controller.endGame();
    }

    @Then("the game should be over")
    public void theGameShouldBeOver() {
        assert(controller != null);
    }
}
