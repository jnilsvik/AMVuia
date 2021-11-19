package bacit.web.AdminFunctions;

import bacit.web.utils.DBUtils;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan
@WebServlet(name = "ToolReturnal", value = "/toolreturnal")
public class ToolReturnal extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        try {
            HttpSession session=request.getSession(false);
            String email = null;
            if(session != null){
                email = (String) session.getAttribute("email");
            }
            if(email == null){
                response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
                return;
            }


            if (SessionCheck.isAdmin(email)) {

                Connection db = DBUtils.getNoErrorConnection();
                String insertUserCommand = "SELECT AMVUser.userID, AMVUser.firstName, AMVUser.lastName, AMVUser.email, AMVUser.phoneNumber, Booking.orderID, Booking.startDate, Booking.endDate, Booking.returnDate, Booking.toolID, Booking.totalPrice FROM Booking INNER JOIN AMVUser ON Booking.userID = AMVUser.userID WHERE returnDate IS NULL";
                PreparedStatement st1 = db.prepareStatement(insertUserCommand);
                st1.executeUpdate();
                ResultSet rs1 = st1.executeQuery();

                request.setAttribute("notReturned", rs1);
                request.getRequestDispatcher("/jspFiles/AdminFunctions/toolReturnal.jsp").forward(request,response);

                rs1.close();
                st1.close();
                db.close();

            } else {
                request.getRequestDispatcher("/jspFiles/AdminFunctions/noAdminAccount.jsp").forward(request,response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        LocalDate returnDate = LocalDate.parse(request.getParameter("returndate"));
        String orderID = request.getParameter("orderID");
        setReturned(orderID, returnDate);

        String successfulLine = "<h3 style=\"text-align:center\">Tool was succesfully returned!</h3>" + "<br><br><br>"  + "<a href=\"toolreturnal\"> <span class=bigbutton> Go back  </span></a>";
        request.setAttribute("successfulLine", successfulLine);
        request.getRequestDispatcher("/jspFiles/AdminFunctions/successfulLine.jsp").forward(request,response);

    }

    public void setReturned(String orderID, LocalDate returnDate) {
        try {
            Connection db = DBUtils.getNoErrorConnection();
            String insertUserCommand = "UPDATE Booking SET returnDate = ? WHERE orderID = ?";
            PreparedStatement statement = db.prepareStatement(insertUserCommand);
            statement.setObject(1, returnDate);
            statement.setString(2, orderID);
            statement.executeUpdate();

            statement.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






