package BaseTest;
import Pojo.CreateNotes;
import Pojo.Login;
import Pojo.Update_exsit_note;
import Pojo.UserRegister;
import Utilities.Utility;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.time.Instant;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
@Slf4j

public class basetest {

    public static RequestSpecification requestSpec;
    public static UserRegister userRegister;
    public static CreateNotes createNotes;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://practice.expandtesting.com/notes/api";
        requestSpec = new RequestSpecBuilder()
                .setContentType("application/json").setAccept("application/json")
                .build();
    }

    //create new user
    public static Response registerNewUser() throws IOException {

        userRegister = Utility.readJsonFromFile("src/main/resources/testData.user/user.json", UserRegister.class);

        // user Randomize Email
        userRegister.setEmail("test" + Instant.now().toEpochMilli() + "@gmail.com");
        log.info(String.format("User Data {}:%s", userRegister));

        final String name_json = userRegister.getName();
        final String email_json = userRegister.getEmail();

        return given(requestSpec)
                .body(userRegister)
                .when()
                .post("/users/register")
                .then().log().body()
                .statusCode(201).body("data.name", equalTo(name_json))
                .body("data.email", equalTo(email_json)).body("message", equalTo("User account created successfully"))
                .extract().response();


    }

//login user
    public static String Login() throws IOException {

        Login login = new Login();
        login.setPassword(userRegister.getPassword());
        login.setEmail(userRegister.getEmail());

        Response response= given(requestSpec)
                .body(login)
                .when().post("/users/login")
                .then().statusCode(200).
                log().body()
                .body("message", equalTo("Login successful")).extract().response();


        // Return token if login was successful
        if (response.statusCode() == 200) {
            return response.jsonPath().getString("data.token");
        } else {
            throw new RuntimeException("Login failed: " + response.getBody().asString());
        }
    }




    //creates a new note with the given title, description, category and user id.
    public static String create_Note(String token) throws IOException {

        createNotes = Utility.readJsonFromFile("src/main/resources/testData.notes/note.json", CreateNotes.class);
        log.info(String.format("User notes {}:%s", createNotes));

        Response response = given(requestSpec)
                .header("x-auth-token", token)
                .log().uri() // Log the full URI
                .log().headers() // Log headers for debugging
                .body(createNotes)
                .when()
                .post("/notes")
                .then()
                .log().all().statusCode(200)
                .body("message", equalTo("Note successfully created"))
                .extract().response();
        return response.jsonPath().getString("data.id");
    }

}
