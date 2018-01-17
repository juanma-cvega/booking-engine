Feature: As an admin, I want to manage buildings.

  Scenario: As an admin, I should be able to create a building
    Given a club is created
    And that sets the background
    When a building is created
    Then the building should be stored
    And a notification of a created building should be published
