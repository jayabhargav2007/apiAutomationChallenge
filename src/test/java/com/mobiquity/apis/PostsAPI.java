package com.mobiquity.apis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.json.model.posts.Post;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import org.json.JSONArray;
import java.io.IOException;
import java.util.List;

import static net.serenitybdd.rest.SerenityRest.given;

public class PostsAPI extends BasePage {

    @Step
    public List<Post> getPostsById(long postId){
        Response response = given()
                .header("Accept","application/json")
                .when().log().all().get("/posts?id="+postId);
        String responseAsString = response.getBody().asString();
        Serenity.setSessionVariable("GetPostsByIDStatusCode").to(response.getStatusCode());
        JSONArray responseAsObject = new JSONArray(responseAsString);
        Serenity.setSessionVariable("GetPostsByIDResponse").to(responseAsObject);
        return getPostsAsList(responseAsString);
    }

    @Step
    public List<Post> getPostsByUserId(long userId){
        Response response = given()
                .header("Accept","application/json")
                .when().log().all().get("/posts?userId="+userId);
        String responseAsString = response.getBody().asString();
        Serenity.setSessionVariable("GetPostsByUserIDStatusCode").to(response.getStatusCode());
        JSONArray responseAsObject = new JSONArray(responseAsString);
        Serenity.setSessionVariable("GetPostsByUserIDResponse").to(responseAsObject);
        return getPostsAsList(responseAsString);
    }

    @Step
    public List<Post> getPostsByNested(long userId){
        Response response = given()
                .header("Accept","application/json")
                .when().log().all().get("/users/"+userId+"/posts");
        String responseAsString = response.getBody().asString();
        Serenity.setSessionVariable("GetPostsByNestedStatusCode").to(response.getStatusCode());
        JSONArray responseAsObject = new JSONArray(responseAsString);
        Serenity.setSessionVariable("GetPostsByNestedResponse").to(responseAsObject);
        return getPostsAsList(responseAsString);
    }

    @Step
    public List<Post> getAllPosts(){
        Response response = given()
                .header("Accept","application/json")
                .when().log().all().get("/posts");
        String responseAsString = response.getBody().asString();
        Serenity.setSessionVariable("GetAllPostsStatusCode").to(response.getStatusCode());
        JSONArray responseAsObject = new JSONArray(responseAsString);
        Serenity.setSessionVariable("GetAllPostsResponse").to(responseAsObject);
        return getPostsAsList(responseAsString);
    }


    private List<Post> getPostsAsList(String response){
        try {
            return mapper.readValue(response, new TypeReference<List<Post>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Posts are not as per the Contract" + e.getMessage());
        }
    }

}
