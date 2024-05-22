Feature: Pump Operations
  Scenario: Creating and connecting multiple pipes and pumps
    Given the program is running
    When a pipe named "pipe1" is created
    Then "pipe1" should be in the game
    When a pipe named "pipe2" is created
    Then "pipe2" should be in the game
    When a pump named "pump1" is created
    Then "pump1" should be in the game
    When a pump named "pump2" is created
    Then "pump2" should be in the game
    When a pump named "pump3" is created
    Then "pump3" should be in the game
    When "pipe1" is connected to "pump1"
    Then "pipe1" should be connected to "pump1"
    When "pipe1" is connected to "pump2"
    Then "pipe1" should be connected to "pump1" and "pump2"
    When "pipe2" is connected to "pump2"
    Then "pipe2" should be connected to "pump2"
    When "pipe2" is connected to "pump3"
    Then "pipe2" should be connected to "pump2" and "pump3"
    When "pipe1" gains 1 water
    Then "pipe1" should have 1 water held
    When "pump2" gains 1 water
    Then "pump2" should have 1 water held
    When the state of "pump2" is set to "activeIn" with value "pipe1"
    Then "pipe1" should be connected to "pump2"
    When the state of "pump2" is set to "activeOut" with value "pipe2"
    Then "pipe2" should be connected to "pump2"
    When the state of "pump2" is set to "broken" with value "true"
    Then "pump2" should be marked as broken
    When water flows from "pump2"
    Then "pump2" should have 2 water held



  Scenario: Mechanic redirects pump
    Given the program is running
    When a pipe named "pipe3" is created
    Then "pipe3" should be in the game
    When a pipe named "pipe4" is created
    Then "pipe4" should be in the game
    When a pump named "pump5" is created
    Then "pump5" should be in the game
    When a mechanic named "mechanic1" is created
    Then "mechanic1" should be in the game
    When "pipe3" is connected to "pump5"
    Then "pipe3" should be connected to "pump5"
    When "pipe4" is connected to "pump5"
    Then "pipe4" should be connected to "pump5"
    When the state of "pump5" is set to "activeIn" with value "pipe4"
    Then "pump5"'s activeIn should be "pipe4"
    When the state of "pump5" is set to "activeOut" with value "pipe3"
    And "pump5"'s activeOut should be "pipe3"
    When "mechanic1" moves to "pump5"
    Then "mechanic1" should be on "pump5"
    When "mechanic1" redirects from "pipe3" to "pipe4"
    Then "pump5"'s activeIn should be "pipe4"
    And "pump5"'s activeOut should be "pipe3"


