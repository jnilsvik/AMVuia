package bacit.web.AdminFunctions;

import bacit.web.utils.DBUtils;

import java.io.PrintWriter;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
            if(session == null){
                response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
                return;
            }
            String email = (String) session.getAttribute("email");

            if (true) {

                Connection db = DBUtils.getNoErrorConnection();
                String insertUserCommand = "SELECT * FROM Booking INNER JOIN AMVUser ON Booking.userID = AMVUser.userID WHERE paid = false AND returnDate IS NOT NULL";
                PreparedStatement st1 = db.prepareStatement(insertUserCommand);
                st1.executeUpdate();
                ResultSet rs1 = st1.executeQuery();

                request.setAttribute("unpaid", rs1);
                request.getRequestDispatcher("/jspFiles/AdminFunctions/payment.jsp").forward(request,response);

            } else {
                request.getRequestDispatcher("/jspFiles/AdminFunctions/noAdminAccount.jsp").forward(request,response);
            }

        } catch (Exception e) {
            out.print("error");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


            String orderID = request.getParameter("orderID");
            setPaid(out, orderID);

            String successfulLine = "Order was successfully marked as paid";
            request.setAttribute("successfulLine", successfulLine);
            request.getRequestDispatcher("/jspFiles/AdminFunctions/successfulLine.jsp").forward(request,response);

    }

    public void setPaid(PrintWriter out, String orderID) {
        try {

        Connection db = DBUtils.getNoErrorConnection();
        String insertUserCommand = "UPDATE Booking SET paid = true WHERE orderID = ?";
        PreparedStatement statement = db.prepareStatement(insertUserCommand);
        statement.setString(1, orderID);
        statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






