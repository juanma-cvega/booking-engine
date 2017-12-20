Feature: As an admin, I want to see slots created. As a user, I want to be able to see the available slots

  Scenario: As an admin, I want to see the first slot created for a room while before the first open time
    Given a room is to be created
    And the room can open up to 10 slots at the same time
    And the room is open between 08:00 and 12:00
    And the room is open between 14:00 and 16:00
    And the room is open between 18:00 and 20:00
    And the slots time is of 10 minute
    And current time is 06:00
    And the room is created with that configuration
    And that sets the background
    When a slot is created
    Then a notification of a created slot starting at 08:00 and ending at 08:10 should be published
    And the slot should be stored starting at 08:00 and ending at 08:10

  Scenario: As an admin, I want to see the first slot created for a room while within the first open time
    Given a room is to be created
    And the room can open up to 10 slots at the same time
    And the room is open between 08:00 and 12:00
    And the room is open between 14:00 and 16:00
    And the room is open between 18:00 and 20:00
    And the slots time is of 10 minute
    And current time is 08:05
    And the room is created with that configuration
    And that sets the background
    When a slot is created
    Then a notification of a created slot starting at 08:10 and ending at 08:20 should be published
    And the slot should be stored starting at 08:10 and ending at 08:20

  Scenario: As an admin, I want to see the first slot created for a room while at the end of the first open time
    Given a room is to be created
    And the room can open up to 10 slots at the same time
    And the room is open between 08:00 and 12:00
    And the room is open between 14:00 and 16:00
    And the room is open between 18:00 and 20:00
    And the slots time is of 10 minute
    And current time is 11:55
    And the room is created with that configuration
    And that sets the background
    When a slot is created
    Then a notification of a created slot starting at 14:00 and ending at 14:10 should be published
    And the slot should be stored starting at 14:00 and ending at 14:10

  Scenario: As an admin, I want to see the first slot created for a room while after the first open time
    Given a room is to be created
    And the room can open up to 10 slots at the same time
    And the room is open between 08:00 and 12:00
    And the room is open between 14:00 and 16:00
    And the room is open between 18:00 and 20:00
    And the slots time is of 10 minute
    And current time is 13:00
    And the room is created with that configuration
    And that sets the background
    When a slot is created
    Then a notification of a created slot starting at 14:00 and ending at 14:10 should be published
    And the slot should be stored starting at 14:00 and ending at 14:10

  Scenario: As an admin, I want to see the first slot created for a room while during the second open time
    Given a room is to be created
    And the room can open up to 10 slots at the same time
    And the room is open between 08:00 and 12:00
    And the room is open between 14:00 and 16:00
    And the room is open between 18:00 and 20:00
    And the slots time is of 10 minute
    And current time is 15:00
    And the room is created with that configuration
    And that sets the background
    When a slot is created
    Then a notification of a created slot starting at 15:00 and ending at 15:10 should be published
    And the slot should be stored starting at 15:00 and ending at 15:10

  Scenario: As an admin, I want to see the first slot created for a room while at the end of the second open time
    Given a room is to be created
    And the room can open up to 10 slots at the same time
    And the room is open between 08:00 and 12:00
    And the room is open between 14:00 and 16:00
    And the room is open between 18:00 and 20:00
    And the slots time is of 10 minute
    And current time is 15:55
    And the room is created with that configuration
    And that sets the background
    When a slot is created
    Then a notification of a created slot starting at 18:00 and ending at 18:10 should be published
    And the slot should be stored starting at 18:00 and ending at 18:10

  Scenario: As an admin, I want to see the first slot created for a room while during the third open time
    Given a room is to be created
    And the room can open up to 10 slots at the same time
    And the room is open between 08:00 and 12:00
    And the room is open between 14:00 and 16:00
    And the room is open between 18:00 and 20:00
    And the slots time is of 10 minute
    And current time is 18:00
    And the room is created with that configuration
    And that sets the background
    When a slot is created
    Then a notification of a created slot starting at 18:00 and ending at 18:10 should be published
    And the slot should be stored starting at 18:00 and ending at 18:10

  Scenario: As an admin, I want to see the first slot created for a room while within the third open time
    Given a room is to be created
    And the room can open up to 10 slots at the same time
    And the room is open between 08:00 and 12:00
    And the room is open between 14:00 and 16:00
    And the room is open between 18:00 and 20:00
    And the slots time is of 10 minute
    And current time is 19:00
    And the room is created with that configuration
    And that sets the background
    When a slot is created
    Then a notification of a created slot starting at 19:00 and ending at 19:10 should be published
    And the slot should be stored starting at 19:00 and ending at 19:10

  Scenario: As an admin, I want to see the first slot created for a room while at the end of the third open time
    Given a room is to be created
    And the room can open up to 10 slots at the same time
    And the room is open between 08:00 and 12:00
    And the room is open between 14:00 and 16:00
    And the room is open between 18:00 and 20:00
    And the slots time is of 10 minute
    And current time is 19:55
    And the room is created with that configuration
    And that sets the background
    When a slot is created
    Then a notification of a created slot starting the next day at 08:00 and ending at 08:10 should be published
    And the slot should be stored starting the next day at 08:00 and ending at 08:10

  Scenario: As an admin, I want to see the first slot created for a room while after the third open time
    Given a room is to be created
    And the room can open up to 10 slots at the same time
    And the room is open between 08:00 and 12:00
    And the room is open between 14:00 and 16:00
    And the room is open between 18:00 and 20:00
    And the slots time is of 10 minute
    And current time is 21:00
    And the room is created with that configuration
    And that sets the background
    When a slot is created
    Then a notification of a created slot starting the next day at 08:00 and ending at 08:10 should be published
    And the slot should be stored starting the next day at 08:00 and ending at 08:10

  Scenario: As an admin, I want to see a slot created when there are others already while before the first open time
    Given a room is to be created
    And the room can open up to 10 slots at the same time
    And the room is open between 08:00 and 12:00
    And the room is open between 14:00 and 16:00
    And the room is open between 18:00 and 20:00
    And the slots time is of 10 minute
    And current time is 06:00
    And the room is created with that configuration
    And a slot is created
    And that sets the background
    When a slot is created
    Then a notification of a created slot starting at 08:10 and ending at 08:20 should be published
    And the slot should be stored starting at 08:10 and ending at 08:20

  Scenario: As an admin, I want to see a slot created when there are others already while at the end of the first open time
    Given a room is to be created
    And the room can open up to 10 slots at the same time
    And the room is open between 08:00 and 12:00
    And the room is open between 14:00 and 16:00
    And the room is open between 18:00 and 20:00
    And the slots time is of 10 minute
    And current time is 11:45
    And the room is created with that configuration
    And a slot is created
    And that sets the background
    When a slot is created
    Then a notification of a created slot starting at 14:00 and ending at 14:10 should be published
    And the slot should be stored starting at 14:00 and ending at 14:10

  Scenario: As an admin, I want to see a slot created when there are others already while at the end of the second open time
    Given a room is to be created
    And the room can open up to 10 slots at the same time
    And the room is open between 08:00 and 12:00
    And the room is open between 14:00 and 16:00
    And the room is open between 18:00 and 20:00
    And the slots time is of 10 minute
    And current time is 15:45
    And the room is created with that configuration
    And a slot is created
    And that sets the background
    When a slot is created
    Then a notification of a created slot starting at 18:00 and ending at 18:10 should be published
    And the slot should be stored starting at 18:00 and ending at 18:10

  Scenario: As an admin, I want to see a slot created when there are others already while at the end of the third open time
    Given a room is to be created
    And the room can open up to 10 slots at the same time
    And the room is open between 08:00 and 12:00
    And the room is open between 14:00 and 16:00
    And the room is open between 18:00 and 20:00
    And the slots time is of 10 minute
    And current time is 19:45
    And the room is created with that configuration
    And a slot is created
    And that sets the background
    When a slot is created
    Then a notification of a created slot starting the next day at 08:00 and ending at 08:10 should be published
    And the slot should be stored starting the next day at 08:00 and ending at 08:10
