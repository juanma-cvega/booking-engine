Feature: As an admin, I want to manage slots in a room. As a user, I want to be able to see the available slots

  Scenario: As an admin, I should be able to create a slot
    Given a slot is created
    When the slot is retrieved
    Then the user should see the slot created

  Scenario: As an admin, I should be able to create a list of slots for a room and then list them
    Given a slot is created for room 1
    And a slot is created for room 1
    When the list of slots is fetched for room 1
    Then the list should contain the created rooms

  Scenario: As a user, I should be able to find a room by its identifier
    When a non existent slot is retrieved
    Then the user should be notified the slot does not exist
