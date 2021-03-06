package bacit.web.Admin;

import bacit.web.utils.DBUtils;
import bacit.web.utils.PageAccess;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan
@WebServlet(name = "ToolReturnal", value = "/toolreturnal")
public class ToolReturnal extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkSession(request,response)) {
                Connection db = DBUtils.getNoErrorConnection();
                PreparedStatement st1 = db.prepareStatement(
                        "SELECT AMVUser.userID, AMVUser.firstName, AMVUser.lastName, AMVUser.email, AMVUser.phoneNumber, " +
                            "Booking.orderID, Booking.startDate, Booking.endDate, Booking.returnDate, Booking.toolID, Booking.totalPrice " +
                            "FROM Booking INNER JOIN AMVUser ON Booking.userID = AMVUser.userID WHERE returnDate IS NULL");
                st1.executeUpdate();
                ResultSet rs1 = st1.executeQuery();

                request.setAttribute("notReturned", rs1);
                request.getRequestDispatcher("/jspFiles/AdminFunctions/toolReturnal.jsp").forward(request,response);

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
        LocalDate returnDate = LocalDate.parse(request.getParameter("returndate"));
        String orderID = request.getParameter("orderID");
        setReturned(orderID, returnDate);

        PageAccess.reDirFeedback(request,response,"Tool was succesfully returned" );
    }

    private void setReturned(String orderID, LocalDate returnDate) {
        try {
            Connection db = DBUtils.getNoErrorConnection();
            PreparedStatement statement = db.prepareStatement(
                    "UPDATE Booking SET returnDate = ? WHERE orderID = ?");
            statement.setObject(1, returnDate);
            statement.setString(2, orderID);
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






