Feature: As a user, I want to book a slot

  Background:
    Given a club is created by user 4
    And a building is created
    And user 1 issues a join request
    And admin 4 accepts the join request created by user 1
    And the accepted join request for user 1 is processed

  Scenario: As a user, I want to book an available slot
    Given a room is created
    And a slot is created
    And that sets the background
    When there is a booking created by user 1
    Then the slot should be booked by the user 1
    And a notification of a created booking should be published
