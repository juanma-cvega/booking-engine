Feature: As an admin, I want to add tags to a building that belongs to a club

  Scenario: As an admin, I want to add a tag to a building
    Given club 1 is added to the list of clubs to manage its authorization
    When club 1 is added tag to building 1
      | MY_BUILDING_TAG |
    Then club 1 should have building 1 added to its list of buildings
    And building 1 of club 1 should have tag in its list of tags
      | MY_BUILDING_TAG |

  Scenario: As an admin, I want to add a tags to a building
    Given club 1 is added to the list of clubs to manage its authorization
    When club 1 is added tags to building 1
      | MY_BUILDING_TAG | MY_BUILDING_TAG_2 |
    Then club 1 should have building 1 added to its list of buildings
    And building 1 of club 1 should have tags in its list of tags
      | MY_BUILDING_TAG | MY_BUILDING_TAG_2 |
