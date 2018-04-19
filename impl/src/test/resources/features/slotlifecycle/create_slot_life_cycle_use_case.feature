Feature: As an admin, I want to create a slot lifecycle manager of a room to control which states the slots transition to

  Scenario: As an admin, I want a slot lifecycle manager of a room to be created
    When a slot lifecycle manager for room 1 is created
    Then a slot lifecycle manager for room 1 should be created
