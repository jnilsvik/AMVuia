package bacit.web.Admin;

import bacit.web.utils.DBUtils;
import bacit.web.utils.PageAccess;

import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan
@WebServlet(name = "Payment", value = "/payment")
public class Payment extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkSession(request,response)) {
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
                PageAccess.reDirWOAdmin(request,response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String orderID = request.getParameter("orderID");
        setPaid(orderID);
        PageAccess.reDirFeedback(request,response,"Order was successfully marked as paid");
    }

    private void setPaid(String orderID) {
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
    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (PageAccess.isAdmin(request)){
            return true;
        }
        PageAccess.reDirWOUser(request,response);
        PageAccess.reDirWOAdmin(request,response);
        return false;
    }
}






