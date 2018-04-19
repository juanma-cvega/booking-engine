Feature: As an admin, I want to replace the slots timetable of a slot life cycle manager

  Background:
    Given a slot lifecycle manager for room 1 is to be created
    And the slot duration is 30 minutes
    And the slots are open
      | THURSDAY |
    And the slots are open from 09:00 to 10:00
    And the slots are open from 17:00 to 18:00
    And the slot lifecycle is created with that configuration

  Scenario: As an admin, I want to modify the slots timetable of a slot life cycle manager
    And the slots timetable is about to be replaced
    And the new slot duration is 60 minutes
    And the new slots are open
      | MONDAY | TUESDAY | WEDNESDAY |
    And the new slots are open from 08:00 to 12:00
    And the new slots are open from 16:00 to 20:00
    When the slot lifecycle 1 gets replaced its slots timetable with that configuration
    Then the slot lifecycle manager for room 1 should contain the new slots timetable

  Scenario: As an admin, I should be able to replace the slots timetable when there are pre reservations covered by the new configuration
    And a pre reservation for next THURSDAY at 09:00 from zone UTC for user 5 is created for room 1
    And the slots timetable is about to be replaced
    And the new slot duration is 60 minutes
    And the new slots are open
      | MONDAY | TUESDAY | WEDNESDAY | THURSDAY |
    And the new slots are open from 08:00 to 12:00
    And the new slots are open from 16:00 to 20:00
    When the slot lifecycle 1 is tried to be replaced its slots timetable with that configuration
    Then the slot lifecycle manager for room 1 should contain the new slots timetable

  Scenario: As an admin, I should be able to replace the slots timetable when there are pre reservations created from a different time zone covered by the new configuration
    Given current date time is 2018-04-10 20:00
    And a pre reservation for next THURSDAY at 10:00 from zone Europe/London for user 5 is created for room 1
    And the slots timetable is about to be replaced
    And the new slot duration is 60 minutes
    And the new slots are open
      | MONDAY | TUESDAY | WEDNESDAY | THURSDAY |
    And the new slots are open from 08:00 to 12:00
    And the new slots are open from 16:00 to 20:00
    When the slot lifecycle 1 is tried to be replaced its slots timetable with that configuration
    Then the slot lifecycle manager for room 1 should contain the new slots timetable

  Scenario: As an admin, I shouldn't be able to replace the slots timetable when there are pre reservations not covered by the days open in the new configuration
    And a pre reservation for next THURSDAY at 09:00 from zone UTC for user 5 is created for room 1
    And the slots timetable is about to be replaced
    And the new slot duration is 60 minutes
    And the new slots are open
      | MONDAY | TUESDAY | WEDNESDAY |
    And the new slots are open from 08:00 to 12:00
    And the new slots are open from 16:00 to 20:00
    When the slot lifecycle 1 is tried to be replaced its slots timetable with that configuration
    Then the admin should be notified the new slots timetable for room 1 cannot be used

  Scenario: As an admin, I shouldn't be able to replace the slots timetable when there are pre reservations not covered by the hours available in the new configuration
    And a pre reservation for next THURSDAY at 09:00 from zone UTC for user 5 is created for room 1
    And the slots timetable is about to be replaced
    And the new slot duration is 60 minutes
    And the new slots are open
      | MONDAY | TUESDAY | WEDNESDAY | THURSDAY
    And the new slots are open from 16:00 to 20:00
    When the slot lifecycle 1 is tried to be replaced its slots timetable with that configuration
    Then the admin should be notified the new slots timetable for room 1 cannot be used

  Scenario: As an admin, I should be able to replace the slots timetable when there are classes covered by the new configuration
    When class 5 is configured in slot lifecycle manager for room 1 to use
      | dayOfWeek      | THURSDAY |
      | slotsStartTime | 09:00    |
      | zoneId         | UTC      |
    And the slots timetable is about to be replaced
    And the new slot duration is 60 minutes
    And the new slots are open
      | MONDAY | TUESDAY | WEDNESDAY | THURSDAY |
    And the new slots are open from 08:00 to 12:00
    And the new slots are open from 16:00 to 20:00
    When the slot lifecycle 1 is tried to be replaced its slots timetable with that configuration
    Then the slot lifecycle manager for room 1 should contain the new slots timetable

  Scenario: As an admin, I should be able to replace the slots timetable when there are classes covered by the new configuration from a different zone
    Given current date time is 2018-04-10 20:00
    When class 5 is configured in slot lifecycle manager for room 1 to use
      | dayOfWeek      | THURSDAY      |
      | slotsStartTime | 10:00         |
      | zoneId         | Europe/London |
    And the slots timetable is about to be replaced
    And the new slot duration is 60 minutes
    And the new slots are open
      | MONDAY | TUESDAY | WEDNESDAY | THURSDAY |
    And the new slots are open from 08:00 to 12:00
    And the new slots are open from 16:00 to 20:00
    When the slot lifecycle 1 is tried to be replaced its slots timetable with that configuration
    Then the slot lifecycle manager for room 1 should contain the new slots timetable

  Scenario: As an admin, I shouldn't be able to replace the slots timetable when there are classes not covered by the days open in the new configuration
    When class 5 is configured in slot lifecycle manager for room 1 to use
      | dayOfWeek      | THURSDAY |
      | slotsStartTime | 09:00    |
      | zoneId         | UTC      |
    And the slots timetable is about to be replaced
    And the new slot duration is 60 minutes
    And the new slots are open
      | MONDAY | TUESDAY | WEDNESDAY |
    And the new slots are open from 08:00 to 12:00
    And the new slots are open from 16:00 to 20:00
    When the slot lifecycle 1 is tried to be replaced its slots timetable with that configuration
    Then the admin should be notified the new slots timetable for room 1 cannot be used

  Scenario: As an admin, I shouldn't be able to replace the slots timetable when there are classes not covered by the hours open in the new configuration
    When class 5 is configured in slot lifecycle manager for room 1 to use
      | dayOfWeek      | THURSDAY |
      | slotsStartTime | 09:00    |
      | zoneId         | UTC      |
    And the slots timetable is about to be replaced
    And the new slot duration is 60 minutes
    And the new slots are open
      | MONDAY | TUESDAY | WEDNESDAY | THURSDAY |
    And the new slots are open from 16:00 to 20:00
    When the slot lifecycle 1 is tried to be replaced its slots timetable with that configuration
    Then the admin should be notified the new slots timetable for room 1 cannot be used
