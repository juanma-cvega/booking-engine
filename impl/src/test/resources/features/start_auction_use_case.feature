Feature: As an admin, I want to see auctions created based on configuration for slots

  Background:
    Given a club is created
    And a building is created

  Scenario: As an admin, I should be able to see an auction created for a slot
    Given a room is to be created
    And the room has a 10 minutes auction time and a 5 days bookings created window
    And the room is open between 08:00 and 10:00
    And current time is 06:00
    And the room is created with that configuration
    And a slot is created
    And that sets the background
    When an auction is created for the slot
    Then the auction should be stored
    And an auction finished event should be scheduled to be published at 06:10

  Scenario: As an admin, I should be able to see a slot from a room without auctions configured does not have an auction attached
    Given a room is to be created
    And the room has a no auctions configuration
    And the room is open between 08:00 and 10:00
    And current time is 06:00
    And the room is created with that configuration
    And a slot is created
    And that sets the background
    When an auction is created for the slot
    Then the auction shouldn't exist
    And an auction finished event shouldn't be scheduled to be published
