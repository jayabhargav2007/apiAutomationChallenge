package com.mobiquity.stepDefinitions;


import com.json.model.posts.Post;
import com.mobiquity.apis.BasePage;
import com.mobiquity.apis.PostsAPI;
import com.mobiquity.apis.UsersAPI;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helper.GetTestProperties;
import io.restassured.RestAssured;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserSteps {

    @Steps
    PostsAPI postsAPI;

    @Steps
    UsersAPI usersAPI;

    BasePage basePage = new BasePage();
    GetTestProperties getTestProperties = new GetTestProperties();
    @Given("^A (.*) user is accessing the portal$")
    public void accessPortal(String userType) {
        RestAssured.baseURI = basePage.getBaseURL();
        Serenity.setSessionVariable("userName").to(getTestProperties.getValue(userType));
    }

    @Given("^A (.*) user with a valid (.*)$")
    public void usersPosts(String userType,String postId) {
        accessPortal(userType);
        Serenity.setSessionVariable("postId").to(getTestProperties.getValue(postId));
        List<Post>totalPosts= postsAPI.getPostsByUserId(Long.parseLong(getTestProperties.getValue(postId)));
        Serenity.setSessionVariable("totalPostsForUser").equals(totalPosts);
    }

    @When("^He want retrieve details of a user with users (.*), (.*), (.*)$")
    public void retrieveUser(String id,String username,String email) {
        Serenity.setSessionVariable("userName").to(getTestProperties.getValue(username));
       usersAPI.getUserById(Long.parseLong(getTestProperties.getValue(id)));
       usersAPI.getUserByUsername(getTestProperties.getValue(username));
       usersAPI.getUserByEmail(getTestProperties.getValue(email));
    }

    @Then("^Data retrieved from all user routes is same$")
    public void compareUserData() {
        assertThat(Serenity.sessionVariableCalled("GetUserByUserNameResponse").toString().equalsIgnoreCase(Serenity.sessionVariableCalled("GetUserByIdResponse").toString())).as("User data matching failed").isTrue();
        assertThat(Serenity.sessionVariableCalled("GetUserByEmailResponse").toString().equalsIgnoreCase(Serenity.sessionVariableCalled("GetUserByIdResponse").toString())).as("User data matching failed").isTrue();
    }

    @Then("^this data is part of total list of users$")
    public void CompareWithFullData() {
        usersAPI.getAllUsers();
        assertThat(Serenity.sessionVariableCalled("GetAllUsersResponse").toString().contains(Serenity.sessionVariableCalled("userName").toString())).as("User data matching failed").isTrue();
    }


}
