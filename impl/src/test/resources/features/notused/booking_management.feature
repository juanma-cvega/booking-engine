#Feature: As a user, I should be able to manage bookings
#
#  Scenario: As a user, I want to book an available slot
#    Given a room is created
#    And a slot from the room is selected
#    When the slot is booked by user 1
#    Then the slot should be booked by the user 1
#
#  Scenario: As a user, I want to see all my bookings
#    Given a room is created
#    And a slot from the room is selected
#    And the slot is booked by user 1
#    When the user 1 asks for his bookings
#    Then the user should see all slots booked by him
#
#  Scenario: As a user, I cannot book a slot already booked
#    Given a room is created
#    And a slot from the room is selected
#    And the slot is booked by user 1
#    When the slot is booked by user 2
#    Then the user should get a notification that the slot is already booked
#
#  Scenario: As a user, I want to cancel a booking
#    Given a room is created
#    And a slot from the room is selected
#    And the slot is booked by user 1
#    When the user 1 cancels his booking
#    Then the user 1 should not see that booking in his list
#
#  Scenario: As a user, I shouldn't be able to cancel a booking once it's already started
#    Given a room is created
#    And a slot from the room is selected
#    And the slot is booked by user 1
#    And the slot start time is passed
#    When the user 1 cancels his booking
#    Then the slot should be booked by the user 1
#    And the user should be notified the booking is already started
#
#  Scenario: As a user, I shouldn't be able to cancel other users' bookings
#    Given a room is created
#    And a slot from the room is selected
#    And the slot is booked by user 1
#    When the user 2 cancels the booking from user 1
#    Then the slot should be booked by the user 1
#    And the user should be notified the booking does belong to other user
#
#  Scenario: As a user, I should be able to bid for a slot while is open for auction and win it
#    Given a room is to be created
#    And the room has a 10 minutes auction time and a 5 days bookings created window
#    And the room is open between 08:00 and 12:00
#    And the room can open up to 10 slots at the same time
#    And the slots time is of 30 minute
#    And current time is 08:00
#    And the room is created with that configuration
#    And a slot from the room is selected
#    And current time is 08:05
#    When the user 1 enters the auction
#    And the auction time is finished at 08:10
#    Then the slot should be booked by the user 1
#
#  Scenario: As a user, I shouldn't be able to book a slot while is open for auction
#    Given a room is to be created
#    And the room has a 10 minutes auction time and a 5 days bookings created window
#    And the room is open between 08:00 and 12:00
#    And the room can open up to 10 slots at the same time
#    And the slots time is of 30 minute
#    And current time is 08:00
#    And the room is created with that configuration
#    And current time is 08:05
#    And a slot from the room is selected
#    When the slot is booked by user 1
#    Then the user should be notified the slot does not exist still in auction
#    And the slot shouldn't be booked by the user 1
#
#  Scenario: As a user, I shouldn't win an auction if there is someone with a better criteria when the auction is configured
#  to make the winner the person with less bookings within a period of time
#    Given a room is created
#    And a slot from the room is selected
#    And the slot is booked by user 1
#    And a room is to be created
#    And the room has a 10 minutes auction time and a 5 days bookings created window
#    And the slots time is of 30 minute
#    And current time is 08:00
#    And the room is created with that configuration
#    And a slot from the room is selected
#    And current time is 08:05
#    And the user 1 enters the auction
#    And the user 2 enters the auction
#    When the auction time is finished at 08:10
#    Then the slot should be booked by the user 2
#
#  Scenario: As a user, I should win an auction to another user when we have the same criteria but I bid first
#    Given a room is to be created
#    And the room has a 10 minutes auction time and a 5 days bookings created window
#    And the slots time is of 30 minute
#    And current time is 08:00
#    And the room is created with that configuration
#    And a slot from the room is selected
#    And current time is 08:05
#    And the user 1 enters the auction
#    And the user 2 enters the auction
#    When the auction time is finished at 08:10
#    Then the slot should be booked by the user 1
