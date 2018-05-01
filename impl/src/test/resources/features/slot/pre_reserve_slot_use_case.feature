Feature: As an admin, I want to pre reserve slots

  Background:
    Given a club is created by user 4
    And the club created is managed by the authorization manager
    And a building is created
    And a room is created
    And a slot is created

  Scenario: As an admin, I want to pre reserve a slot for a person
    Given current time is 06:00
    And that sets the background
    When the slot is pre reserved for user 1
    Then the slot should be pre reserved
    And a notification of a slot pre reserved by user 1 should be published

  Scenario: As a user, I want to reserve a slot after it has been made available
    Given current time is 06:00
    And user 1 is the member 4 of the club created
    And the slot is made available
    And that sets the background
    When the slot is reserved by user 1
    Then the slot should be reserved
    And a notification of a slot reserved by user 1 should be published

  Scenario: As a user, I cannot pre reserve a slot already reserved
    Given user 1 is the member 6 of the club created
    And user 2 is the member 12 of the club created
    And a slot is created
    And the slot is made available
    And the slot is reserved by user 1
    When the user 2 tries to pre reserve the slot
    Then the user should get a notification that the slot is already reserved

  Scenario: As a user, I cannot pre reserve a slot already started
    Given a room is to be created
    And the room is open between 08:00 and 12:00
    And the slots time is of 30 minute
    And current time is 06:00
    And the room is created with that configuration
    And user 1 is the member 4 of the club created
    And a slot is created
    And the slot is made available
    And current time is 08:20
    And that sets the background
    When the user 1 tries to pre reserve the slot
    Then the user should get a notification that the slot is already started
