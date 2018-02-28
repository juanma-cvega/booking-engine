Feature: As an admin, I want to add the newly created club to the list of clubs to manage its authorization

  Scenario: As an admin, I want to add the newly created club to the list of clubs to manage its authorization
    When club 1 is added to the list of clubs to manage its authorization
    Then the club 1 should be added to the list of clubs to manage its authorization
