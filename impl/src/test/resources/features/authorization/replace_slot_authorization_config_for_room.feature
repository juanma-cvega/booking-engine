Feature: As an admin, I want to create o replace the slot authorization configuration for a room

  Scenario: As an admin, I shouldn't be able to create the slot authorization config for a room for a club that does not exist
    When club 1 is tried to be added the slot authorization configuration of 2 DAYS for room 2 in building 3
    Then the admin should get a notification the club 1 does not exist

  Scenario: As an admin, I want to create the slot authorization configuration for a room
    Given club 1 is added to the list of clubs to manage its authorization
    When club 1 is added the slot authorization configuration of 2 DAYS for room 2 in building 3
    Then club 1 should have building 3 added to its list of buildings
    And club 1 should have room 2 in building 3 added to its list of buildings
    And room 2 in building 3 of club 1 should have a slot authorization configuration of 2 DAYS

  Scenario: As an admin, I want to replace the slot authorization configuration for a room
    Given club 1 is added to the list of clubs to manage its authorization
    And club 1 is added the slot authorization configuration of 2 DAYS for room 2 in building 3
    When club 1 is added the slot authorization configuration of 4 WEEKS for room 2 in building 3
    Then club 1 should have building 3 added to its list of buildings
    And club 1 should have room 2 in building 3 added to its list of buildings
    And room 2 in building 3 of club 1 should have a slot authorization configuration of 4 WEEKS
