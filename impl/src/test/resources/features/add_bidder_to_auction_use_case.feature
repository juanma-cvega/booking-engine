Feature: As a user, I want to bet in an open auction for a slot

  Background:
    Given a club is created
    And a building is created

  Scenario: As a user, I should be able to bet on an auction just opened
    Given a room is to be created
    And the room has a 10 minutes auction time and a 5 days bookings created window
    And current time is 08:00
    And the room is created with that configuration
    And a slot is created
    And an auction is created for the slot
    When user 1 bets on the auction
    Then the auction should contain the user 1 bet created at 08:00

  Scenario: As a user, I should be able to bet on an open auction
    Given a room is to be created
    And the room has a 10 minutes auction time and a 5 days bookings created window
    And current time is 08:00
    And the room is created with that configuration
    And a slot is created
    And an auction is created for the slot
    And current time is 08:05
    When user 1 bets on the auction
    Then the auction should contain the user 1 bet created at 08:05

  Scenario: As a user, I should be able to bet on an open auction alongside other users
    Given a room is to be created
    And the room has a 10 minutes auction time and a 5 days bookings created window
    And current time is 08:00
    And the room is created with that configuration
    And a slot is created
    And an auction is created for the slot
    When user 1 bets on the auction
    And user 2 bets on the auction
    Then the auction should contain the user 1 bet created at 08:00
    Then the auction should contain the user 2 bet created at 08:00

  Scenario: As a user, I shouldn't be able to bet on a close auction
    Given a room is to be created
    And the room has a 10 minutes auction time and a 5 days bookings created window
    And current time is 08:00
    And the room is created with that configuration
    And a slot is created
    And an auction is created for the slot
    And current time is 08:10
    When user 1 bets on the auction
    Then the user should be notified the auction is finished
