Feature: As an admin, I want a slot to wait for the result of an auction after it has been created

  Background:
    Given a club is created by user 4
    And a building is created

  Scenario: As an admin, I want a slot to wait for the result of an auction
    Given a room is created
    And a slot is created
    And that sets the background
    When the slot is made wait for the result of an auction
    Then the slot should wait for the result of the auction
    And a notification the slot is waiting for the result of the auction should be published
