Feature: As an admin, I want to manage rooms. As a user, I want to be able to see the available rooms

  Scenario: As an admin, I should be able to create a room
    Given a room is created
    When the room is retrieved
    Then the user should see the room created

  Scenario: As an admin, I should be able to verify slots created by a room based on its configuration
    Given a room is to be created
    And the room is open between 08:00 and 12:00
    And current time is 06:00
    And the room can open up to 10 slots at the same time
    And the slots time is of 1 minute
    When the room is created with that configuration
    Then 10 slots should have been created
    And the first slot should start at 08:00
    And the last slot should start at 08:09
    And a new slot should be scheduled to be created at 08:01

  Scenario: As an admin, I should be able to verify slots are created for the closest open time after room creation
    Given a room is to be created
    And the room is open between 08:00 and 12:00
    And the room is open between 14:00 and 20:00
    And current time is 06:00
    And the room can open up to 10 slots at the same time
    And the slots time is of 1 minute
    When the room is created with that configuration
    Then 10 slots should have been created
    And the first slot should start at 08:00
    And the last slot should start at 08:09
    And a new slot should be scheduled to be created at 08:01

  Scenario: As an admin, I should be able to verify slots are created for the closest next open time after room creation
    Given a room is to be created
    And the room is open between 08:00 and 12:00
    And the room is open between 14:00 and 20:00
    And current time is 13:00
    And the room can open up to 10 slots at the same time
    And the slots time is of 1 minute
    When the room is created with that configuration
    Then 10 slots should have been created
    And the first slot should start at 14:00
    And the last slot should start at 14:09
    And a new slot should be scheduled to be created at 08:01

  Scenario: As an admin, I should be able to verify slots creation while current time is within an open time
    Given a room is to be created
    And the room is open between 08:12 and 12:23
    And current time is 08:34
    And the room can open up to 10 slots at the same time
    And the slots time is of 1 minute
    When the room is created with that configuration
    Then 10 slots should have been created
    And the first slot should start at 08:34
    And the last slot should start at 08:43
    And a new slot should be scheduled to be created at 08:01

  Scenario: As an admin, I should be able to verify slots creation between different opening times within the same day
    Given a room is to be created
    And the room is open between 08:12 and 12:23
    And the room is open between 14:47 and 19:09
    And current time is 12:22
    And the room can open up to 10 slots at the same time
    And the slots time is of 1 minute
    When the room is created with that configuration
    Then 10 slots should have been created
    And the first slot should start at 12:22
    And the last slot should start at 14:55
    And a new slot should be scheduled to be created at 08:01

  Scenario: As an admin, I should be able to verify slots creation between different opening times in two different days
    Given a room is to be created
    And the room is open between 08:12 and 12:23
    And current time is 12:16
    And the room can open up to 10 slots at the same time
    And the slots time is of 1 minute
    When the room is created with that configuration
    Then 10 slots should have been created
    And the first slot should start at 12:16
    And the last slot should start at 08:14
    And the last slot should start the day after
    And a new slot should be scheduled to be created at 08:01


  Scenario: As an admin, I should be able to verify slots creation starting next day
    Given a room is to be created
    And the room is open between 08:12 and 12:23
    And current time is 14:00
    And the room can open up to 10 slots at the same time
    And the slots time is of 1 minute
    When the room is created with that configuration
    Then 10 slots should have been created
    And the first slot should start at 08:12
    And the last slot should start at 08:21
    And the first slot should start the day after
    And the last slot should start the day after
    And a new slot should be scheduled to be created at 08:24
