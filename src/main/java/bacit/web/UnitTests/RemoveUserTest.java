package bacit.web.UnitTests;

import bacit.web.AdminFunctions.RemoveUser;
import bacit.web.Modules.UserModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RemoveUserTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void doGet() throws Exception{
        //Arrange
        FakeRemoveUser unitUnderTest = new FakeRemoveUser();
        //Act
        unitUnderTest.doGet(null,null);

        assertEquals("email1 email2", outputStreamCaptor.toString()
                .trim());

    }

    @Test
    void doPost() throws Exception{
        //Arrange
        FakeRemoveUser unitUnderTest = new FakeRemoveUser();
        //Act
        unitUnderTest.doPost(null,null);

        assertEquals("true email2", outputStreamCaptor.toString()
                .trim());

        unitUnderTest.doPost(null, null);

        assertEquals("true email2 false email2", outputStreamCaptor.toString()
                .trim());
    }
}

class FakeRemoveUser extends RemoveUser {

    List<UserModel> users = new LinkedList<>();

    FakeRemoveUser(){
        setUser();
    }

    private void setUser(){
        users.add(
                new UserModel(
                        1, "firstName1", "secondName1", "pw", "+123456", false , false, "email1"
                )
        );
        users.add(
                new UserModel(
                        2, "firstName2", "secondName2", "pw", "+123456", false , false, "email2"
                )
        );
    }

    @Override
    protected boolean deleteUser(String email){
        for(UserModel user: users){
            if(user.getEmail() == email){
                users.remove(user);
                return true;
            }
        }
        return false;
    }

    @Override
    protected List<UserModel> getUsers(){
        return users;
    }

    @Override
    protected String getUser(HttpServletRequest request){
        return "email1";
    }

    @Override
    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response){
        return true;
    }

    @Override
    protected void writeGetToJSP(List<UserModel> users, HttpServletRequest request, HttpServletResponse response){
        System.out.flush();
        for(UserModel user : users){
            System.out.print(user.getEmail() + " ");
        }
    }

    @Override
    protected void writePostToJSP(List<UserModel> users, boolean success, HttpServletRequest request, HttpServletResponse response){
        System.out.flush();
        System.out.print(success + " ");
        for(UserModel user : users){
            System.out.print(user.getEmail() + " ");
        }
    }
}
