package com.mobiquity.apis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.json.model.comments.Comment;
import com.json.model.posts.Post;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import org.json.JSONArray;
import utils.EmailValidatorClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static net.serenitybdd.rest.SerenityRest.given;
import static org.assertj.core.api.Assertions.assertThat;

public class CommentsAPI extends BasePage{

    @Step
    public List<Comment> getCommentsForPostId(long postId){
        Response response = given()
                .header("Accept","application/json")
                .when().log().all().get("/comments?postId="+postId);
        String responseAsString = response.getBody().asString();
        Serenity.setSessionVariable("GetCommentsByIDStatusCode").to(response.getStatusCode());
        JSONArray responseAsObject = new JSONArray(responseAsString);
        Serenity.setSessionVariable("GetCommentsByIDResponse").to(responseAsObject);
        return getCommentsAsList(responseAsString);
    }

    @Step
    public List<Comment> getCommentsForPostUsingNested(long postId){
        Response response = given()
                .header("Accept","application/json")
                .when().log().all().get("/posts/"+postId+"/comments");
        String responseAsString = response.getBody().asString();
        Serenity.setSessionVariable("GetCommentsNestedStatusCode").to(response.getStatusCode());
        JSONArray responseAsObject = new JSONArray(responseAsString);
        Serenity.setSessionVariable("GetCommentsByNestedResponse").to(responseAsObject);
        return getCommentsAsList(responseAsString);
    }

    @Step
    public List<Comment> getCommentsForCommentId(long commentId){
        Response response = given()
                .header("Accept","application/json")
                .when().log().all().get("/comments?id="+commentId);
        String responseAsString = response.getBody().asString();
        Serenity.setSessionVariable("GetCommentsByCommentIDStatusCode").to(response.getStatusCode());
        JSONArray responseAsObject = new JSONArray(responseAsString);
        Serenity.setSessionVariable("GetCommentsByCommentIDResponse").to(responseAsObject);
        return getCommentsAsList(responseAsString);
    }

    @Step
    public List<Comment> getCommentsForEmailId(String emailId){
        Response response = given()
                .header("Accept","application/json")
                .when().log().all().get("/comments?email="+emailId);
        String responseAsString = response.getBody().asString();
        Serenity.setSessionVariable("GetCommentsByEmailIDStatusCode").to(response.getStatusCode());
        JSONArray responseAsObject = new JSONArray(responseAsString);
        Serenity.setSessionVariable("GetCommentsByEmailIDResponse").to(responseAsObject);
        return getCommentsAsList(responseAsString);
    }

    @Step
    public List<Comment> getAllComments(){
        Response response = given()
                .header("Accept","application/json")
                .when().log().all().get("/comments");
        String responseAsString = response.getBody().asString();
        Serenity.setSessionVariable("GetAllCommentsStatusCode").to(response.getStatusCode());
        JSONArray responseAsObject = new JSONArray(responseAsString);
        Serenity.setSessionVariable("GetAllCommentsResponse").to(responseAsObject);
        return getCommentsAsList(responseAsString);
    }


    private List<Comment> getCommentsAsList(String response){
        try {
            return mapper.readValue(response, new TypeReference<List<Comment>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Comments are not as per the Contract" + e.getMessage());
        }
    }

    public List<Comment> getAllCommentsForPosts(List<Post> totalPosts){
        List<Comment> totalCommentsOfUserPosts = new ArrayList<>();
        List<Comment> overAllComments = getAllComments();
        for (Post post : totalPosts) {
            List<Comment> comments = getCommentsForPostId(post.getId());
            totalCommentsOfUserPosts.addAll(comments);
        }
        return totalCommentsOfUserPosts;
    }



    public List<Comment> getCommentsForPost(Post post,List<Comment> comments){
        return comments.stream().filter(comment -> comment.getPostId() == post.getId())
                .collect(Collectors.toList());
    }

    public boolean verifyEmailsAreValidForComments(List<Comment> totalComments){
        for (Comment comment : totalComments) {
            assertThat(EmailValidatorClass.getValidity(comment.getEmail())).as("Email is Verified as Valid").isTrue();
        }
        return true;
    }


}
