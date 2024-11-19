package Api;

import Pojo.CreateNotes;
import Pojo.Update_exsit_note;
import Utilities.Utility;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static BaseTest.basetest.requestSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Slf4j

public class NotesMethod {

//    public static CreateNotes createNotes;
   public static Update_exsit_note updateExsitNote;
//
//    //creates a new note with the given title, description, category and user id.
//    public static String create_Note(String token) throws IOException {
//
//        createNotes = Utility.readJsonFromFile("src/main/resources/testData.notes/note.json", CreateNotes.class);
//        log.info(String.format("User notes {}:%s", createNotes));
//
//        Response response = given(requestSpec)
//                .header("x-auth-token", token)
//                .log().uri() // Log the full URI
//                .log().headers() // Log headers for debugging
//                .body(createNotes)
//                .when()
//                .post("/notes")
//                .then()
//                .log().all().statusCode(200)
//                .body("message", equalTo("Note successfully created"))
//                .extract().response();
//        return response.jsonPath().getString("data.id");
//    }

    //Retrieve a list of notes for the authenticated user
    public static Response get_All_Notes(String token) throws IOException {
        return given(requestSpec)
                .header("x-auth-token", token)
                .log().uri() // Log the full URI
                .log().headers() // Log headers for debugging
                .when().get("/notes")
                .then().log().all().statusCode(200)
                .body("message", equalTo("Notes successfully retrieved"))
                .extract().response();
    }

    //Retrieve a note by its ID
    public static Response get_Anote_By_Id(String token, String myid) {
        return given(requestSpec)
                .header("x-auth-token", token)
                .pathParam("id", myid.trim())
                .log().uri() // Log the full URI
                .log().headers() // Log headers for debugging
                .when()
                .get("/notes/{id}")
                .then().log().all().statusCode(200)
                .body("message", equalTo("Note successfully retrieved"))
                .extract().response();

    }

    //Creates a new note with the given title, description, category and user id.
    public static Response update_Exsiting_note(String token, String myid) throws IOException {
        updateExsitNote = Utility.readJsonFromFile("src/main/resources/testData.notes/updateExsitnote.json", Update_exsit_note.class);
        log.info("update exsit note file :" + updateExsitNote);

        return given(requestSpec)
                .header("x-auth-token", token)
                .pathParam("id", myid.trim())
                .body(updateExsitNote)
                .when().put("/notes/{id}")
                .then().log().all().statusCode(200)
                .body("message", equalTo("Note successfully Updated"))
                .extract().response();
    }

    //Update the completed attribute of the note with the specified id
    public static boolean update_Completed_note(String token, String myid) {
        Response response = given(requestSpec)
                .header("x-auth-token", token)
                .pathParam("id", myid.trim())
                .when().patch("/notes/{id}")
                .then().log().all()
                .statusCode(200)
                .body("message", equalTo("Note successfully updated"))
                .extract().response();


        boolean updateCompleted = response.jsonPath().getBoolean("completed");
        if (updateCompleted == true) {
            updateCompleted = false;
        }
        if (updateCompleted == false) {
            updateCompleted = false;
        }
        return updateCompleted;
    }

    //Deletes a note with the specified ID.
    public static Response delete_Note_Byid(String token, String myid) {
        return given(requestSpec)
                .header("x-auth-token", token)
                .pathParam("id", myid.trim())
                .when().delete("/notes/{id}")
                .then().log().all()
                .statusCode(200)
                .body("message", equalTo("Note successfully deleted"))
                .extract().response();
    }

}
