Feature: As a user, I want to find a club by its name

  Scenario: As a user, I want to find a club by its name
    Given a club is created with name CLUB_NAME
    When a user searches for the club CLUB_NAME
    Then the user should find the club
