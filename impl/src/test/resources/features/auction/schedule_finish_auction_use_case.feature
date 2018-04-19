Feature: As an admin, I want auction finish to be scheduled based on configuration

  Background:
    Given a club is created
    And the club created is managed by the authorization manager
    And a building is created
    And a room is created
    And current time is 06:00
    And a slot is created

  Scenario: As an admin, I want auction finish to be scheduled based on configuration
    Given an auction is created for the slot with a 10 minutes auction time and a 5 days bookings created window
    When the auction is scheduled to finish
    And an auction finished event should be scheduled to be published at 06:10
