Feature: As a user, I want to cancel reservation for a slot. As an admin, I want to see a slot available after
  its auction finished without any bidder

  Background:
    Given a club is created by user 4
    And a building is created
    And user 1 issues a join request
    And admin 4 accepts the join request created by user 1
    And the accepted join request for user 1 is processed

  Scenario: As a user, I want to cancel my reservation for a slot
    Given a room is to be created
    And the room has a no auctions configuration
    And the room is created with that configuration
    And a slot is created
    And the slot is reserved by user 1
    And that sets the background
    When the slot is made available
    Then the slot should be available
    And a notification of a slot made available should be published

  Scenario: As an admin, I want to see the slot available after an auction has finished without a bidder
    Given a room is to be created
    And the room has a 5 minutes auction time and a 5 days bookings created window
    And the room is created with that configuration
    And a slot is created
    And that sets the background
    When the slot is made available
    Then the slot should be available
    And a notification of a slot made available should be published


