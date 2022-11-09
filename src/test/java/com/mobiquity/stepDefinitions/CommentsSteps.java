package com.mobiquity.stepDefinitions;


import com.json.model.comments.Comment;
import com.json.model.posts.Post;
import com.mobiquity.apis.BasePage;
import com.mobiquity.apis.CommentsAPI;
import com.mobiquity.apis.PostsAPI;
import com.mobiquity.apis.UsersAPI;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helper.GetTestProperties;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentsSteps {

    @Steps
    PostsAPI postsAPI;

    @Steps
    UsersAPI usersAPI;

    @Steps
    CommentsAPI commentsAPI;

    GetTestProperties getTestProperties = new GetTestProperties();

    @Given("^want to search for the comments using (.*), (.*)$")
    public void retrieveComments(String commentId,String commenterEmail) {
        Serenity.setSessionVariable("emailId").to(getTestProperties.getValue(commenterEmail));
        commentsAPI.getCommentsForCommentId(Long.parseLong(getTestProperties.getValue(commentId)));
        commentsAPI.getCommentsForEmailId(getTestProperties.getValue(commenterEmail));
    }

    @Then("^Data retrieved from both comment routes is same$")
    public void assertCommentRoutes() {
        assertThat(Serenity.sessionVariableCalled("GetCommentsByCommentIDResponse").toString().equalsIgnoreCase(Serenity.sessionVariableCalled("GetCommentsByEmailIDResponse").toString())).as("Comments data matching failed").isTrue();
    }

    @Then("^this data is also part of postId comments$")
    public void assertDataToPostComments() {
        commentsAPI.getCommentsForPostId(Long.parseLong(Serenity.sessionVariableCalled("postId").toString()));
        assertThat(Serenity.sessionVariableCalled("GetCommentsByIDResponse").toString().contains(Serenity.sessionVariableCalled("emailId").toString())).as("Comments data matching failed").isTrue();
    }

    @Then("^this data is part of total list of comments$")
    public void assertDataToAllComments() {
        commentsAPI.getAllComments();
        assertThat(Serenity.sessionVariableCalled("GetAllCommentsResponse").toString().contains(Serenity.sessionVariableCalled("emailId").toString())).as("Comments data matching failed").isTrue();
    }

    @When("^He retrieve his comments using (.*)$")
    public void retrievePost(String postId) {
        commentsAPI.getCommentsForPostId(Long.parseLong(getTestProperties.getValue(postId)));
    }

    @When("^He retrieve his comments through nested route for (.*)$")
    public void retrievePostsNested(String postId) {
        commentsAPI.getCommentsForPostUsingNested(Long.parseLong(getTestProperties.getValue(postId)));
    }

    @When("^this data is of comments is same$")
    public void compareNestedRoutes() {
        assertThat(Serenity.sessionVariableCalled("GetCommentsByNestedResponse").toString().equalsIgnoreCase(Serenity.sessionVariableCalled("GetCommentsByIDResponse").toString())).as("Comments data for nested routes matching failed").isTrue();
    }

    @When("^he retrieves comments on (.*) posts$")
    public void retrieveComments(String user) {
        List<Post>totalPosts =new ArrayList<>();
        if("all".equalsIgnoreCase(user)){
            totalPosts= postsAPI.getAllPosts();
            Serenity.setSessionVariable("totalListOfCommentsForUser").to(commentsAPI.getAllComments());
        }else{
            long userId= usersAPI.getUserId(Serenity.sessionVariableCalled("userName"));
            totalPosts= postsAPI.getPostsByUserId(userId);
            Serenity.setSessionVariable("totalListOfCommentsForUser").to(commentsAPI.getAllCommentsForPosts(totalPosts));
        }


    }

    @Then("^Comments of (.*) user|users have valid email address$")
    public void verifyCommentsEmail(String userType) {
        List<Comment> totalComments =new ArrayList<>();
        if("all".equalsIgnoreCase(userType)){
            totalComments =Serenity.sessionVariableCalled("totalListOfCommentsForUser");
        }else{
            totalComments=Serenity.sessionVariableCalled("totalListOfCommentsForUser");
        }

        assertThat(totalComments.size()).as("total comments size is verified as greater than 0").isGreaterThan(0);
        assertThat(commentsAPI.verifyEmailsAreValidForComments(totalComments)).isTrue();
    }

}
