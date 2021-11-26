package bacit.web.UnitTests;

import bacit.web.Modules.BookingModel;
import bacit.web.Modules.ToolModel;
import bacit.web.ToolBooking.ToolBookingServlet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToolBookingTest {
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
    public void doGet() throws IOException {
        ToolBookingFake unitUnderTest = new ToolBookingFake();

        unitUnderTest.doPost(null,null);

        assertEquals("2 2 ", outputStreamCaptor.toString());

        unitUnderTest.setToolID(1);

        unitUnderTest.doPost(null, null);

        assertEquals("2 2 error ", outputStreamCaptor.toString());

        unitUnderTest.setToolID(3);

        unitUnderTest.setUserID(1);

        unitUnderTest.doPost(null, null);

        assertEquals("2 2 error error ", outputStreamCaptor.toString());

        unitUnderTest.setUserID(3);

        unitUnderTest.doPost(null, null);

        assertEquals("2 2 error error 3 3 ", outputStreamCaptor.toString());
    }
}

class ToolBookingFake extends ToolBookingServlet{

    private int userID;
    private int toolID;

    ToolBookingFake(){
        userID = 2;
        toolID = 2;
    }

    public void setToolID(int toolID) {
        this.toolID = toolID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    protected void setContentType(HttpServletResponse response){
    }

    @Override
    protected boolean hasCertificate(int userID, int toolCertificateID){
        if(userID == 1) return false;
        return true;
    }

    @Override
    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response){
        return true;
    }

    @Override
    protected boolean dateBookedTaken(BookingModel booking){
        if(toolID == 1) return true;
        return false;
    }

    @Override
    protected void registerBooking(BookingModel booking){

    }

    @Override
    protected ToolModel getTool(int toolID){
        return new ToolModel(
                this.toolID,
                "Tool1",
                "Cat1",
                false,
                0,
                3,
                1,
                "Description",
                "");
    }

    @Override
    protected BookingModel getBooking(ToolModel tool, HttpServletRequest request){
        return new BookingModel(2, userID, tool.getToolID(), 2, null, null, null);
    }

    @Override
    protected boolean isUnionMember(int userID){
        return false;
    }

    @Override
    protected int getUserID(String email){
        return userID;
    }

    @Override
    protected int getToolID(HttpServletRequest request){
        return toolID;
    }

    @Override
    protected void printFeedBackJSP(String message, HttpServletRequest request, HttpServletResponse response){
        System.out.print("error ");
    }

    @Override
    protected void printSuccessJSP(BookingModel booking, HttpServletRequest request, HttpServletResponse response){
        System.out.print(booking.getUserID() + " " + booking.getToolID() + " ");
    }
}
