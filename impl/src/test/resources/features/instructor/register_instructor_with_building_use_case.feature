Feature: As an admin, I want to register a club instructor with a building

  Background:
    Given a club is created
    And a building is created

  Scenario: As an admin, I want to register a club instructor with a building
    Given an instructor is created
    When the instructor is registered with the building
    Then the instructor should be registered with the building
    And a notification of an instructor registered with the building should be published
