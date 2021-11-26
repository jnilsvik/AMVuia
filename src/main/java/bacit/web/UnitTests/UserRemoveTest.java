package bacit.web.UnitTests;

import bacit.web.Admin.UserRemove;
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

public class UserRemoveTest {
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
        FakeUserRemove unitUnderTest = new FakeUserRemove();

        unitUnderTest.doGet(null,null);

        assertEquals("email1 email2 ", outputStreamCaptor.toString());
    }

    @Test
    void doPost() throws Exception{
        FakeUserRemove unitUnderTest = new FakeUserRemove();

        unitUnderTest.doPost(null,null);

        assertEquals("Self deletion ", outputStreamCaptor.toString());

        unitUnderTest.setEmailToDelete("email2");

        unitUnderTest.doPost(null, null);

        //Deletion should work so true was added
        assertEquals("Self deletion true email1 ", outputStreamCaptor.toString());

        unitUnderTest.doPost(null, null);

        //Deletion should not work so false gets added because email1 is not in the list anymore
        assertEquals("Self deletion true email1 false email1 ", outputStreamCaptor.toString());
    }
}

class FakeUserRemove extends UserRemove {

    List<UserModel> users = new LinkedList<>();
    String emailLoggedIn = "email1";
    String emailToDelete = "email1";

    FakeUserRemove(){
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

    public void setEmailToDelete(String emailToDelete){
        this.emailToDelete = emailToDelete;
    }

    @Override
    protected boolean deleteUser(String email){
        for(UserModel user: users){
            if(user.getEmail().equals(email)){
                users.remove(user);
                return true;
            }
        }
        return false;
    }

    @Override
    protected String getEmail(HttpServletRequest request){
        return emailLoggedIn;
    }

    @Override
    protected List<UserModel> getUsers(){
        return users;
    }

    @Override
    protected String getUser(HttpServletRequest request){
        return emailToDelete;
    }

    @Override
    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response){
        return true;
    }

    @Override
    protected void writeGetToJSP(List<UserModel> users, HttpServletRequest request, HttpServletResponse response){
        for(UserModel user : users){
            System.out.print(user.getEmail() + " ");
        }
    }

    @Override
    protected void writePostToJSP(List<UserModel> users, boolean success, HttpServletRequest request, HttpServletResponse response){
        System.out.print(success + " ");
        for(UserModel user : users){
            System.out.print(user.getEmail() + " ");
        }
    }

    @Override
    protected void printJspSelfDeletion(HttpServletRequest request, HttpServletResponse response){
        System.out.print("Self deletion ");
    }
}
