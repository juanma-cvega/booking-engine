Feature: As an admin, I want to manage buildings

  Scenario: As an admin, I want to be able to create a building
    Given a club is created
    And that sets the background
    When a building is created
    Then the building should be stored
    And a notification of a created building should be published

  Scenario: As an admin, I shouldn't be able to create a building if the club associated does not exist
    When a building is created for a non existing club
    Then the user should be notified the club does not exist
