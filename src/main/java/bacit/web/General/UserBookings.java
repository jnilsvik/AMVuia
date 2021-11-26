package bacit.web.General;

import bacit.web.Modules.BookingModel;
import bacit.web.utils.DBUtils;
import bacit.web.utils.PageAccess;

import java.sql.*;
import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "UserBookings", value = "/profile")
public class UserBookings extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkSession(request,response)){
                String email = getEmailFromSession(request,response);
                List<BookingModel> bookings = getBookings(email);
                printGetToJSP(bookings, request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String getEmailFromSession(HttpServletRequest request, HttpServletResponse response){
        return PageAccess.getEmail(request);
    }

    protected List<BookingModel> getBookings(String email) throws SQLException {
        Connection db = DBUtils.getNoErrorConnection();
        PreparedStatement ps = db.prepareStatement("SELECT orderID, AMVUser.userID, Tool.toolID, startDate, endDate,totalPrice, returnDate FROM ((AMVUser INNER JOIN BOOKING ON AMVUser.userID = Booking.userID) INNER JOIN Tool on Booking.toolID = Tool.toolID) WHERE email = ?;");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        List<BookingModel> bookings = new LinkedList<>();
        while(rs.next()){
            int toolID = 0;
            try{
                toolID = rs.getInt("toolID");
            }catch (NullPointerException e){e.printStackTrace();}
            LocalDate toolReturnDate = null;
            try{
                toolReturnDate = rs.getDate("returnDate").toLocalDate();
            } catch (NullPointerException e){e.printStackTrace();}

            bookings.add(new BookingModel(
                    rs.getInt("orderID"),
                    rs.getInt("userID"),
                    toolID,
                    rs.getInt("totalPrice"),
                    rs.getDate("startDate").toLocalDate(),
                    rs.getDate("endDate").toLocalDate(),
                    toolReturnDate));
        }
        rs.close();
        ps.close();
        db.close();
        return bookings;
    }

    protected void printGetToJSP(List<BookingModel> bookings, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("/jspFiles/Profile/profile.jsp").forward(request,response);
    }
    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (PageAccess.isUser(request)){
            return true;
        }
        PageAccess.reDirWOUser(request,response);
        return false;
    }
}





