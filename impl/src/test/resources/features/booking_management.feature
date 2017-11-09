Feature: As a user, I should be able to manage bookings

  Scenario: As a user, I want to book an available slot
    Given a room is created
    And a slot from the room is selected
    When the slot is booked
    Then the slot should be booked by the user

  Scenario: As a user, I want to see all my bookings
    Given a room is created
    And all slots from the room are booked by the same user
    When the user asks for his bookings
    Then the user should see all slots booked by him

  Scenario: As a user, I cannot book a slot already booked
    Given a room is created
    And a slot from the room is selected
    And the slot is booked
    When a new user books the slot
    Then the user should get a notification that the slot is already booked

  Scenario: As a user, I want to cancel a booking
    Given a room is created
    And a slot from the room is selected
    And the slot is booked
    When the user cancels the booking
    Then the user should not see that booking in his list

  Scenario: As a user, I shouldn't be able to cancel a booking once it's already started
    Given a room is created
    And a slot from the room is selected
    And the slot is booked
    And the slot start time is passed
    When the user cancels the booking
    Then the slot should be booked by the user
    And the user should be notified the booking is already started

  Scenario: As a user, I shouldn't be able to cancel other users' bookings
    Given a room is created
    And a slot from the room is selected
    And the slot is booked
    When a different user cancels the booking
    Then the slot should be booked by the user
    And the user should be notified the booking does belong to other user
