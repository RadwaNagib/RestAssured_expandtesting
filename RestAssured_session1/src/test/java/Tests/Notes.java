package Tests;

import Api.NotesMethod;
import BaseTest.basetest;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import org.testng.Assert;
@Slf4j

public class Notes extends basetest {
    NotesMethod notesMethod;
    public static String token;
    public static String myid;

    @BeforeClass
    public static void Register_Login_User() throws IOException {
        setup();
        basetest.registerNewUser();
        token = basetest.Login();
        System.out.println("token:" + token);
        myid=create_Note(token);
    }

    @Test
    public void Get_All_Notes() throws IOException {
        notesMethod.get_All_Notes(token);
    }

    @Test
    public void Get_Anote_Byid() {

        notesMethod.get_Anote_By_Id(token, myid);
    }

    @Test
    public void Update_Exsiting_Note() throws IOException {
        notesMethod.update_Exsiting_note(token, myid);
    }

    @Test
    public void Update_Completedstatus_Ofnote() {
        notesMethod.update_Completed_note(token, myid);
    }

    @Test
    public void Delete_Note() {
        notesMethod.delete_Note_Byid(token, myid);

    }

}
