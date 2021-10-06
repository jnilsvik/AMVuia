package bacit.web.bacit_web;

import bacit.web.bacit_models.BookingModel;
import bacit.web.bacit_models.UserModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DetailedToolServlet", value = "/history")
public class UserHistoryServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        String userID = req.getParameter("userID");
        try {
            LinkedList<BookingModel> user = getBookingFromDB(userID, out);
        } catch (SQLException e) {
            writeError(out, e);
        }

    }

    private LinkedList<BookingModel> getBookingFromDB(String userID, PrintWriter out) throws SQLException {
        Connection dbConnection = getConnection(out);
        String query = "SELECT * FROM Booking WHERE userID = ?;";
        PreparedStatement statement = dbConnection.prepareStatement(query);
        statement.setString(1, userID);
        ResultSet rs = statement.executeQuery();
        LinkedList<BookingModel> bookings = new LinkedList<>();
        while(rs.next()){
            bookings.add(new BookingModel(rs.getInt("bookingID"), rs.getInt("userID"), rs.getInt("toolID"), rs.getTimestamp("startDate").toLocalDateTime(), rs.getTimestamp("endDate").toLocalDateTime()));
        }
        return bookings;
    }

    private Connection getConnection(PrintWriter out){
        Connection dbConnection = null;

        try{
            dbConnection = DBUtils.getINSTANCE().getConnection(out);
        } catch (SQLException | ClassNotFoundException sqlException){
            out.println(sqlException);
        }
        return dbConnection;
    }

    private void writeError(PrintWriter out, Exception e){
        HeaderFooter.printHeader(out);
        out.println("<h2>An internal error happened</h2>");
        out.println("<p>"+e.getMessage()+"</p>");
        HeaderFooter.printFooter(out);
    }
}
