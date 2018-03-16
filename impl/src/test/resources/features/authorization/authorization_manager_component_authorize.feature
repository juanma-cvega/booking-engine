Feature: As an admin, I want to authorise users based on their credentials

  Scenario: As an admin, I should authorise a user to access a slot when there are no credentials set
    Given club 3 is added to the list of clubs to manage its authorization
    And user 2 of club 3 with member ID 6 is added to the list of members to manage its authorization
    And current date time is 2018-01-10 20:00
    When admin verifies credentials of user 2 to access slot created the 2018-01-10 at 18:00 in room 3 in building 4 in club 3
    Then the member should be authorised

  Scenario: As an admin, I should authorise a user to access a slot when he has the right credentials for the building
    Given club 3 is added to the list of clubs to manage its authorization
    And user 2 of club 3 with member ID 6 is added to the list of members to manage its authorization
    And current date time is 2018-01-10 20:00
    And club 3 is added tag to building 4
      | MY_BUILDING_TAG |
    And member 6 is added tag to building 4
      | MY_BUILDING_TAG |
    When admin verifies credentials of user 2 to access slot created the 2018-01-10 at 18:00 in room 3 in building 4 in club 3
    Then the member should be authorised

  Scenario: As an admin, I shouldn't authorise a user to access a slot when he has the wrong credentials for the building
    Given club 3 is added to the list of clubs to manage its authorization
    And user 2 of club 3 with member ID 6 is added to the list of members to manage its authorization
    And current date time is 2018-01-10 20:00
    And club 3 is added tag to building 4
      | MY_BUILDING_TAG |
    And member 6 is added tag to building 4
      | MY_OTHER_BUILDING_TAG |
    When admin verifies credentials of user 2 to access slot created the 2018-01-10 at 18:00 in room 3 in building 4 in club 3
    Then the member should not be authorised

  Scenario: As an admin, I shouldn't authorise a user to access a slot when he has no credentials for the building
    Given club 3 is added to the list of clubs to manage its authorization
    And user 2 of club 3 with member ID 6 is added to the list of members to manage its authorization
    And current date time is 2018-01-10 20:00
    And club 3 is added tag to building 4
      | MY_BUILDING_TAG |
    When admin verifies credentials of user 2 to access slot created the 2018-01-10 at 18:00 in room 3 in building 4 in club 3
    Then the member should not be authorised

  Scenario: As an admin, I should authorise a user to access a slot when he has the right credentials for the room and slot type
    Given club 3 is added to the list of clubs to manage its authorization
    And user 2 of club 3 with member ID 6 is added to the list of members to manage its authorization
    And current date time is 2018-01-10 20:00
    And club 3 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    And member 6 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    When admin verifies credentials of user 2 to access slot created the 2018-01-10 at 18:00 in room 3 in building 4 in club 3
    Then the member should be authorised

  Scenario: As an admin, I should authorise a user to access a slot when he has one right credential for the room and slot type
    Given club 3 is added to the list of clubs to manage its authorization
    And user 2 of club 3 with member ID 6 is added to the list of members to manage its authorization
    And current date time is 2018-01-10 20:00
    And club 3 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG | MY_OTHER_ROOM_TAG
    And member 6 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    When admin verifies credentials of user 2 to access slot created the 2018-01-10 at 18:00 in room 3 in building 4 in club 3
    Then the member should be authorised

  Scenario: As an admin, I should authorise a user to access a slot when he has the right credentials for the building, the room and slot type
    Given club 3 is added to the list of clubs to manage its authorization
    And user 2 of club 3 with member ID 6 is added to the list of members to manage its authorization
    And current date time is 2018-01-10 20:00
    And club 3 is added tag to building 4
      | MY_BUILDING_TAG |
    And member 6 is added tag to building 4
      | MY_BUILDING_TAG |
    And club 3 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    And member 6 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    When admin verifies credentials of user 2 to access slot created the 2018-01-10 at 18:00 in room 3 in building 4 in club 3
    Then the member should be authorised

  Scenario: As an admin, I should authorise a user to access a slot when he has the right credentials for the building and one for the room and slot type
    Given club 3 is added to the list of clubs to manage its authorization
    And user 2 of club 3 with member ID 6 is added to the list of members to manage its authorization
    And current date time is 2018-01-10 20:00
    And club 3 is added tag to building 4
      | MY_BUILDING_TAG |
    And member 6 is added tag to building 4
      | MY_BUILDING_TAG |
    And club 3 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG | MY_OTHER_ROOM_TAG |
    And member 6 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    When admin verifies credentials of user 2 to access slot created the 2018-01-10 at 18:00 in room 3 in building 4 in club 3
    Then the member should be authorised

  Scenario: As an admin, I should authorise a user to access a slot when he has the right type of credentials for the slot configuration
    Given club 3 is added to the list of clubs to manage its authorization
    And user 2 of club 3 with member ID 6 is added to the list of members to manage its authorization
    And current date time is 2018-01-10 20:00
    And club 3 is added tag for slot status EARLY_BIRD to room 3 in building 4
      | MY_ROOM_TAG |
    And club 3 is added the slot authorization configuration of 2 DAYS for room 3 in building 4
    And member 6 is added tag for slot status EARLY_BIRD to room 3 in building 4
      | MY_ROOM_TAG |
    When admin verifies credentials of user 2 to access slot created the 2018-01-10 at 18:00 in room 3 in building 4 in club 3
    Then the member should be authorised

  Scenario: As an admin, I shouldn't authorise a user to access a slot when he does not have the right credentials for the room for a slot type
    Given club 3 is added to the list of clubs to manage its authorization
    And user 2 of club 3 with member ID 6 is added to the list of members to manage its authorization
    And current date time is 2018-01-10 20:00
    And club 3 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    And member 6 created is added tag for slot status NORMAL to room 3 in building 4
      | MY_OTHER_ROOM_TAG |
    When admin verifies credentials of user 2 to access slot created the 2018-01-10 at 18:00 in room 3 in building 4 in club 3
    Then the user 2 should receive a notification he is not authorised to use room 3 in building 4 in club 3

  Scenario: As an admin, I shouldn't authorise a user to access a slot when he has the right credentials for the building and the room but for another slot type
    Given club 3 is added to the list of clubs to manage its authorization
    And user 2 of club 3 with member ID 6 is added to the list of members to manage its authorization
    And current date time is 2018-01-10 20:00
    And club 3 is added tag to building 4
      | MY_BUILDING_TAG |
    And member 6 created is added tag to building 4
      | MY_BUILDING_TAG |
    And club 3 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    And member 6 is added tag for slot status EARLY_BIRD to room 3 in building 4
      | MY_ROOM_TAG |
    When admin verifies credentials of user 2 to access slot created the 2018-01-10 at 18:00 in room 3 in building 4 in club 3
    Then the user 2 should receive a notification he is not authorised to use room 3 in building 4 in club 3

  Scenario: As an admin, I shouldn't authorise a user to access a slot when there are no credentials for building but he has the right credentials for another slot type
    Given club 3 is added to the list of clubs to manage its authorization
    And user 2 of club 3 with member ID 6 is added to the list of members to manage its authorization
    And current date time is 2018-01-10 20:00
    And club 3 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    And club 3 is added the slot authorization configuration of 2 DAYS for room 3 in building 4
    And member 6 is added tag for slot status EARLY_BIRD to room 3 in building 4
      | MY_ROOM_TAG |
    When admin verifies credentials of user 2 to access slot created the 2018-01-10 at 18:00 in room 3 in building 4 in club 3
    Then the user 2 should receive a notification he is not authorised to use room 3 in building 4 in club 3

  Scenario: As an admin, I shouldn't authorise a user to access a slot when he has the right credentials for the building but not for the slot type
    Given club 3 is added to the list of clubs to manage its authorization
    And user 2 of club 3 with member ID 6 is added to the list of members to manage its authorization
    And club 3 is added tag to building 4
      | MY_BUILDING_TAG |
    And member 6 is added tag to building 4
      | MY_BUILDING_TAG |
    And club 3 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    And member 6 is added tag for slot status NORMAL to room 3 in building 4
      | MY_OTHER_ROOM_TAG |
    When admin verifies credentials of user 2 to access slot created the 2018-01-10 at 18:00 in room 3 in building 4 in club 3
    Then the user 2 should receive a notification he is not authorised to use room 3 in building 4 in club 3

  Scenario: As an admin, I shouldn't authorise a user to access a slot when he has the right credentials for the room and slot type but not for the building
    Given club 3 is added to the list of clubs to manage its authorization
    And user 2 of club 3 with member ID 6 is added to the list of members to manage its authorization
    And club 3 is added tag to building 4
      | MY_BUILDING_TAG |
    And member 6 is added tag to building 4
      | MY_OTHER_BUILDING_TAG |
    And club 3 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    And member 6 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    When admin verifies credentials of user 2 to access slot created the 2018-01-10 at 18:00 in room 3 in building 4 in club 3
    Then the user 2 should receive a notification he is not authorised to use room 3 in building 4 in club 3

  Scenario: As an admin, I shouldn't authorise a user to access a slot when he has the right credentials for the room and slot type but a different building
    Given club 3 is added to the list of clubs to manage its authorization
    And user 2 of club 3 with member ID 6 is added to the list of members to manage its authorization
    And club 3 is added tag to building 4
      | MY_BUILDING_TAG |
    And member 6 is added tag to building 7
      | MY_BUILDING_TAG |
    And club 3 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    And member 6 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    When admin verifies credentials of user 2 to access slot created the 2018-01-10 at 18:00 in room 3 in building 4 in club 3
    Then the user 2 should receive a notification he is not authorised to use room 3 in building 4 in club 3

  Scenario: As an admin, I shouldn't authorise a user to access a slot when he has the right credentials for a different room
    Given club 3 is added to the list of clubs to manage its authorization
    And user 2 of club 3 with member ID 6 is added to the list of members to manage its authorization
    And club 3 is added tag to building 4
      | MY_BUILDING_TAG |
    And member 6 is added tag to building 4
      | MY_BUILDING_TAG |
    And club 3 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    And member 6 is added tag for slot status NORMAL to room 4 in building 4
      | MY_ROOM_TAG |
    When admin verifies credentials of user 2 to access slot created the 2018-01-10 at 18:00 in room 3 in building 4 in club 3
    Then the user 2 should receive a notification he is not authorised to use room 3 in building 4 in club 3
