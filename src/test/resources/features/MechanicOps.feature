Feature: Mechanic Operations

  Scenario: Mechanic picks up a pipe
    Given the program is running
    When a pipe named "pipe1" is created
    And a pump named "pump1" is created
    And "pipe1" is connected to "pump1"
    And a mechanic named "mechanic1" is created
    And "mechanic1" moves to "pump1"
    And "mechanic1" picks up "pipe1"
    Then "pipe1" should be marked as being held

  Scenario: Mechanic repairs a broken pipe
    Given the program is running
    When a pipe named "pipe1" is created
    And a spring named "spring1" is created
    And a pump named "pump1" is created
    And a mechanic named "mechanic1" is created
    And "pipe1" is connected to "spring1"
    And "pipe1" is connected to "pump1"
    And "mechanic1" moves to "pipe1"
    And "mechanic1" pierces the pipe
    And "mechanic1" repairs "pipe1"
    And "mechanic1" pierces the pipe
    Then "pipe1" should be marked as not broken



  Scenario: Mechanic places down a pipe
    Given the program is running
    When a pipe named "pipe1" is created
    And a pipe named "pipe2" is created
    And a pump named "pump1" is created
    And a pump named "pump2" is created
    And a cistern named "cistern1" is created
    And a mechanic named "mechanic1" is created
    And "pipe1" is connected to "pump1"
    And "pipe1" is connected to "pump2"
    And "pipe2" is connected to "pump2"
    And "pipe2" is connected to "cistern1"
    And "mechanic1" moves to "pump2"
    And "mechanic1" picks up "pipe1"
    And "mechanic1" moves to "pipe2"
    And "mechanic1" moves to "cistern1"
    And "mechanic1" places down "pipe1"
    Then "pipe1" should be marked as not being held
