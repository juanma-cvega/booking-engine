Feature: As an admin, I want to see slots scheduled to be created based on a creation strategy

  Scenario: As an admin, I want to see a slot scheduled to be created immediately when the room can have more open slots
    Given a room is to be created
    And the room can open up to 10 slots at the same time
    And the room is open between 08:00 and 12:00
    And the slots time is of 10 minute
    And current time is 15:45
    And the room is created with that configuration
    And a slot is created
    And that sets the background
    When a slot is scheduled
    Then a command to open the next slot for the room should be published immediately

  Scenario: As an admin, I want to see a slot scheduled to be created after the next slot is ended when the room has all slots created
    Given a room is to be created
    And the room can open up to 2 slots at the same time
    And the room is open between 08:00 and 12:00
    And the slots time is of 10 minute
    And current time is 08:00
    And the room is created with that configuration
    And 2 slots are created
    And that sets the background
    When a slot is scheduled
    Then a command to open the next slot for the room should be scheduled to be published at 08:10

  Scenario: As an admin, I want to see a slot scheduled to be created immediately if all slots are expired
    Given a room is to be created
    And the room can open up to 2 slots at the same time
    And the room is open between 08:00 and 12:00
    And the slots time is of 10 minute
    And current time is 08:00
    And the room is created with that configuration
    And 2 slots are created
    And current time is 09:00
    And that sets the background
    When a slot is scheduled
    Then a command to open the next slot for the room should be published immediately
