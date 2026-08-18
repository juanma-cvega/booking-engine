Feature: As a user, I want to be able to see one of my bookings

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

  Scenario: As a user, I want to see the booking I created
    When user 1 asks for his booking
    Then user 1 should see the booking he created

  Scenario: As an unauthorized user, I shouldn't be able to see a booking from another user
    When an unauthorized user 2 asks for the booking from user 1
    Then the user 2 should be notified the booking does belong to other user
