Feature: As an admin, I want to add tags to a building that belongs to a member

  Scenario: As an admin, I shouldn't be able to add a tag to a member that does not exist
    When member 1 is tried to be added tag to building 1
      | MY_BUILDING_TAG |
    Then the admin should get a notification the member 1 does not exist

  Scenario: As an admin, I want to add a tag to a building
    Given member 1 is added to the list of members to manage its authorization
    When member 1 is added tag to building 1
      | MY_BUILDING_TAG |
    Then member 1 should have building 1 added to its list of buildings
    And building 1 of member 1 should have tag in its list of tags
      | MY_BUILDING_TAG |

  Scenario: As an admin, I want to add a tags to a building
    Given member 1 is added to the list of members to manage its authorization
    When member 1 is added tags to building 1
      | MY_BUILDING_TAG | MY_BUILDING_TAG_2 |
    Then member 1 should have building 1 added to its list of buildings
    And building 1 of member 1 should have tags in its list of tags
      | MY_BUILDING_TAG | MY_BUILDING_TAG_2 |
