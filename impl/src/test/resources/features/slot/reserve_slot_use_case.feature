Feature: As a user, I want to reserve a slot

  Background:
    Given a club is created by user 4
    And a building is created
    And user 1 issues a join request
    And admin 4 accepts the join request created by user 1
    And the accepted join request for user 1 is processed

  Scenario: As a user, I want to reserve an available slot
    Given a room is created
    And a slot is created
    And that sets the background
    When the slot is reserved by user 1
    Then the slot should be reserved
    And a notification of a slot reserved by user 1 should be published

  Scenario: As a user, I want to reserve a slot available after its auction finished without a winner
    Given a room is to be created
    And the room has a 10 minutes auction time and a 5 days bookings created window
    And current time is 06:00
    And the room is created with that configuration
    And a slot is created
    And the slot is made available
    And that sets the background
    When the slot is reserved by user 1
    Then the slot should be reserved
    And a notification of a slot reserved by user 1 should be published

  Scenario: As a user, I cannot reserve a slot already reserved
    Given user 2 issues a join request
    And admin 4 accepts the join request created by user 2
    And the accepted join request for user 2 is processed
    And a room is created
    And a slot is created
    And the slot is reserved by user 1
    When the user 2 tries to reserve the slot
    Then the user should get a notification that the slot is already reserved

  Scenario: As a user, I cannot reserve a slot already started
    Given a room is to be created
    And the room has a no auctions configuration
    And the room is open between 08:00 and 12:00
    And the room can open up to 10 slots at the same time
    And the slots time is of 30 minute
    And current time is 06:00
    And the room is created with that configuration
    And a slot is created
    And current time is 08:20
    And that sets the background
    When the user 1 tries to reserve the slot
    Then the user should get a notification that the slot is already started

  Scenario: As a user, I shouldn't be able to reserved a slot while is open for auction
    Given a room is to be created
    And the room has a 10 minutes auction time and a 5 days bookings created window
    And the room is open between 08:00 and 12:00
    And the room can open up to 10 slots at the same time
    And the slots time is of 30 minute
    And current time is 06:00
    And the room is created with that configuration
    And a slot is created
    When the user 1 tries to reserve the slot
    Then the user should be notified the slot is still in auction

  Scenario: As a user, I cannot reserve a slot if I'm not a member of the club
    Given a room is created
    And a slot is created
    When the user 2 tries to reserve the slot
    Then the user 2 should get a notification that he is not a member of the club
