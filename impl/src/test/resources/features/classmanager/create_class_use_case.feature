Feature: As an admin, I want to be able to create classes to be used by rooms of a building to pre reserve slots

  Background:
    Given a club is created
    And a building is created
    And that sets the background

  Scenario: As an admin, I want to create a class to be used by rooms in a building
    When a class is created
    Then a class should be created
    And a notification of a created class should published


