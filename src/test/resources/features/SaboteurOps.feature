Feature: Saboteur Operations

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
    Then "pipe1" should be marked as broken

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
    Then the message "pipe1 is already broken" should be shown

  Scenario: Saboteur gets stuck on glued pipe
    Given the program is running
    When a pipe named "pipe1" is created
    And a pump named "pump1" is created
    And a pump named "pump2" is created
    And a saboteur named "saboteur1" is created on "pipe1"
    And "pipe1" is connected to "pump1"
    And "pipe1" is connected to "pump2"
    And the state of "pipe1" is set to "glued" with value "true"
    And "saboteur1" moves to "pump1"
    And "saboteur1" moves to "pipe1"
    And "saboteur1" moves to "pump2"
    Then the message "saboteur1 can't move to pump2" should be shown
