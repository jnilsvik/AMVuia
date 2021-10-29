package bacit.web.bacit_web;

import bacit.web.bacit_database.DBUtils;
import bacit.web.bacit_headerFooter.PageElements;
import bacit.web.bacit_models.BookingModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UserHistoryServlet", value = "/history")
public class UserHistoryServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        String userID = req.getParameter("userID");
        try {
            LinkedList<BookingModel> bookings = getBookingFromDB(userID, out);
            if(bookings.size() == 0) writeNoBookings(out);
            else {
                for(BookingModel booking: bookings) writeBooking(booking, out);
            }
        } catch (SQLException e) {
            writeError(out, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private LinkedList<BookingModel> getBookingFromDB(String userID, PrintWriter out) throws SQLException {
        Connection dbConnection = DBUtils.getNoErrorConnection(out);
        String query = "SELECT * FROM Booking WHERE userID = ?;";
        PreparedStatement statement = dbConnection.prepareStatement(query);
        statement.setString(1, userID);
        ResultSet rs = statement.executeQuery();
        LinkedList<BookingModel> bookings = new LinkedList<>();
        while(rs.next()){
            bookings.add(new BookingModel(rs.getInt("bookingID"), rs.getInt("userID"), rs.getInt("toolID"), rs.getTimestamp("startDate").toLocalDateTime().toLocalDate(), rs.getTimestamp("endDate").toLocalDateTime().toLocalDate()));
        }
        return bookings;
    }

    private void writeError(PrintWriter out, Exception e){
        PageElements.printHeader("UserHistory", out);
        out.println("<h2>An internal error happened</h2>");
        out.println("<p>"+e.getMessage()+"</p>");
        PageElements.printFooter(out);
    }

    private void writeBooking(BookingModel booking, PrintWriter out){
        PageElements.printHeader("UserHistory",out);
        out.println("<h3>Item: "+booking.getToolName(out)+"</h3>");
        out.println("<p>From: "+writeDate(booking.getStartDate())+"</p>");
        out.println("<p>To: "+writeDate(booking.getEndDate())+"</p>");
        out.println("<p>For the price of: "+booking.getTotalPrice(out)+"NOK</p>");
        PageElements.printFooter(out);
    }

    private void writeNoBookings(PrintWriter out){
        PageElements.printHeader("UserHistory",out);
        out.println("<h3>This user has not made any bookings</h3>");
        PageElements.printFooter(out);
    }

    private String writeDate(LocalDate date){
        return date.getDayOfMonth() + "." + date.getMonth();
    }
}
