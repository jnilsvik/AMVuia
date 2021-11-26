package bacit.web.UnitTests;

import bacit.web.Modules.BookingModel;
import bacit.web.General.UserBookings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserBookingsTest {

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
        FakeUserBookings unitUnderTest = new FakeUserBookings();
        //Act
        unitUnderTest.doGet(null,null);

        assertEquals("1 1 1 2 ", outputStreamCaptor.toString());

        unitUnderTest.setUserID(2);

        unitUnderTest.doGet(null,null);

        assertEquals("1 1 1 2 2 3 ", outputStreamCaptor.toString());

        unitUnderTest.setUserID(3);

        unitUnderTest.doGet(null,null);

        assertEquals("1 1 1 2 2 3 ", outputStreamCaptor.toString());
    }
}

class FakeUserBookings extends UserBookings {

    private List<BookingModel> bookings = new LinkedList<>();
    private int userID = 1;

    FakeUserBookings(){
        setBookings();
    }

    private void setBookings(){
        bookings.add(
                new BookingModel(1, 1, 1, 0, null, null, null)
        );
        bookings.add(
                new BookingModel(2, 1, 1, 0, null, null, null)
        );
        bookings.add(
                new BookingModel(3, 2, 1, 0, null, null, null)
        );
    }

    void setUserID(int userID){
        this.userID = userID;
    }

    @Override
    protected List<BookingModel> getBookings(String email){
        List<BookingModel> filteredBookings = bookings.stream().filter(b -> b.getUserID() == userID).collect(Collectors.toList());
        return filteredBookings;
    }

    @Override
    protected String getEmailFromSession(HttpServletRequest request, HttpServletResponse response){
        return "";
    }

    @Override
    protected void printGetToJSP(List<BookingModel> bookings, HttpServletRequest request, HttpServletResponse response){
        for(BookingModel booking : bookings){
            System.out.print(booking.getUserID()+" " + booking.getOrderID() + " ");
        }
    }

    @Override
    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response){
        return true;
    }
}
