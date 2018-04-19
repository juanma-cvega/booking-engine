Feature: As a user, I want to bid in an open auction for a slot

  Background:
    Given a club is created
    And the club created is managed by the authorization manager
    And a building is created
    And a room is created
    And a slot is created

  Scenario: As a user, I should be able to bid on an auction just opened
    Given current time is 08:00
    And user 4 is the member 4 of the club created
    And member 4 can bid in auctions
    And an auction is created for the slot with a 10 minutes auction time and a 5 days bookings created window
    When user 4 bids on the auction
    Then the auction should contain the user 4 bid created at 08:00

  Scenario: As a user, I should be able to bid on an open auction
    Given current time is 08:00
    And user 8 is the member 4 of the club created
    And member 4 can bid in auctions
    And an auction is created for the slot with a 10 minutes auction time and a 5 days bookings created window
    And current time is 08:05
    When user 8 bids on the auction
    Then the auction should contain the user 8 bid created at 08:05

  Scenario: As a user, I should be able to bid on an open auction alongside other users
    Given current time is 08:00
    And user 6 is the member 6 of the club created
    And member 6 can bid in auctions
    And user 15 is the member 15 of the club created
    And member 15 can bid in auctions
    And an auction is created for the slot with a 10 minutes auction time and a 5 days bookings created window
    When user 6 bids on the auction
    And user 15 bids on the auction
    Then the auction should contain the user 6 bid created at 08:00
    Then the auction should contain the user 15 bid created at 08:00


  Scenario: As a user, I should be able to bid when I've given access to auctions again
    Given current time is 08:00
    And user 56 is the member 7 of the club created
    And an auction is created for the slot with a 10 minutes auction time and a 5 days bookings created window
    And member 7 can bid in auctions
    And member 7 is not authorized to bid in auctions for the room
    And member 7 can bid in auctions
    And a slot is created
    When user 56 bids on the auction
    Then the auction should contain the user 56 bid created at 08:00


  Scenario: As a user, I shouldn't be able to bid on a close auction
    Given current time is 08:00
    And user 8 is the member 2 of the club created
    And member 2 can bid in auctions
    And an auction is created for the slot with a 10 minutes auction time and a 5 days bookings created window
    And current time is 08:10
    When user 8 tries to bid on the auction
    Then the user should be notified the auction is finished

  Scenario: As a user, I shouldn't be able to bid if I don't have access to auction for the room
    Given current time is 08:00
    And user 56 is the member 2 of the club created
    And member 2 is not authorized to bid in auctions for the room
    Given an auction is created for the slot with a 10 minutes auction time and a 5 days bookings created window
    When user 56 tries to bid on the auction
    Then the user 56 should be notified he is not authorized to bid in auctions in the room created
