Feature: Verify all routes of user are working as expected

  @Regression @USER-001
  Scenario: Verify all routes of user are working as expected
    Given A admin user is accessing the portal
    When He want retrieve details of a user with users uniqueId, uniqueUsername, uniqueEmail
    Then Data retrieved from all user routes is same
    And this data is part of total list of users

  @Regression @USER-002 @Manual
  Scenario: Verify Empty list is retrieved when user is not found
    Given A admin user is accessing the portal
    When He attempts to search for a user with dummyName
    Then Data retrieved is empty