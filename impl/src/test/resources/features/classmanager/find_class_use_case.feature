Feature: As an admin, I want to be able to find created classes given their ids

  Background:
    Given a club is created
    And a building is created
    And that sets the background

  Scenario: As an admin, I want to find a class created
    Given a class is created
    When the class is looked up
    Then a class should be found


