Feature: As a user, I want to know the result of a auction I bet for

  Background:
    Given a club is created
    And a building is created

  Scenario: As a user, I shouldn't win an auction if there is someone with a better criteria when the auction is configured
  to make the winner the person with less bookings within a period of time
    Given a room is created
    And a slot is created
    And the slot is booked by user 1
    And a room is to be created
    And the room has a 10 minutes auction time and a 5 days bookings created window
    And the slots time is of 30 minute
    And current time is 08:00
    And the room is created with that configuration
    And a slot is created
    And an auction is created for the slot
    And current time is 08:05
    And user 1 bets on the auction
    And user 2 bets on the auction
    And that sets the background
    When the auction time is finished at 08:10
    Then a notification containing user 2 as the winner should be published

  Scenario: As a user, I should win an auction to another user when we have the same criteria but I bid first
    Given a room is to be created
    And the room has a 10 minutes auction time and a 5 days bookings created window
    And the slots time is of 30 minute
    And current time is 08:00
    And the room is created with that configuration
    And a slot is created
    And current time is 08:05
    And a slot is created
    And an auction is created for the slot
    And user 1 bets on the auction
    And user 2 bets on the auction
    And that sets the background
    When the auction time is finished at 08:10
    Then a notification containing user 1 as the winner should be published

  Scenario: As a user, I should see no one winning an auction when nobody bets on it
    Given a room is to be created
    And the room has a 10 minutes auction time and a 5 days bookings created window
    And the slots time is of 30 minute
    And current time is 08:00
    And the room is created with that configuration
    And a slot is created
    And current time is 08:05
    And a slot is created
    And an auction is created for the slot
    And that sets the background
    When the auction time is finished at 08:10
    Then a notification containing the winner shouldn't be published
