Feature: As an admin, I want to be able to remove a class from a building

  Background:
    Given a club is created
    And a building is created
    And a class is created
    And that sets the background

  Scenario: As an admin, I want to remove a class from a building
    When the class is remove
    Then a class should be removed
    And a notification of a removed class should published

  Scenario: As an admin, I shouldn't be able to remove a class while it still contains rooms registrations
    Given a room is created
    And the room is registered to give the class
    When the class is tried to be removed
    Then the admin should be notified the class cannot be removed
    And the class should still be available
