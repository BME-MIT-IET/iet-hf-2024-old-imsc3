Feature: Pipe, Spring, and Pump Operations
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
