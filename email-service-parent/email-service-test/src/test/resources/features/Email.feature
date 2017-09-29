@email
Feature: Email Features

  Scenario: An email is sent to Administrators inbox when CreateUserEvent received
    Given a valid createUserEvent payload
    When the payload is sent to the createUserEvent topic
    Then an email is sent to the Administrators