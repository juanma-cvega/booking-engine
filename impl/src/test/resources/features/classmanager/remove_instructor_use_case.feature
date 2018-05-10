Feature: As an admin, I want to be able to remove instructors from the list of available instructors for a class but
  only when there are remaining instructors

  Background:
    Given a club is created
    And a building is created

  Scenario: As an admin, I want to remove an instructor from the list of available instructors in a class
    Given a class is created with instructor 56
    And instructor 66 is added to the class
    And that sets the background
    When instructor 56 is removed from the class
    Then the class should not have instructor 56 as an available instructor
    And the class should have instructor 66 as an available instructor
    And a notification of the removed instructor 56 from the class should published

  Scenario: As an admin, I should be able to remove an instructor from the list of available instructors in a class if he is the last one
    Given a class is created with instructor 56
    And that sets the background
    When instructor 56 is tried to be removed from the class
    Then the admin should be notified the instructor 56 cannot be removed and leave the list of instructors empty for the class
    And the class should have instructor 56 as an available instructor
