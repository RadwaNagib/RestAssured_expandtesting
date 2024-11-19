package Tests;
import Api.UserMethod;
import BaseTest.basetest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

@Slf4j
public class User extends basetest {

    UserMethod userMethod;
    protected static String mytoken;

    @Before
    public void Register_Login_User() throws IOException {
        setup();
        registerNewUser();
        mytoken = Login();
    }

    @Test
    public void Retrieve_user_info() {

        userMethod.retrieve_User_Info(mytoken);
    }

    @Test
    public void Logout_User() {
        userMethod.logout_User(mytoken);
    }

    @Test
    public void delete_User() {
        userMethod.delete_User(mytoken);
    }

}