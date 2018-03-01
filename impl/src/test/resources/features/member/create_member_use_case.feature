Feature: As a user, I want to become a member of a club after my join request has been accepted

  Scenario: As a user, I want to become a member of a club
    Given a club with name CLUB_NAME is created by user 1
    And user 2 issues a join request
    And admin 1 accepts the join request created by user 2
    And that sets the background
    When the accepted join request for user 2 is processed
    Then the user 2 should be a member of club
    And a notification of a new membership for user 2 has been created should be published
