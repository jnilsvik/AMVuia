package bacit.web.profilePage;

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
            writeHeader(out, "Profile Page", email);
            writeBookings(out, bookings);
            //writeFooter(out);
        } catch (Exception e) {
            out.print(e);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

    }

    private List<BookingModel> getBookings(String email, PrintWriter out) throws SQLException {
        Connection db = DBUtils.getNoErrorConnection(out);
        PreparedStatement ps = db.prepareStatement("SELECT orderID, AMVUser.userID, Tool.toolID, startDate, endDate, returnDate FROM ((AMVUser INNER JOIN BOOKING ON AMVUser.userID = Booking.userID) INNER JOIN Tool on Booking.toolID = Tool.toolID) WHERE email = ?;");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        List<BookingModel> bookings = new LinkedList<>();
        while(rs.next()){
            int toolID = 0;
            try{
                toolID = rs.getInt("toolID");
            }catch (NullPointerException e){}
            LocalDate toolReturnDate = null;
            try{
                toolReturnDate = rs.getDate("toolReturnDate").toLocalDate();
            } catch (NullPointerException e){}

            bookings.add(new BookingModel(
                    rs.getInt("orderID"),
                    rs.getInt("userID"),
                    toolID,
                    rs.getDate("startDate").toLocalDate(),
                    rs.getDate("endDate").toLocalDate(),
                    toolReturnDate
            ));
        }
        return bookings;
    }

    private void writeHeader(PrintWriter out, String header, String email){
     //   PageElements.printSidebar(out, email);
       // PageElements.printHeader(header, out);
        //TODO sidebar and head
        out.print("<style>");
        out.print("table, th, td { border:1px solid black;}");
        out.print("</style>");
    }

    private void writeBookings(PrintWriter out, List<BookingModel> bookings){
        out.print("<h2>Your current bookings</h2>");
        out.print("<table style = 'width:80%'>");
        out.print("<tr>");
        out.print("<th>Order Number</th>");
        out.print("<th>Tool Name</th>");
        out.print("<th>Start Date</th>");
        out.print("<th>End Date</th>");
        out.print("<th>Total Price</th>");
        out.print("<th>Return Date</th>");
        out.print("</tr>");
        for(BookingModel booking : bookings){
            out.print("<tr>");
            out.print("<td>" + booking.getOrderID() + "</td> ");
            out.print("<td>" + booking.getToolName(out) + "</td> ");
            out.print("<td>" + booking.getStartDate() + "</td> ");
            out.print("<td>" + booking.getStartDate() + "</td> ");
            out.print("<td>" + booking.getTotalPrice(out) + "</td> ");
            out.print("<td>" + booking.getReturnDate() + "</td> ");
            out.print("</tr>");
        }
        out.print("</table>");
    }

    /*private void writeFooter(PrintWriter out){
        PageElements.printFooter(out);
    }
*/
    //TODO cleanup footer
}





