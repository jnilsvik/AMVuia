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
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Tool maintenance</title>");
                out.println("</head>");
                out.println("<style>");
                out.println("table, th, td { border:1px solid black;}");
                out.println("</style>");
                out.println("<body>");

                out.println("<table style = 'width:100%'>");
                out.println("<tr>");
                out.println("<th>User ID</th>");
                out.println("<th>First Name</th>");
                out.println("<th>Last Name</th>");
                out.println("<th>Email</th>");
                out.println("<th>Phone Number</th>");
                out.println("<th>Order ID</th>");
                out.println("<th>Start Date</th>");
                out.println("<th>End Date</th>");
                out.println("<th>Returnal Date</th>");
                out.println("<th>Tool ID</th>");
                out.println("<th>Total Price</th>");
                out.println("</tr>");

                Connection db = DBUtils.getNoErrorConnection(out);
                String insertUserCommand = "SELECT * FROM Booking INNER JOIN AMVUser ON Booking.userID = AMVUser.userID WHERE paid = false AND toolReturnDate IS NOT NULL";
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
                    LocalDate toolReturnDate = rs1.getDate("toolReturnDate").toLocalDate();
                    int toolID = rs1.getInt("toolID");
                    int totalPrice = rs1.getInt("totalPrice");

                    String startDateString = startDate.format(formatters);
                    String endDateString = endDate.format(formatters);
                    String toolReturnalDateString = toolReturnDate.format(formatters);

                    out.println("<tr>");
                    out.println("<td>" + userID + "</td> ");
                    out.println("<td>" + firstName + "</td> ");
                    out.println("<td>" + lastName + "</td> ");
                    out.println("<td>" + email1 + "</td> ");
                    out.println("<td>" + phoneNumber + "</td> ");
                    out.println("<td>" + orderID + "</td> ");
                    out.println("<td>" + startDateString + "</td> ");
                    out.println("<td>" + endDateString + "</td> ");
                    out.println("<td>" + toolReturnalDateString + "</td> ");
                    out.println("<td>" + toolID + "</td> ");
                    out.println("<td>" + totalPrice + "</td> ");
                    out.println("</tr>");
                }
                out.println("</tr>");
                out.println("</table>");
                out.println("<br>");

                out.println("<h2>Mark order as payed</h2>");
                out.println("<form action = 'payment' method = 'POST'>");
                out.println("<label for = 'orderID'>Order ID: </label><br>");
                out.println("<input type = 'text' name = 'orderID'><br>");
                out.println("<input type = 'submit' value = 'Submit'>");
                out.println("</form>");

                out.println("</body>");
                out.println("</html>");
            } else {
                out.println("<h1> Sorry, you don't have access to this page");
            }

        } catch (Exception e) {
            out.println("error");
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

            out.println("<html>");
            out.print("<head>");
            out.print("</head>");
            out.println("<body>");
            out.println("<h1>Order successfully marked as paid</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}




