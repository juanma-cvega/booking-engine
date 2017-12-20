Feature: As an admin, I want to see slots scheduled to be created based on a creation strategy

  Scenario: As an admin, I want to see a slot scheduled to be created immediately when the room can have more open slots
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
    When a slot is scheduled
    Then a notification of a scheduled slot creation to be executed immediately should be published

  Scenario: As an admin, I want to see a slot scheduled to be created after the next slot is ended when the room has all slots created
    Given a room is to be created
    And the room can open up to 2 slots at the same time
    And the room is open between 08:00 and 12:00
    And the room is open between 14:00 and 16:00
    And the room is open between 18:00 and 20:00
    And the slots time is of 10 minute
    And current time is 08:00
    And the room is created with that configuration
    And 2 slots are created
    And that sets the background
    When a slot is scheduled
    Then a notification of a scheduled slot creation to be executed at 08:10 should be published
