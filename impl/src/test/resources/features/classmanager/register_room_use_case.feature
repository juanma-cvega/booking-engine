Feature: As an admin, I want to be able to register a room as giving the class configured

  Background:
    Given a club is created
    And a building is created
    And a room is created
    And a class is created
    And that sets the background

  Scenario: As an admin, I want to add a instructor to the list of instructors available for a class
    When the room is registered to give the class
    Then the class should have the room registered
    And a notification of the room being registered in the class should be published
