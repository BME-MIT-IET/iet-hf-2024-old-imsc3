Feature: Mechanic Operations

  Scenario: Mechanic picks up a pipe
    Given the program is running
    When a pipe named "pipe1" is created
    And a pump named "pump1" is created
    And a mechanic named "mechanic1" is created
    And "pipe1" is connected to "pump1"
    And "mechanic1" moves to "pipe1"
    And "mechanic1" picks up "pipe1"
    Then "pipe1" should be marked as being held

  Scenario: Mechanic repairs a broken pipe
    Given the program is running
    When a pipe named "pipe1" is created
    And a pump named "pump1" is created
    And a mechanic named "mechanic1" is created
    And "pipe1" is connected to "pump1"
    And the state of "pipe1" is set to "broken" with value "true"
    And "mechanic1" moves to "pipe1"
    And "mechanic1" repairs "pipe1"
    Then "pipe1" should be marked as not broken

  Scenario: Mechanic places down a pipe
    Given the program is running
    When a pipe named "pipe1" is created
    And a pump named "pump1" is created
    And a mechanic named "mechanic1" is created
    And "pipe1" is connected to "pump1"
    And "mechanic1" moves to "pipe1"
    And "mechanic1" picks up "pipe1"
    And "mechanic1" moves to "pump1"
    And "mechanic1" places down "pipe1"
    Then "pipe1" should be marked as not being held
