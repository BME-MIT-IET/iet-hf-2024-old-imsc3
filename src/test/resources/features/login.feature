Feature: User Exit

  Scenario: User ends the game successfully
    Given the game has started
    When the player ends the game
    Then the game should be over