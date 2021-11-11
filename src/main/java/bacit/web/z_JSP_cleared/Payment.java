package bacit.web.z_JSP_cleared;

import bacit.web.AdminAccess;
import bacit.web.utils.DBUtils;

import java.io.PrintWriter;
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
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("email");

            if (AdminAccess.accessRights(email)) {

                Connection db = DBUtils.getNoErrorConnection(out);
                String insertUserCommand = "SELECT * FROM Booking INNER JOIN AMVUser ON Booking.userID = AMVUser.userID WHERE paid = false AND returnDate IS NOT NULL";
                PreparedStatement st1 = db.prepareStatement(insertUserCommand);
                st1.executeUpdate();
                ResultSet rs1 = st1.executeQuery();

                request.setAttribute("unpaid", rs1);
                request.getRequestDispatcher("payment.jsp").forward(request,response);

            } else {
                out.print("<h1> Sorry, you don't have access to this page");
            }

        } catch (Exception e) {
            out.print("error");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            String orderID = request.getParameter("orderID");
            setPaid(out, orderID);

            String successfulLine = "Order was successfully marked as paid";
            request.setAttribute("successfulLine", successfulLine);
            request.getRequestDispatcher("successfulLine.jsp").forward(request,response);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPaid(PrintWriter out, String orderID) {
        try {

        Connection db = DBUtils.getNoErrorConnection(out);
        String insertUserCommand = "UPDATE Booking SET paid = true WHERE orderID = ?";
        PreparedStatement statement = db.prepareStatement(insertUserCommand);
        statement.setString(1, orderID);
        statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






