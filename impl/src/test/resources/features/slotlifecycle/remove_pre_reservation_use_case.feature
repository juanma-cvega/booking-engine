Feature: As an admin, I want to remove a pre reservation from a slot lifecycle manager of a room

  Background:
    Given a slot lifecycle manager for room 1 is to be created
    And the slot duration is 60 minutes
    And the slots are open
      | MONDAY | TUESDAY | WEDNESDAY |
    And the slots are open from 08:00 to 12:00
    And the slots are open from 16:00 to 20:00
    And the slot lifecycle is created with that configuration

  Scenario: As an admin, I want to remove a pre reservation from a slot lifecycle manager of a room
    And a pre reservation for next MONDAY at 09:00 from zone UTC for user 5 is created for room 1
    When the pre reservation for next MONDAY at 09:00 from zone UTC is removed from room 1
    Then a slot lifecycle manager for room 1 should not contain a pre reservation for user 5 next MONDAY at 09:00 from zone UTC

  Scenario: As an admin, I should not be able to remove a pre reservation from a non existing slot lifecycle manager
    When the pre reservation for next MONDAY at 09:00 from zone UTC is tried to be removed from room 5
    Then the admin should receive a notification the slot lifecycle manager for room 5 does not exist

  Scenario: As an admin, I should not be able to remove a non existing pre reservation
    When the pre reservation for next MONDAY at 09:00 from zone UTC is tried to be removed removed from room 1
    Then the admin should receive a notification the pre reservation for next MONDAY at 09:00 from zone UTC for slot lifecycle manager for room 1 does not exist
