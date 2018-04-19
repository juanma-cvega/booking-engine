Feature: As a user, I want to know the result of an auction I bid for

  Background:
    Given a club is created
    And the club created is managed by the authorization manager
    And a building is created
    And a room is created
    And a slot is created

  Scenario: As a user, I shouldn't win an auction if there is someone with a better criteria when the auction is configured
  to make the winner the person with less bookings within a period of time
    Given an auction is created for the slot with a 10 minutes auction time and a 5 days bookings created window
    And current time is 08:05
    And user 1 is the member 5 of the club created
    And member 5 can bid in auctions
    And user 47 is the member 23 of the club created
    And member 23 can bid in auctions
    And user 1 bids on the auction
    And user 47 bids on the auction
    And that sets the background
    When the auction time is finished
    Then a notification containing user 47 as the winner should be published

  Scenario: As a user, I should win an auction to another user when we have the same criteria but I bid first
    Given an auction is created for the slot with a 10 minutes auction time and a 5 days bookings created window
    And user 62 is the member 4 of the club created
    And member 4 can bid in auctions
    And user 11 is the member 5 of the club created
    And member 5 can bid in auctions
    And current time is 08:05
    And user 62 bids on the auction
    And current time is 08:06
    And user 11 bids on the auction
    And that sets the background
    When the auction time is finished
    Then a notification containing user 62 as the winner should be published

  Scenario: As a user, I should see no one winning an auction when nobody bids on it
    Given an auction is created for the slot with a 10 minutes auction time and a 5 days bookings created window
    And that sets the background
    When the auction time is finished
    Then a notification saying the slot can be made available should be published
