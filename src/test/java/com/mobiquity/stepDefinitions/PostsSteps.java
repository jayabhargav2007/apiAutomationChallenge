package com.mobiquity.stepDefinitions;

import com.json.model.posts.Post;
import com.mobiquity.apis.PostsAPI;
import com.mobiquity.apis.UsersAPI;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helper.GetTestProperties;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PostsSteps {

    @Steps
    PostsAPI postsAPI;

    @Steps
    UsersAPI usersAPI;

    GetTestProperties getTestProperties = new GetTestProperties();

    @When("^He want retrieve details of his posts with is (.*)$")
    public void fetchPostWithPostId(String postId) {
        Serenity.setSessionVariable("postId").to(getTestProperties.getValue(postId));
        postsAPI.getPostsById(Long.parseLong(getTestProperties.getValue(postId)));
    }


    @Then("^this data is also part of user posts$")
    public void assertUserPosts() {
        postsAPI.getPostsByUserId(usersAPI.getUserId(Serenity.sessionVariableCalled("userName")));
        assertThat(Serenity.sessionVariableCalled("GetPostsByUserIDResponse").toString().contains(Serenity.sessionVariableCalled("postId"))).isTrue();
    }

    @Then("^this data is part of total list of posts$")
    public void assertTotalPosts() {
        postsAPI.getAllPosts();
        assertThat(Serenity.sessionVariableCalled("GetAllPostsResponse").toString().contains(Serenity.sessionVariableCalled("postId"))).isTrue();

    }

    @When("^He retrieves his posts using (.*)$")
    public void retrievePosts(String userId) {
        Serenity.setSessionVariable("userId").to(getTestProperties.getValue(userId));
        postsAPI.getPostsByUserId(Long.parseLong(getTestProperties.getValue(userId)));
    }

    @When("^He retrieves his posts through nested route using (.*)$")
    public void RetrieveNestedPosts(String userId) {
        postsAPI.getPostsByNested(Long.parseLong(getTestProperties.getValue(userId)));
    }

    @When("^this data is of posts is same$")
    public void this_data_is_of_posts_is_same() {
        assertThat(Serenity.sessionVariableCalled("GetPostsByNestedResponse").toString().equalsIgnoreCase(Serenity.sessionVariableCalled("GetPostsByUserIDResponse").toString())).as("Posts data for nested routes matching failed").isTrue();
    }

    @When("^He fetch the posts created by him$")
    public void fetchPosts() {
        long userId= usersAPI.getUserId(Serenity.sessionVariableCalled("userName"));
        List<Post>totalPosts= postsAPI.getPostsByUserId(userId);
        Serenity.setSessionVariable("totalPostsForUser").equals(totalPosts);
    }

    @Then("^all the posts posted by him are displayed as expected$")
    public void all_the_posts_posted_by_him_are_displayed_as_expected() {
        assertThat(Serenity.sessionVariableCalled("GetPostsByUserIDStatusCode").toString()).as("Status code is expected to be").isEqualTo("200");
        assertThat((Integer)Serenity.sessionVariableCalled("totalPostsCount")).as("Count of posts is as expected").isGreaterThan(0);
    }
}
