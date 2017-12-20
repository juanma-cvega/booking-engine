Feature: As an admin, I want to manage slots in a room. As a user, I want to be able to see the available slots

  Scenario: As a user, I should be notified when a slot does not exist
    When a non existent slot is retrieved
    Then the user should be notified the slot does not exist
