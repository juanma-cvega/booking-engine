Feature: As an admin of a club, I want to be able to list all pending join requests

  Scenario: As an admin, I want to list pending join requests
    Given a club with name CLUB_NAME is created by user 1
    And user 2 issues a join request
    And user 3 issues a join request
    When admin 1 looks for all join requests created
    Then the admin should be able to see the list of join requests containing requests for users
      | 2 | 3 |

  Scenario: As a normal user, I cannot see the list of join requests
    Given a club with name CLUB_NAME is created by user 1
    And user 2 issues a join request
    And user 3 issues a join request
    When user 4 looks for all join requests created
    Then the user 4 should receive a notification that he is not allowed to see the list
