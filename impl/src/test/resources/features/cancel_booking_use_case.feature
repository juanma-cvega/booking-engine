Feature: As a user, I want to be able to cancel bookings

  Scenario: As a user, I want to cancel a booking
    Given a room is created
    And a slot is created
    And the slot is booked by user 1
    When the user 1 cancels his booking
    Then the user 1 should not see that booking in his list

  Scenario: As a user, I shouldn't be able to cancel a booking once it's already started
    Given a room is created
    And a slot is created
    And the slot is booked by user 1
    And the slot start time is passed
    When the user 1 cancels his booking
    Then the slot should be booked by the user 1
    And the user should be notified the booking is already started

  Scenario: As a user, I shouldn't be able to cancel other users' bookings
    Given a room is created
    And a slot is created
    And the slot is booked by user 1
    When the user 2 cancels the booking from user 1
    Then the slot should be booked by the user 1
    And the user should be notified the booking does belong to other user
