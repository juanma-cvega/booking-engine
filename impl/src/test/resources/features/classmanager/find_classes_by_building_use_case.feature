Feature: As an admin, I want to be able to find all classes associated with a building

  Background:
    Given a club is created
    And a building is created

  Scenario: As an admin, I want to find the classes created for a building
    Given a class is created for created building 1
    And a class is created for created building 1
    When the list of classes for the created building 1 is looked up
    Then all created classes should be found

  Scenario: As an admin, I should see only classes created for the building specified
    Given a building is created
    And a class is created for created building 1
    And a class is created for created building 2
    When the list of classes for the created building 1 is looked up
    Then only class created 1 should be found

  Scenario: As an admin, I should get an empty list of classes for a building without classes yet associated to it
    When the list of classes for the created building 1 is looked up
    Then the list of created classes found should be empty
