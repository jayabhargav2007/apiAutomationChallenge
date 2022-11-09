package com.mobiquity.apis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.json.model.users.User;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import org.json.JSONArray;
import java.io.IOException;
import java.util.List;

import static net.serenitybdd.rest.SerenityRest.given;

public class UsersAPI extends BasePage {

    @Step
    public List<User> getAllUsers(){
        Response response = given()
                .header("Accept","application/json")
                .when().log().all().get("/users");
        String responseAsString = response.getBody().asString();
        Serenity.setSessionVariable("GetAllUsersStatusCode").to(response.getStatusCode());
        JSONArray responseAsObject = new JSONArray(responseAsString);
        Serenity.setSessionVariable("GetAllUsersResponse").to(responseAsObject);
        return getUsersAsList(responseAsString);
    }

    @Step
    public List<User> getUserByUsername(String username){
        Response response = given()
                .header("Accept","application/json")
                .when().log().all().get("/users?username="+username);
        String responseAsString = response.getBody().asString();
        Serenity.setSessionVariable("GetUserByUserNameStatusCode").to(response.getStatusCode());
        JSONArray responseAsObject = new JSONArray(responseAsString);
        Serenity.setSessionVariable("GetUserByUserNameResponse").to(responseAsObject);
        return getUsersAsList(responseAsString);
    }

    @Step
    public List<User> getUserById(Long id){
        Response response = given()
                .header("Accept","application/json")
                .when().log().all().get("/users?id="+id);
        String responseAsString = response.getBody().asString();
        Serenity.setSessionVariable("GetUserByIdStatusCode").to(response.getStatusCode());
        JSONArray responseAsObject = new JSONArray(responseAsString);
        Serenity.setSessionVariable("GetUserByIdResponse").to(responseAsObject);
        return getUsersAsList(responseAsString);
    }

    @Step
    public List<User> getUserByEmail(String email){
        Response response = given()
                .header("Accept","application/json")
                .when().log().all().get("/users?email="+email);
        String responseAsString = response.getBody().asString();
        Serenity.setSessionVariable("GetUserByEmailStatusCode").to(response.getStatusCode());
        JSONArray responseAsObject = new JSONArray(responseAsString);
        Serenity.setSessionVariable("GetUserByEmailResponse").to(responseAsObject);
        return getUsersAsList(responseAsString);
    }

    @Step
    public long getUserId(String userName){
        List<User> allUsers = getUserByUsername(userName);
        return allUsers.get(0).getId();
    }

    private List<User> getUsersAsList(String response){
        try {
            return mapper.readValue(response, new TypeReference<List<User>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Users are not as per the Contract" + e.getMessage());
        }
    }


}
