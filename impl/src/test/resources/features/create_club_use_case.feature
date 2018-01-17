Feature: As an admin, I want to manage clubs.

  Scenario: As an admin, I should be able to create a club
    When a club is created
    Then the club should be stored
    And a notification of a created club should be published
