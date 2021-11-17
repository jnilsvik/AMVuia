package bacit.web.WIP;

import bacit.web.Modules.BookingModel;
import bacit.web.utils.DBUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;

@WebServlet(name = "b", value = "/b")
public class Tool_Booking extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int duration = Integer.parseInt(req.getParameter("days"));
        LocalDate reqStartDate = LocalDate.parse(req.getParameter("dateStart"));
        LocalDate reqEndDate = reqStartDate.plusDays(duration);

        try {
            BookingModel bModel = new BookingModel(
                    0,
                    Integer.parseInt(String.valueOf(req.getAttribute("uID"))),
                    Integer.parseInt(req.getParameter("toolID")),
                    0,
                    reqStartDate,
                    reqEndDate,
                    null);

            if(CompareAndValidateBooking(reqStartDate, reqEndDate, GetBookedDates(req.getParameter("toolID")))){
                InsertBooking(bModel);
                req.setAttribute("bmodel", bModel);
                req.getRequestDispatcher("/jspFiles/PageElements/login.jsp").forward(req,resp);
            } else {
                // TODO: 31.10.2021 figure out what to w/this
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static LinkedList<BookingModel> GetBookedDates(String toolID) throws SQLException{
        Connection dbConnection = DBUtils.getNoErrorConnection();
        PreparedStatement statement = dbConnection.prepareStatement(
                "select * from Booking where toolID = ?");
        statement.setString(1, toolID);
        ResultSet rs = statement.executeQuery();

        LinkedList<BookingModel> model = null;
        while (rs.next()){
            model.add(new BookingModel(
                    rs.getInt("orderID"),
                    rs.getInt("userID"),
                    rs.getInt("toolID"),
                    0,
                    rs.getTimestamp("startDate").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("endDate").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("returnDate").toLocalDateTime().toLocalDate()));
        }
        return model;
    }

    boolean CompareAndValidateBooking(LocalDate rSD, LocalDate rED, LinkedList<BookingModel> bookings){
        boolean ava = true;
        for (BookingModel b : bookings){
            // ! why? (removes the relevance of endDate) & checks if req_endDate crashes
            //if r-start is after r-end = ERROR.if r-end is b4 next start, b-start is irrelevant,if r-start is after b-end,  b-end is irrelevant
            if (!rSD.isAfter(rED) && rED.isBefore(b.getStartDate()) && ((rSD.isAfter(b.getEndDate()) || rSD.isAfter(b.getReturnDate())))){
                continue;
            } else ava=false;
        }
        return ava;
    }

    void InsertBooking(BookingModel model) throws SQLException{
        Connection dbc = DBUtils.getNoErrorConnection();
        PreparedStatement statement = dbc.prepareStatement(
                "insert into Booking(userID, toolID, startDate, endDate, totalPrice) values (?,?,?,?,?)");
        statement.setInt(1, model.getUserID());
        statement.setInt(1, model.getToolID());
        statement.setObject(1, model.getStartDate());
        statement.setObject(1, model.getEndDate());
        statement.setDouble(1, model.getTotalPrice());
        statement.executeUpdate();
    }
}
