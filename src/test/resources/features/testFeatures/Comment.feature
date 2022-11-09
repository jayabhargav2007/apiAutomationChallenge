Feature: Verify all routes of comment API are working as expected

  @Regression @Comment-001
  Scenario: Verify all routes of comments are working as expected
    Given A default user with a valid postId
    And want to search for the comments using commentId, commenterEmail
    Then Data retrieved from both comment routes is same
    And this data is also part of postId comments
    And this data is part of total list of comments

  @Regression
  Scenario: Data retrieved from nested rotes and direct routes is same
    Given A default user with a valid postId
    When He retrieve his comments using postId
    And He retrieve his comments through nested route for postId
    And this data is of comments is same

  @Regression @Comment-002 @Manual
  Scenario: Verify Empty list is retrieved when comments is not found
    Given A default user with a valid postId
    When He attempts to search for a postId with dummyCommentId
    Then Data retrieved is empty


