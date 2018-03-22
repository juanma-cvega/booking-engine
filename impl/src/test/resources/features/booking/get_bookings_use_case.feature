Feature: As a user, I want to be able to see my bookings

  Background:
    Given a club is created by user 4
    And a building is created
    And user 1 issues a join request
    And admin 4 accepts the join request created by user 1
    And the accepted join request for user 1 is processed
    And a room is created
    And a slot is created
    And the slot is made available
    And there is a booking created by user 1

  Scenario: As a user, I want to see the booking I just created
    When user 1 asks for his bookings
    Then user 1 should see 1 bookings

  Scenario: As a user, I shouldn't be able to see a canceled booking
    Given the user 1 cancels his booking
    When user 1 asks for his bookings
    Then user 1 should see 0 bookings
