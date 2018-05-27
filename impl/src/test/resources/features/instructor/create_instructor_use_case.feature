Feature: As an admin, I want to register an instructor in a club.

  Background:
    Given a club is created
    And that sets the background

  Scenario: As an admin, I want to register an instructor
    When an instructor is created
    Then the instructor should be registered in the club
    And a notification of a registered instructor should be published
