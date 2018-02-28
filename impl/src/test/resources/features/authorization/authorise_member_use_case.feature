Feature: As an admin, I want to authorise users based on their credentials.

  Scenario: As an admin, I should authorise a user to access a slot when there are no credentials set
    Given club 1 is added to the list of clubs to manage its authorization
    And member 2 is added to the list of members to manage its authorization
    When admin verifies credentials of member 2 to access room 3 with slot status NORMAL of building 4 in club 1
    Then the member should be authorised

  Scenario: As an admin, I should authorise a user to access a slot when he has the right credentials for the building
    Given club 1 is added to the list of clubs to manage its authorization
    And member 2 is added to the list of members to manage its authorization
    And club 1 is added tag to building 4
      | MY_BUILDING_TAG |
    And member 2 is added tag to building 4
      | MY_BUILDING_TAG |
    When admin verifies credentials of member 2 to access room 3 with slot status NORMAL of building 4 in club 1
    Then the member should be authorised

  Scenario: As an admin, I shouldn't authorise a user to access a slot when he has the wrong credentials for the building
    Given club 1 is added to the list of clubs to manage its authorization
    And member 2 is added to the list of members to manage its authorization
    And club 1 is added tag to building 4
      | MY_BUILDING_TAG |
    And member 2 is added tag to building 4
      | MY_OTHER_BUILDING_TAG |
    When admin verifies credentials of member 2 to access room 3 with slot status NORMAL of building 4 in club 1
    Then the member should not be authorised

  Scenario: As an admin, I shouldn't authorise a user to access a slot when he has no credentials for the building
    Given club 1 is added to the list of clubs to manage its authorization
    And member 2 is added to the list of members to manage its authorization
    And club 1 is added tag to building 4
      | MY_BUILDING_TAG |
    When admin verifies credentials of member 2 to access room 3 with slot status NORMAL of building 4 in club 1
    Then the member should not be authorised

  Scenario: As an admin, I should authorise a user to access a slot when he has the right credentials for the room and slot type
    Given club 1 is added to the list of clubs to manage its authorization
    And member 2 is added to the list of members to manage its authorization
    And club 1 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    And member 2 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    When admin verifies credentials of member 2 to access room 3 with slot status NORMAL of building 4 in club 1
    Then the member should be authorised

  Scenario: As an admin, I should authorise a user to access a slot when he has one right credential for the room and slot type
    Given club 1 is added to the list of clubs to manage its authorization
    And member 2 is added to the list of members to manage its authorization
    And club 1 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG | MY_OTHER_ROOM_TAG
    And member 2 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    When admin verifies credentials of member 2 to access room 3 with slot status NORMAL of building 4 in club 1
    Then the member should be authorised

  Scenario: As an admin, I should authorise a user to access a slot when he has the right credentials for the building, the room and slot type
    Given club 1 is added to the list of clubs to manage its authorization
    And member 2 is added to the list of members to manage its authorization
    And club 1 is added tag to building 4
      | MY_BUILDING_TAG |
    And member 2 is added tag to building 4
      | MY_BUILDING_TAG |
    And club 1 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    And member 2 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    When admin verifies credentials of member 2 to access room 3 with slot status NORMAL of building 4 in club 1
    Then the member should be authorised

  Scenario: As an admin, I should authorise a user to access a slot when he has the right credentials for the building and one for the room and slot type
    Given club 1 is added to the list of clubs to manage its authorization
    And member 2 is added to the list of members to manage its authorization
    And club 1 is added tag to building 4
      | MY_BUILDING_TAG |
    And member 2 is added tag to building 4
      | MY_BUILDING_TAG |
    And club 1 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG | MY_OTHER_ROOM_TAG |
    And member 2 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    When admin verifies credentials of member 2 to access room 3 with slot status NORMAL of building 4 in club 1
    Then the member should be authorised

  Scenario: As an admin, I shouldn't authorise a user to access a slot when he does not have the right credentials for the room for a slot type
    Given club 1 is added to the list of clubs to manage its authorization
    And member 2 is added to the list of members to manage its authorization
    And club 1 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    And member 2 is added tag for slot status NORMAL to room 3 in building 4
      | MY_OTHER_ROOM_TAG |
    When admin verifies credentials of member 2 to access room 3 with slot status NORMAL of building 4 in club 1
    Then the member should not be authorised

  Scenario: As an admin, I shouldn't authorise a user to access a slot when he has the right credentials for the building and the room but for another slot type
    Given club 1 is added to the list of clubs to manage its authorization
    And member 2 is added to the list of members to manage its authorization
    And club 1 is added tag to building 4
      | MY_BUILDING_TAG |
    And member 2 is added tag to building 4
      | MY_BUILDING_TAG |
    And club 1 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    And member 2 is added tag for slot status EARLY_BIRD to room 3 in building 4
      | MY_ROOM_TAG |
    When admin verifies credentials of member 2 to access room 3 with slot status NORMAL of building 4 in club 1
    Then the member should not be authorised

  Scenario: As an admin, I shouldn't authorise a user to access a slot when there are no credentials for building but he has the right credentials for another slot type
    Given club 1 is added to the list of clubs to manage its authorization
    And member 2 is added to the list of members to manage its authorization
    And club 1 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    And member 2 is added tag for slot status EARLY_BIRD to room 3 in building 4
      | MY_ROOM_TAG |
    When admin verifies credentials of member 2 to access room 3 with slot status NORMAL of building 4 in club 1
    Then the member should not be authorised

  Scenario: As an admin, I shouldn't authorise a user to access a slot when he has the right credentials for the building but not for the slot type
    Given club 1 is added to the list of clubs to manage its authorization
    And member 2 is added to the list of members to manage its authorization
    And club 1 is added tag to building 4
      | MY_BUILDING_TAG |
    And member 2 is added tag to building 4
      | MY_BUILDING_TAG |
    And club 1 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    And member 2 is added tag for slot status NORMAL to room 3 in building 4
      | MY_OTHER_ROOM_TAG |
    When admin verifies credentials of member 2 to access room 3 with slot status NORMAL of building 4 in club 1
    Then the member should not be authorised

  Scenario: As an admin, I shouldn't authorise a user to access a slot when he has the right credentials for the room and slot type but not for the building
    Given club 1 is added to the list of clubs to manage its authorization
    And member 2 is added to the list of members to manage its authorization
    And club 1 is added tag to building 4
      | MY_BUILDING_TAG |
    And member 2 is added tag to building 4
      | MY_OTHER_BUILDING_TAG |
    And club 1 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    And member 2 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    When admin verifies credentials of member 2 to access room 3 with slot status NORMAL of building 4 in club 1
    Then the member should not be authorised

  Scenario: As an admin, I shouldn't authorise a user to access a slot when he has the right credentials for the room and slot type but a different building
    Given club 1 is added to the list of clubs to manage its authorization
    And member 2 is added to the list of members to manage its authorization
    And club 1 is added tag to building 4
      | MY_BUILDING_TAG |
    And member 2 is added tag to building 7
      | MY_BUILDING_TAG |
    And club 1 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    And member 2 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    When admin verifies credentials of member 2 to access room 3 with slot status NORMAL of building 4 in club 1
    Then the member should not be authorised

  Scenario: As an admin, I shouldn't authorise a user to access a slot when he has the right credentials for a different room
    Given club 1 is added to the list of clubs to manage its authorization
    And member 2 is added to the list of members to manage its authorization
    And club 1 is added tag to building 4
      | MY_BUILDING_TAG |
    And member 2 is added tag to building 4
      | MY_BUILDING_TAG |
    And club 1 is added tag for slot status NORMAL to room 3 in building 4
      | MY_ROOM_TAG |
    And member 2 is added tag for slot status NORMAL to room 4 in building 4
      | MY_ROOM_TAG |
    When admin verifies credentials of member 2 to access room 3 with slot status NORMAL of building 4 in club 1
    Then the member should not be authorised
