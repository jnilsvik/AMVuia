package bacit.web.AdminFunctions;

import bacit.web.utils.DBUtils;

import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan
@WebServlet(name = "Payment", value = "/payment")
public class Payment extends HttpServlet {

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
                String insertUserCommand = "SELECT AMVUser.userID, AMVUser.firstName, AMVUser.lastName, AMVUser.email, AMVUser.phoneNumber, Booking.orderID, Booking.startDate, Booking.endDate, Booking.returnDate, Booking.toolID, Booking.totalPrice FROM Booking INNER JOIN AMVUser ON Booking.userID = AMVUser.userID WHERE paid = false AND returnDate IS NOT NULL";
                PreparedStatement st1 = db.prepareStatement(insertUserCommand);
                st1.executeUpdate();
                ResultSet rs1 = st1.executeQuery();

                request.setAttribute("unpaid", rs1);
                request.getRequestDispatcher("/jspFiles/AdminFunctions/payment.jsp").forward(request,response);

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

            String orderID = request.getParameter("orderID");
            setPaid(orderID);

            String successfulLine = "<h3 style=\"text-align:center\">Order was successfully marked as paid</h3>" + "<br><br><br>"  + "<a href=\"payment\"> <span class=bigbutton> Go back  </span></a>";
            request.setAttribute("successfulLine", successfulLine);
            request.getRequestDispatcher("/jspFiles/AdminFunctions/successfulLine.jsp").forward(request,response);

    }

    public void setPaid(String orderID) {
        try {

        Connection db = DBUtils.getNoErrorConnection();
        String insertUserCommand = "UPDATE Booking SET paid = true WHERE orderID = ?";
        PreparedStatement statement = db.prepareStatement(insertUserCommand);
        statement.setString(1, orderID);
        statement.executeUpdate();

            statement.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






