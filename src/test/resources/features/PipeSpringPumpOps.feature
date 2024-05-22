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

  Scenario: Saboteur gets stuck on glued pipe
    Given the program is running
    When a pipe named "pipe1" is created
    Then "pipe1" should be in the game

    When a pump named "pump1" is created
    Then "pump1" should be in the game

    When a pump named "pump2" is created
    Then "pump2" should be in the game

    When "pipe1" is connected to "pump1"
    Then "pipe1" should be connected to "pump1"

    When "pipe1" is connected to "pump2"
    Then "pipe1" should be connected to "pump1" and "pump2"

    When the state of "pipe1" is set to "glued" with value "true"
    Then "pipe1" should be marked as glued

    When a saboteur named "saboteur1" is created on "pipe1"
    Then "saboteur1" should be on "pipe1"

    When "saboteur1" moves to "pump2"
    Then the message "saboteur1 can't move to pump2" should be shown

