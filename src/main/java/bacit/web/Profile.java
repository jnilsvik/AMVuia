package bacit.web;

import bacit.web.a_models.BookingModel;
import bacit.web.utils.DBUtils;

import java.io.PrintWriter;
import java.sql.*;
import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// TODO: 11.11.2021 -joachim: id like to point out that this has a horribly unintuitive name
// by Dilan changed by Paul
@WebServlet(name = "Profile", value = "/profile")
public class Profile extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession(false);
            if(session == null){
                response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
                return;
            }
            String email = (String) session.getAttribute("email");
            List<BookingModel> bookings = getBookings(email, out);

            out.println(email);
            request.setAttribute("out", out);
            request.setAttribute("bookings", bookings);
            request.getRequestDispatcher("/Profile.jsp").forward(request,response);
        } catch (Exception e) {
            out.println(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
    }

    private List<BookingModel> getBookings(String email, PrintWriter out) throws SQLException {
        Connection db = DBUtils.getNoErrorConnection();
        PreparedStatement ps = db.prepareStatement("SELECT orderID, AMVUser.userID, Tool.toolID, startDate, endDate, returnDate FROM ((AMVUser INNER JOIN BOOKING ON AMVUser.userID = Booking.userID) INNER JOIN Tool on Booking.toolID = Tool.toolID) WHERE email = ?;");
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
                    0,
                    rs.getDate("startDate").toLocalDate(),
                    rs.getDate("endDate").toLocalDate(),
                    toolReturnDate));
        }
        db.close();
        return bookings;
    }
}





