Feature: As an admin of a club, I want to be able to deny a join request from another user

  Scenario: As an admin, I want to deny a join request
    Given a club with name CLUB_NAME is created by user 1
    And user 2 issues a join request
    And that sets the background
    When admin 1 denies the join request created by user 2
    Then the club should not have the join request for user 2 anymore
    And a notification of a join request denied for user 2 should be published

  Scenario: As a normal user, I shouldn't be able to deny a join request
    Given a club with name CLUB_NAME is created by user 1
    And user 2 issues a join request
    And that sets the background
    When user 3 denies the join request created by user 2
    Then the user 3 should be notified he has no rights to deny join requests
    And the club should have the join request for user 2
    And a notification of a join request denied shouldn't be published

  Scenario: As an admin, I shouldn't be able to deny a join request that does not exist
    Given a club with name CLUB_NAME is created by user 1
    And that sets the background
    When admin 1 denies the non existing join request 5
    Then the admin should be notified the join request 5 does not exist
    And a notification of a join request denied shouldn't be published
