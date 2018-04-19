Feature: As an admin, I want to see auctions created based on configuration for slots

  Background:
    Given a club is created
    And the club created is managed by the authorization manager
    And a building is created
    And a room is created
    And a slot is created
    And that sets the background

  Scenario: As an admin, I should be able to see an auction created for a slot
    When an auction is created for the slot with a 10 minutes auction time and a 5 days bookings created window
    Then the auction should be stored
    And an auction started event should be published
