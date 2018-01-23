Feature: As an admin of a club, I want to be able to accept a join request from another user

  Scenario: As an admin, I want to accept a join request
    Given a club with name CLUB_NAME is created by user 1
    And user 2 issues a join request
    And that sets the background
    When admin 1 accepts the join request created by user 2
    Then the club should not have the join request for user 2 anymore
    And a notification of a join request accepted for user 2 should be published
