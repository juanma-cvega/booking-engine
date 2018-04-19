Feature: As a user, I want to cancel reservation for a slot. As an admin, I want to see a slot available after
  its auction finished without any bidder

  Background:
    Given a club is created by user 4
    And the club created is managed by the authorization manager
    And a building is created
    And a room is created
    And a slot is created

  Scenario: As a user, I want to cancel my reservation for a slot
    And user 1 is the member 6 of the club created
    And the slot is made available
    And the slot is reserved by user 1
    And that sets the background
    When the slot is made available
    Then the slot should be available
    And a notification of a slot made available should be published

  Scenario: As an admin, I want to see the slot available after an auction has finished without a bidder
    And that sets the background
    When the slot is made available
    Then the slot should be available
    And a notification of a slot made available should be published


