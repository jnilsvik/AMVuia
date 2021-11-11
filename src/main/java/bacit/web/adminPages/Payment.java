package bacit.web.adminPages;

import bacit.web.utils.DBUtils;

import java.io.PrintWriter;
import java.io.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            String email = (String) session.getAttribute("email");

            if (AdminAccess.accessRights(email)) {
                out.print("<html>");
                out.print("<head>");
                out.print("<title>Tool maintenance</title>");
                out.print("</head>");
                out.print("<style>");
                out.print("table, th, td { border:1px solid black;}");
                out.print("</style>");
                out.print("<body>");

                out.print("<table style = 'width:100%'>");
                out.print("<tr>");
                out.print("<th>User ID</th>");
                out.print("<th>First Name</th>");
                out.print("<th>Last Name</th>");
                out.print("<th>Email</th>");
                out.print("<th>Phone Number</th>");
                out.print("<th>Order ID</th>");
                out.print("<th>Start Date</th>");
                out.print("<th>End Date</th>");
                out.print("<th>Return Date</th>");
                out.print("<th>Tool ID</th>");
                out.print("<th>Total Price</th>");
                out.print("</tr>");

                Connection db = DBUtils.getNoErrorConnection(out);
                String insertUserCommand = "SELECT * FROM Booking INNER JOIN AMVUser ON Booking.userID = AMVUser.userID WHERE paid = false AND returnDate IS NOT NULL";
                PreparedStatement st1 = db.prepareStatement(insertUserCommand);
                st1.executeUpdate();
                ResultSet rs1 = st1.executeQuery();

                DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                while(rs1.next()) {
                    int userID = rs1.getInt("userID");
                    String firstName = rs1.getString("firstName");
                    String lastName = rs1.getString("lastName");
                    String email1 = rs1.getString("email");
                    String phoneNumber = rs1.getString("phoneNumber");

                    int orderID = rs1.getInt("orderID");
                    LocalDate startDate = rs1.getDate("startDate").toLocalDate();
                    LocalDate endDate = rs1.getDate("endDate").toLocalDate();
                    LocalDate toolReturnDate = rs1.getDate("returnDate").toLocalDate();
                    int toolID = rs1.getInt("toolID");
                    int totalPrice = rs1.getInt("totalPrice");

                    String startDateString = startDate.format(formatters);
                    String endDateString = endDate.format(formatters);
                    String toolReturnalDateString = toolReturnDate.format(formatters);

                    out.print("<tr>");
                    out.print("<td>" + userID + "</td> ");
                    out.print("<td>" + firstName + "</td> ");
                    out.print("<td>" + lastName + "</td> ");
                    out.print("<td>" + email1 + "</td> ");
                    out.print("<td>" + phoneNumber + "</td> ");
                    out.print("<td>" + orderID + "</td> ");
                    out.print("<td>" + startDateString + "</td> ");
                    out.print("<td>" + endDateString + "</td> ");
                    out.print("<td>" + toolReturnalDateString + "</td> ");
                    out.print("<td>" + toolID + "</td> ");
                    out.print("<td>" + totalPrice + "</td> ");
                    out.print("</tr>");
                }
                out.print("</tr>");
                out.print("</table>");
                out.print("<br>");

                out.print("<h2>Mark order as payed</h2>");
                out.print("<form action = 'payment' method = 'POST'>");
                out.print("<label for = 'orderID'>Order ID: </label><br>");
                out.print("<input type = 'text' name = 'orderID'><br>");
                out.print("<input type = 'submit' value = 'Submit'>");
                out.print("</form>");

                out.print("</body>");
                out.print("</html>");
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
            Connection db = DBUtils.getNoErrorConnection(out);
            String insertUserCommand = "UPDATE Booking SET paid = true WHERE orderID = ?";
            PreparedStatement statement = db.prepareStatement(insertUserCommand);
            statement.setString(1, request.getParameter("orderID"));
            statement.executeUpdate();

            out.print("<html>");
            out.print("<head>");
            out.print("</head>");
            out.print("<body>");
            out.print("<h1>Order successfully marked as paid</h1>");
            out.print("</body>");
            out.print("</html>");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}




