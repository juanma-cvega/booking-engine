Feature: As an admin, I want to add the newly created club to the list of clubs to manage its authorization

  Scenario: As an admin, I want to add the newly created club to the list of clubs to manage its authorization
    When user 1 of club 3 with member ID 6 is added to the list of members to manage its authorization
    Then the member 6 should be added to the list of members to manage its authorization
