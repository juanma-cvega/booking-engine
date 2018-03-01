Feature: As a user, I want to issue a join request for an existing club

  Scenario: As a user, I want to issue a join request
    Given a club is created
    And that sets the background
    When user 2 issues a join request
    Then the club should have a join request created for user
      | 2 |
    And a notification of a join request created should be published

  Scenario: As different users, we want to issue a join request each one of us
    Given a club is created
    And that sets the background
    When user 2 issues a join request
    And user 3 issues a join request
    Then the club should have a join request created for users
      | 2 | 3 |
    And 2 notifications of join requests created should be published
