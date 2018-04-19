Feature: As an admin, I want to add pre reservations to a slot lifecycle manager of a room

  Background:
    Given a slot lifecycle manager for room 1 is to be created
    And the slot duration is 60 minutes
    And the slots are open
      | MONDAY | TUESDAY | WEDNESDAY |
    And the slots are open from 08:00 to 12:00
    And the slots are open from 16:00 to 20:00
    And the slot lifecycle is created with that configuration

  Scenario: As an admin, I want to add a pre reservation to a slot lifecycle manager of a room
    When a pre reservation for next MONDAY at 09:00 from zone UTC for user 5 is created for room 1
    Then a slot lifecycle manager for room 1 should contain a pre reservation for user 5 next MONDAY at 09:00 from zone UTC

  Scenario: As an admin, I want to see old reservations removed
    And a pre reservation for next MONDAY at 09:00 from zone UTC for user 5 is created for room 1
    And current date time has moved by 14 DAYS
    When a pre reservation for next MONDAY at 09:00 from zone UTC for user 5 is created for room 1
    Then a slot lifecycle manager for room 1 should contain a pre reservation for user 5 next MONDAY at 09:00 from zone UTC

  Scenario: As an admin, I shouldn't be able to create a pre reservation for a not available day
    When a pre reservation for next THURSDAY at 09:00 from zone UTC for user 5 is tried to be created for room 1
    Then the admin should receive a notification the reservation is not valid for next THURSDAY at 09:00 from zone UTC for user 5 for room 1

  Scenario: As an admin, I shouldn't be able to create a pre reservation for a not available open time
    When a pre reservation for next MONDAY at 13:00 from zone UTC for user 5 is tried to be created for room 1
    Then the admin should receive a notification the reservation is not valid for next MONDAY at 13:00 from zone UTC for user 5 for room 1

  Scenario: As an admin, I shouldn't be able to create a pre reservation at the end of an open time for an available day
    When a pre reservation for next MONDAY at 12:00 from zone UTC for user 5 is tried to be created for room 1
    Then the admin should receive a notification the reservation is not valid for next MONDAY at 12:00 from zone UTC for user 5 for room 1

  Scenario: As an admin, I shouldn't be able to create a pre reservation for a not valid slot time
    When a pre reservation for next THURSDAY at 09:30 from zone UTC for user 5 is tried to be created for room 1
    Then the admin should receive a notification the reservation is not valid for next THURSDAY at 09:30 from zone UTC for user 5 for room 1

  Scenario: As an admin, I shouldn't be able to create a pre reservation when there is already another pre reservation for the same date
    And a pre reservation for next MONDAY at 09:00 from zone UTC for user 5 is created for room 1
    When a pre reservation for next MONDAY at 09:00 from zone UTC for user 7 is tried to be created for room 1
    Then the admin should receive a notification the reservation overlaps with another reservation for next MONDAY at 09:00 from zone UTC for user 5 for room 1

  Scenario: As an admin, I shouldn't be able to create a pre reservation when it overlaps with a class already configured
    And class 5 is configured in slot lifecycle manager for room 1 to use
      | dayOfWeek      | MONDAY      | WEDNESDAY   |
      | slotsStartTime | 10:00,11:00 | 16:00,17:00 |
      | zoneId         | UTC         | UTC         |
    When a pre reservation for next MONDAY at 10:00 from zone UTC for user 7 is tried to be created for room 1
    Then the admin should receive a notification the reservation overlaps with another reservation for next MONDAY at 10:00 from zone UTC for user 5 for room 1

  Scenario: As an admin, I shouldn't be able to create a pre reservation when it overlaps with a class already configured with another time zone
    And class 5 is configured in slot lifecycle manager for room 1 to use
      | dayOfWeek      | MONDAY      | WEDNESDAY   |
      | slotsStartTime | 10:00,11:00 | 16:00,17:00 |
      | zoneId         | UTC         | UTC         |
    When a pre reservation for next MONDAY at 11:00 from zone Europe/London for user 7 is tried to be created for room 1
    Then the admin should receive a notification the reservation overlaps with another reservation for next MONDAY at 11:00 from zone Europe/London for user 5 for room 1

