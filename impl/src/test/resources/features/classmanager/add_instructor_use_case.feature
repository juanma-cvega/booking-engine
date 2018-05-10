Feature: As an admin, I want to be able to add instructors to the list of available instructors for a class

  Background:
    Given a club is created
    And a building is created
    And a class is created
    And that sets the background

  Scenario: As an admin, I want to add a instructor to the list of instructors available for a class
    When instructor 56 is added to the class
    Then the class should have instructor 56 as an available instructor
    And a notification of the newly added instructor 56 to the class should published
