Feature: As an admin, I want to manage rooms. As a user, I want to be able to see the available rooms

  Scenario: As an admin, I want to be able to create a room
    Given a club is created
    And a building is created
    And that sets the background
    When a room is created
    Then the room should be stored
    And a notification of a created room should be published

  Scenario: As an admin, I shouldn't be able to create a room if the building it is associated to does not exist
    When a room is created for a non existing building
    Then the user should be notified the building does not exist
