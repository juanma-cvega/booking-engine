Feature: As an admin, I want to remove a class configuration from a slot lifecycle manager

  Background:
    Given a slot lifecycle manager 1 is to be created
    And the slot duration is 60 minutes
    And the slots are open
      | MONDAY | TUESDAY | WEDNESDAY |
    And the slots are open from 08:00 to 12:00
    And the slots are open from 16:00 to 20:00
    And the slot lifecycle is created with that configuration

  Scenario: As an admin, I want to remove a class configuration from a slot lifecycle manager
    When class 5 is configured in room 1 to use
      | dayOfWeek      | MONDAY      | WEDNESDAY |
      | slotsStartTime | 10:00,11:00 | 16:00     |
      | zoneId         | UTC         | UTC       |
    When the class 5 is removed from room 1
    Then a slot lifecycle manager 1 should not contain a configuration for class 5

  Scenario: As an admin, I should not be able to remove a class configuration from a non existing slot lifecycle manager
    When class 5 is tried to be configured in room 5 to use
      | dayOfWeek      | MONDAY      | WEDNESDAY |
      | slotsStartTime | 10:00,11:00 | 16:00     |
      | zoneId         | UTC         | UTC       |
    Then the admin should receive a notification the slot lifecycle manager 5 does not exist

  Scenario: As an admin, I should not be able to remove a non existing class configuration
    When the class 5 is tried to be removed from room 1
    Then the admin should receive a notification the class 5 for slot lifecycle manager 1 does not exist
