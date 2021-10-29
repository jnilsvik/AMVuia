package bacit.web.profilePage;

import java.io.PrintWriter;
import java.sql.*;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan
@WebServlet(name = "Profile", value = "/profile")
public class Profile extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("email");

                Class.forName("org.mariadb.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");
                PreparedStatement ps = con.prepareStatement("SELECT * FROM ((AMVUser INNER JOIN BOOKING ON AMVUser.userID = Booking.userID) INNER JOIN Tool on Booking.toolID = Tool.toolID) WHERE email = ?");
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();

                out.println("<html>");
                out.println("<head>");
                out.println("<title>Give a user a certificate</title>");
                out.println("</head>");
                out.println("<style>");

                out.println("table, th, td { border:1px solid black;}");

                out.println("</style>");

                out.print("<h2>Your current bookings</h2>");
                out.println("<table style = 'width:50%'>");
                out.println("<tr>");
                out.println("<th>Order Number</th>");
                out.println("<th>Tool Name</th>");
                out.println("<th>Start Date</th>");
                out.println("<th>End Date</th>");
                out.println("<th>Total Price</th>");

                while(rs.next()) {
                    int OrderID = rs.getInt("orderID");
                    Date startDate = rs.getDate("startDate");
                    Date endDate = rs.getDate("endDate");
                    int totalPrice = rs.getInt("totalPrice");
                    String toolName = rs.getString("toolName");

                    out.println("<tr>");
                    out.println("<td>" + OrderID + "</td> ");
                    out.println("<td>" + toolName.replaceAll("_", " ") + "</td> ");
                    out.println("<td>" + startDate + "</td> ");
                    out.println("<td>" + endDate + "</td> ");
                    out.println("<td>" + totalPrice + "</td> ");
                    out.println("</tr>");
                }
                out.print("</table>");

                out.println("</body>");
                out.println("</html>");


        } catch (Exception e) {
            out.println("error");
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

    }

}





