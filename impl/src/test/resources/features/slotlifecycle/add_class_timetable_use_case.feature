Feature: As an admin, I want to add classes to a slot lifecycle manager of a room

  Background:
    Given a slot lifecycle manager for room 1 is to be created
    And the slot duration is 60 minutes
    And the slots are open
      | MONDAY | TUESDAY | WEDNESDAY |
    And the slots are open from 08:00 to 12:00
    And the slots are open from 16:00 to 20:00
    And the slot lifecycle is created with that configuration

  Scenario: As an admin, I want to configure a class to use a set of slots periodically
    When class 5 is configured in slot lifecycle manager for room 1 to use
      | dayOfWeek      | MONDAY      | WEDNESDAY   |
      | slotsStartTime | 10:00,11:00 | 16:00,17:00 |
      | zoneId         | UTC         | UTC         |
    Then slot lifecycle manager for room 1 should contain class 5 to use the room
      | dayOfWeek      | MONDAY      | WEDNESDAY   |
      | slotsStartTime | 10:00,11:00 | 16:00,17:00 |
      | zoneId         | UTC         | UTC         |

  Scenario: As an admin, I want to add a configure more than one class to a room
    When class 5 is configured in slot lifecycle manager for room 1 to use
      | dayOfWeek      | MONDAY      | WEDNESDAY   |
      | slotsStartTime | 10:00,11:00 | 16:00,17:00 |
      | zoneId         | UTC         | UTC         |
    And class 6 is configured in slot lifecycle manager for room 1 to use
      | dayOfWeek      | MONDAY      | WEDNESDAY   |
      | slotsStartTime | 16:00,17:00 | 10:00,11:00 |
      | zoneId         | UTC         | UTC         |
    Then slot lifecycle manager for room 1 should contain class 5 to use the room
      | dayOfWeek      | MONDAY      | WEDNESDAY   |
      | slotsStartTime | 10:00,11:00 | 16:00,17:00 |
      | zoneId         | UTC         | UTC         |
    And slot lifecycle manager for room 1 should contain class 6 to use the room
      | dayOfWeek      | MONDAY      | WEDNESDAY   |
      | slotsStartTime | 16:00,17:00 | 10:00,11:00 |
      | zoneId         | UTC         | UTC         |

  Scenario: As an admin, I shouldn't be able to configure a class for a not available day
    When class 5 is tried to be configured in room 1 to use
      | dayOfWeek      | MONDAY      | THURSDAY    |
      | slotsStartTime | 10:00,11:00 | 16:00,17:00 |
      | zoneId         | UTC         | UTC         |
    Then the admin should receive a notification the class information is not valid for class 5 for room 1
      | dayOfWeek      | MONDAY      | THURSDAY    |
      | slotsStartTime | 10:00,11:00 | 16:00,17:00 |
      | zoneId         | UTC         | UTC         |

  Scenario: As an admin, I shouldn't be able to create configure a class for a not available open time
    When class 5 is tried to be configured in room 1 to use
      | dayOfWeek      | MONDAY      | WEDNESDAY |
      | slotsStartTime | 10:00,11:00 | 23:00     |
      | zoneId         | UTC         | UTC       |
    Then the admin should receive a notification the class information is not valid for class 5 for room 1
      | dayOfWeek      | MONDAY      | WEDNESDAY |
      | slotsStartTime | 10:00,11:00 | 23:00     |
      | zoneId         | UTC         | UTC       |

  Scenario: As an admin, I shouldn't be able to configure a class at the end of an open time for an available day
    When class 5 is tried to be configured in room 1 to use
      | dayOfWeek      | MONDAY      | WEDNESDAY |
      | slotsStartTime | 10:00,11:00 | 20:00     |
      | zoneId         | UTC         | UTC       |
    Then the admin should receive a notification the class information is not valid for class 5 for room 1
      | dayOfWeek      | MONDAY      | WEDNESDAY |
      | slotsStartTime | 10:00,11:00 | 20:00     |
      | zoneId         | UTC         | UTC       |

  Scenario: As an admin, I shouldn't be able to configure a class for a not valid slot time
    When class 5 is tried to be configured in room 1 to use
      | dayOfWeek      | MONDAY      | WEDNESDAY |
      | slotsStartTime | 09:00,11:00 | 16:30     |
      | zoneId         | UTC         | UTC       |
    Then the admin should receive a notification the class information is not valid for class 5 for room 1
      | dayOfWeek      | MONDAY      | WEDNESDAY |
      | slotsStartTime | 09:00,11:00 | 16:30     |
      | zoneId         | UTC         | UTC       |

  Scenario: As an admin, I shouldn't be able to configure a class when there is already a pre reservation for the same date
    And a pre reservation for next MONDAY at 10:00 from zone UTC for user 5 is created for room 1
    When class 5 is tried to be configured in room 1 to use
      | dayOfWeek      | MONDAY      | WEDNESDAY |
      | slotsStartTime | 10:00,11:00 | 16:00     |
      | zoneId         | UTC         | UTC       |
    Then the admin should receive a notification the class information overlaps with an already reserved slot for class 5 for room 1
      | dayOfWeek      | MONDAY      | WEDNESDAY |
      | slotsStartTime | 10:00,11:00 | 16:00     |
      | zoneId         | UTC         | UTC       |

  Scenario: As an admin, I shouldn't be able to configure a class when it overlaps with a class already configured
    And class 3 is configured in slot lifecycle manager for room 1 to use
      | dayOfWeek      | MONDAY      | WEDNESDAY |
      | slotsStartTime | 10:00,11:00 | 16:00     |
      | zoneId         | UTC         | UTC       |
    When class 5 is tried to be configured in room 1 to use
      | dayOfWeek      | MONDAY |
      | slotsStartTime | 11:00  |
      | zoneId         | UTC    |
    Then the admin should receive a notification the class information overlaps with an already reserved slot for class 5 for room 1
      | dayOfWeek      | MONDAY |
      | slotsStartTime | 11:00  |
      | zoneId         | UTC    |

  Scenario: As an admin, I shouldn't be able to configure a class when it overlaps with a class already configured with a different time zone
    And class 3 is configured in slot lifecycle manager for room 1 to use
      | dayOfWeek      | MONDAY      | WEDNESDAY |
      | slotsStartTime | 10:00,11:00 | 16:00     |
      | zoneId         | UTC         | UTC       |
    When class 5 is tried to be configured in room 1 to use
      | dayOfWeek      | MONDAY        |
      | slotsStartTime | 11:00         |
      | zoneId         | Europe/London |
    Then the admin should receive a notification the class information overlaps with an already reserved slot for class 5 for room 1
      | dayOfWeek      | MONDAY        |
      | slotsStartTime | 11:00         |
      | zoneId         | Europe/London |
