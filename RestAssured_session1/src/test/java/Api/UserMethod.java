package Api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static BaseTest.basetest.requestSpec;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Slf4j

public class UserMethod {


    // Returns the profile information for the logged-in current user.
    public static Response retrieve_User_Info(String My_token) {
        return given(requestSpec)
                .header("x-auth-token", My_token)
                .contentType(ContentType.JSON)
                .when().get("/users/profile").then().statusCode(200)
                .log().all().body("message", equalTo("Profile successful"))
                .extract().response();
    }

    //logout user
    public static Response logout_User(String My_token) {
        return given(requestSpec).header("x-auth-token", My_token)
                .contentType(ContentType.JSON)
                .when().delete("/users/logout")
                .then().log().all().statusCode(200)
                .body("message", equalTo("User has been successfully logged out"))
                .extract().response();

    }

    //delete User from database
    public static Response delete_User(String My_token) {
        return given(requestSpec)
                .header("x-auth-token", My_token)
                .contentType(ContentType.JSON)
                .when().delete("/users/delete-account").then()
                .log().all().statusCode(200)
                .body("message", equalTo("Account successfully deleted"))
                .extract().response();
    }


}
