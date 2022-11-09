Feature: Verify all routes of post API are working as expected

  @Regression @POST-001
  Scenario: Verify all routes of post are working as expected
    Given A default user is accessing the portal
    When He want retrieve details of his posts with is postId
    Then this data is also part of user posts
    And this data is part of total list of posts

  @Regression
  Scenario: Data retrieved from nested rotes and direct routes is same
    Given A default user is accessing the portal
    When He retrieves his posts using userId
    And He retrieves his posts through nested route using userId
    And this data is of posts is same

  @Regression @POST-002 @Manual
  Scenario: Verify Empty list is retrieved when user is not found
    Given A default user is accessing the portal
    When He attempts to search for a postId with dummyid
    Then Data retrieved is empty

