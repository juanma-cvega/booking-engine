Feature: As an admin, I want to be able to unregister a room from a class

  Background:
    Given a club is created
    And a building is created
    And a room is created
    And a class is created

  Scenario: As an admin, I want to add a instructor to the list of instructors available for a class
    Given the room is registered to give the class
    And that sets the background
    When the room is unregistered from the class
    Then the class should not have the room registered
    And a notification of the room being unregistered from the class should be published
