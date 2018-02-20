Feature: As an admin, I want auction finish to be scheduled based on configuration

  Background:
    Given a club is created
    And a building is created
    Given a room is to be created
    And the room has a 10 minutes auction time and a 5 days bookings created window
    And current time is 06:00
    And the room is created with that configuration
    And a slot is created
    And an auction is created for the slot
    And that sets the background

  Scenario: As an admin, I want auction finish to be scheduled based on configuration
    When the auction is scheduled to finish
    And an auction finished event should be scheduled to be published at 06:10
