Feature: As a user, I want to bid in an open auction for a slot

  Background:
    Given a club is created
    And the club created is managed by the authorization manager
    And a building is created

  Scenario: As a user, I should be able to bid on an auction just opened
    Given a room is to be created
    And the room has a 10 minutes auction time and a 5 days bookings created window
    And current time is 08:00
    And the room is created with that configuration
    And user 4 is the member 4 of the club created
    And member 4 can bid in auctions
    And a slot is created
    And an auction is created for the slot
    When user 4 bids on the auction
    Then the auction should contain the user 4 bid created at 08:00

  Scenario: As a user, I should be able to bid on an open auction
    Given a room is to be created
    And the room has a 10 minutes auction time and a 5 days bookings created window
    And current time is 08:00
    And the room is created with that configuration
    And user 8 is the member 2 of the club created
    And member 2 can bid in auctions
    And a slot is created
    And an auction is created for the slot
    And current time is 08:05
    When user 8 bids on the auction
    Then the auction should contain the user 8 bid created at 08:05

  Scenario: As a user, I should be able to bid on an open auction alongside other users
    Given a room is to be created
    And the room has a 10 minutes auction time and a 5 days bookings created window
    And current time is 08:00
    And the room is created with that configuration
    And user 6 is the member 14 of the club created
    And member 14 can bid in auctions
    And user 15 is the member 7 of the club created
    And member 7 can bid in auctions
    And a slot is created
    And an auction is created for the slot
    When user 6 bids on the auction
    And user 15 bids on the auction
    Then the auction should contain the user 6 bid created at 08:00
    Then the auction should contain the user 15 bid created at 08:00


  Scenario: As a user, I should be able to bid when I've given access to auctions again
    Given a room is to be created
    And the room has a 10 minutes auction time and a 5 days bookings created window
    And current time is 08:00
    And the room is created with that configuration
    And user 56 is the member 7 of the club created
    And member 7 can bid in auctions
    And member 7 is not authorized to bid in auctions for the room
    And member 7 can bid in auctions
    And a slot is created
    And an auction is created for the slot
    When user 56 bids on the auction
    Then the auction should contain the user 56 bid created at 08:00


  Scenario: As a user, I shouldn't be able to bid on a close auction
    Given a room is to be created
    And the room has a 10 minutes auction time and a 5 days bookings created window
    And current time is 08:00
    And the room is created with that configuration
    And user 8 is the member 2 of the club created
    And member 2 can bid in auctions
    And a slot is created
    And an auction is created for the slot
    And current time is 08:10
    When user 8 tries to bid on the auction
    Then the user should be notified the auction is finished

  Scenario: As a user, I shouldn't be able to bid if I don't have access to auction for the room
    Given a room is to be created
    And the room has a 10 minutes auction time and a 5 days bookings created window
    And current time is 08:00
    And the room is created with that configuration
    And user 56 is the member 7 of the club created
    And member 7 can bid in auctions
    And member 7 is not authorized to bid in auctions for the room
    And a slot is created
    And an auction is created for the slot
    When user 56 tries to bid on the auction
    Then the user 56 should be notified he is not authorized to bid in auctions in the room created
