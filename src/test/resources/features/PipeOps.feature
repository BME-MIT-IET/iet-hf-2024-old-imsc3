Feature: Pipe Operations
  Scenario: Creating and manipulating pipe, spring, and pump
    Given the program is running
    When a pipe named "pipe1" is created
    Then "pipe1" should be in the game
    When a spring named "spring1" is created
    Then "spring1" should be in the game
    When a pump named "pump1" is created
    Then "pump1" should be in the game
    When "pipe1" is connected to "spring1"
    Then "pipe1" should be connected to "spring1"
    When "pipe1" is connected to "pump1"
    Then "pipe1" should be connected to "spring1" and "pump1"
    When "pipe1" is broken
    Then "pipe1" should be marked as broken
    When "pipe1" gains 0 water
    Then "pipe1" should have 0 water held
    When the saboteur gains a point
    Then the saboteur's points should be 1

  Scenario: Connecting pipe to cistern and pump
    Given the program is running
    When a pipe named "pipe1" is created
    And a cistern named "cistern1" is created
    And a pump named "pump1" is created
    And a mechanic named "mechanic1" is created
    And "pipe1" is connected to "cistern1"
    And "pipe1" is connected to "pump1"
    And "mechanic1" moves to "pipe1"
    And "mechanic1" moves to "cistern1"

  Scenario: Saboteur pierces a pipe
    Given the program is running
    When a pipe named "pipe1" is created
    And a spring named "spring1" is created
    And a pump named "pump1" is created
    And a saboteur named "saboteur1" is created
    And "pipe1" is connected to "spring1"
    And "pipe1" is connected to "pump1"
    And "saboteur1" moves to "pipe1"
    And "saboteur1" pierces the pipe

  Scenario: Mechanic picks up pipe and water flows
    Given the program is running
    When a pipe named "pipe1" is created
    And a spring named "spring1" is created
    And a pump named "pump1" is created
    And a mechanic named "mechanic1" is created
    And "pipe1" is connected to "spring1"
    And "pipe1" is connected to "pump1"
    And "mechanic1" moves to "pump1"
    And "mechanic1" picks up "pipe1"
    And water flows from "spring1"

  Scenario: Saboteur pierces an already broken pipe
    Given the program is running
    When a pipe named "pipe1" is created
    And a spring named "spring1" is created
    And a pump named "pump1" is created
    And a saboteur named "saboteur1" is created
    And "pipe1" is connected to "spring1"
    And "pipe1" is connected to "pump1"
    And "saboteur1" moves to "pipe1"
    And the state of "pipe1" is set to "broken" with value "true"
    And "saboteur1" pierces the pipe

  Scenario: Mechanic connects full pump
    Given the program is running
    When a pipe named "pipe1" is created
    And a pipe named "pipe2" is created
    And a pump named "pump1" is created
    And a pump named "pump2" is created
    And a pump named "pump3" is created
    And a mechanic named "mechanic1" is created
    And "pipe1" is connected to "pump1"
    And "pipe1" is connected to "pump2"
    And "pipe2" is connected to "pump2"
    And "pipe2" is connected to "pump3"
    And the state of "pump3" is set to "maximumPipes" with value "1"
    And "mechanic1" moves to "pump2"
    And "mechanic1" picks up "pipe1"
    And "mechanic1" moves to "pipe2"
    And "mechanic1" moves to "pump3"
    And "mechanic1" places down "pipe1"

