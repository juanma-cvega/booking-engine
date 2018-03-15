Feature: As an admin, I want to add tags to a room that belongs to a member

  Scenario: As an admin, I shouldn't be able to add a tag to a member that does not exist
    When member 1 is tried to be added tag for slot status NORMAL to room 4 in building 2
      | MY_BUILDING_TAG |
    Then the admin should get a notification the member 1 does not exist

  Scenario: As an admin, I want to add a tag to a room
    Given user 1 of club 3 with member ID 6 is added to the list of members to manage its authorization
    When member 6 is added tag for slot status NORMAL to room 4 in building 2
      | MY_ROOM_TAG |
    Then member 6 should have building 2 added to its list of buildings
    Then member 6 should have room 4 in building 2 added to its list of buildings
    And room 4 in building 2 of member 6 should have tag of slot status NORMAL in its list of tags
      | MY_ROOM_TAG |

  Scenario: As an admin, I want to add a tags to a room
    Given user 1 of club 3 with member ID 6 is added to the list of members to manage its authorization
    When member 6 is added tag for slot status NORMAL to room 4 in building 2
      | MY_ROOM_TAG | MY_ROOM_TAG_2 |
    Then member 6 should have building 2 added to its list of buildings
    Then member 6 should have room 4 in building 2 added to its list of buildings
    And room 4 in building 2 of member 6 should have tag of slot status NORMAL in its list of tags
      | MY_ROOM_TAG | MY_ROOM_TAG_2 |
