Feature: As a user, I want to reserve a slot

  Background:
    Given a club is created by user 4
    And the club created is managed by the authorization manager
    And a building is created
    And a room is created
    And a slot is created

  Scenario: As a user, I want to reserve an available slot
    Given user 1 is the member 4 of the club created
    And the slot is made available
    And that sets the background
    When the slot is reserved by user 1
    Then the slot should be reserved
    And a notification of a slot reserved by user 1 should be published

  Scenario: As a user, I want to reserve a slot after it's made available
    Given current time is 06:00
    And user 1 is the member 4 of the club created
    And the slot is made available
    And that sets the background
    When the slot is reserved by user 1
    Then the slot should be reserved
    And a notification of a slot reserved by user 1 should be published

  Scenario: As a user, I cannot reserve a slot already reserved
    Given user 1 is the member 4 of the club created
    And user 7 is the member 8 of the club created
    And the slot is made available
    And the slot is reserved by user 1
    When the user 7 tries to reserve the slot
    Then the user should get a notification that the slot is already reserved

  Scenario: As a user, I cannot reserve a slot already started
    Given a room is to be created
    And the room is open between 08:00 and 12:00
    And current time is 06:00
    And the room is created with that configuration
    And user 1 is the member 4 of the club created
    And a slot is created
    And the slot is made available
    And current time is 08:20
    And that sets the background
    When the user 1 tries to reserve the slot
    Then the user should get a notification that the slot is already started

  Scenario: As a user, I shouldn't be able to reserved a slot while is still not available
    Given user 1 is the member 4 of the club created
    When the user 1 tries to reserve the slot
    Then the user should be notified the slot is not available

  Scenario: As a user, I cannot reserve a slot if I'm not a member of the club
    Given the slot is made available
    When the user 1 tries to reserve the slot
    Then the user 1 should get a notification that he is not a member of the club

  Scenario: As a user, I cannot reserve a slot if I'm not authorized
    Given user 1 is the member 4 of the club created
    And the room created requires authorization to use it
    And the slot is made available
    When the user 1 tries to reserve the slot
    Then the user 1 should receive a notification he is not authorized to use the room created
