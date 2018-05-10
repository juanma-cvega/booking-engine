Feature: As an admin, I want to be able to remove a class from a building

  Background:
    Given a club is created
    And a building is created
    And a class is created
    And that sets the background

  Scenario: As an admin, I want to create a class to be used by rooms in a building
    When the class is remove
    Then a class should be removed
    And a notification of a removed class should published


