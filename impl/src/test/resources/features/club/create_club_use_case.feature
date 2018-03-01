Feature: As an admin, I want to manage clubs

  Scenario: As an admin, I should be able to create a club
    When a club is created by user 1
    Then the club should be stored with user 1 as admin
    And a notification of a created club should be published
