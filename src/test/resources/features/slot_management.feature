Feature: As an admin, I want to manage slots in a room. As a user, I want to be able to see the available slots

  Scenario: A slot is created
    Given a slot is created
    When the slot is retrieved
    Then the slot fetched and the slot created should be the same

  Scenario: A list of slots are created for a room and then listed
    Given a slot is created for room 1
    And a slot is created for room 1
    When the list of slots is fetched for room 1
    Then the list should contain the created rooms
