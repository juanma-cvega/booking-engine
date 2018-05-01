Feature: As an admin, I want to inform of what type of reservations has been made to the slots created

  Scenario: A notification should be sent when a person reserves a slot
    When a notification of a slot reserved by a PERSON with id 7 for slot 34 is published
    Then a notification of a slot reserved by a user with id 7 for slot 34 should be published

  Scenario: A notification should be sent when a class reserves a slot
    When a notification of a slot reserved by a CLASS with id 9 for slot 12 is published
    Then a notification of a slot reserved by a class with id 9 for slot 12 should be published
