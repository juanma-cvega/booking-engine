Feature: As an admin, I want slots recently created to transition to their next state based on room configuration

  Background:
    Given a slot lifecycle manager for room 1 is to be created
    And the slot duration is 60 minutes
    And the slots are open
      | MONDAY | TUESDAY | WEDNESDAY |
    And the slots are open from 08:00 to 12:00
    And the slots are open from 16:00 to 20:00
    And the slot lifecycle is created with that configuration

  Scenario: As an admin, I want a slot to be available for reservation when there are no configured reservations or auctions
    When slot 7 from room 1 started the 2018-01-10 at 20:00 has just been created
    Then a notification that the slot 7 is available should be published

  Scenario: As an admin, I want a slot to be in auction when there is an auction configured and no reservations
    Given an auction configuration of 5 minutes duration and bookings period of 2 days is added to slot lifecycle manager for room 1
    When slot 7 from room 1 started the 2018-01-10 at 20:00 has just been created
    Then a notification that the slot 7 requires an auction of 5 minutes duration and bookings period of 2 days should be published

  Scenario: As an admin, I want a slot to be pre reserved when there is a pre reservation configured even when auctions are configured
    Given an auction configuration of 5 minutes duration and bookings period of 2 days is added to slot lifecycle manager for room 1
    And a pre reservation for next MONDAY at 09:00 from zone UTC for user 5 is created for room 1
    When slot 7 from room 1 starts the next MONDAY at 09:00 from zone UTC has just been created
    Then a notification that the slot 7 is pre reserved for user 5 should be published

  Scenario: As an admin, I want a slot to be reserved for a class when there is a class configured even when auctions are configured
    Given an auction configuration of 5 minutes duration and bookings period of 2 days is added to slot lifecycle manager for room 1
    When class 11 is configured in slot lifecycle manager for room 1 to use
      | dayOfWeek      | MONDAY      | WEDNESDAY   |
      | slotsStartTime | 10:00,11:00 | 16:00,17:00 |
      | zoneId         | UTC         | UTC         |
    When slot 7 from room 1 starts the next MONDAY at 10:00 from zone UTC has just been created
    Then a notification that the slot 7 is reserved for class 11 should be published
