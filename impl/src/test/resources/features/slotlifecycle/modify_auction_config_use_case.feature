Feature: As an admin, I want to modify the auction configuration of a slot life cycle manager

  Scenario: As an admin, I want to modify the auction configuration of a slot life cycle manager
    Given a slot lifecycle manager 1 is created
    When an auction configuration of 5 minutes duration and bookings period of 2 days is added to slot lifecycle manager 1
    Then the slot lifecycle manager 1 should contain the auction configuration of 5 minutes duration and bookings period of 2 days
