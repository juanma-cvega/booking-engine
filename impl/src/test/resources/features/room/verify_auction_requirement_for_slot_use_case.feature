Feature: As an admin, I want the system to decide whether a slot needs an auction created or it could be made available

  Background:
    Given a club is created by user 4
    And a building is created
    And user 1 issues a join request
    And admin 4 accepts the join request created by user 1
    And the accepted join request for user 1 is processed

  Scenario: As an admin, I want the system to publish a notification of an auction needed when the room is configured
  to use auctions
    Given a room is to be created
    And the room has a 10 minutes auction time and a 5 days bookings created window
    And the room is created with that configuration
    And a slot is created
    And that sets the background
    When the room is asked whether the slot needs an auction
    Then an event of an auction required should be published

  Scenario: As an admin, I want the system to make the slot available when the room is not configured to use auctions
    Given a room is to be created
    And the room has a no auctions configuration
    And the room is created with that configuration
    And a slot is created
    And that sets the background
    When the room is asked whether the slot needs an auction
    Then an event of the slot being ready should be published
