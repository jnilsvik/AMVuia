package bacit.web.toolPages;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//Marius wrote this code

@WebServlet(name = "ToolDetailServlet", value = "/tooldetail")
public class ToolDetailServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("email");

            int toolID = Integer.parseInt(request.getParameter("tool"));

            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM Tool WHERE toolID = ?");
            ps.setInt(1, toolID);
            ResultSet rs = ps.executeQuery();

            out.println("<html>");
            out.println("<head>");
            out.println("</head>");
            out.println("<body>");

            while (rs.next()) {

                String toolName = rs.getString("toolName");
                String toolDescription = rs.getString("toolDescription");
                String toolCategory = rs.getString("toolCategory");
                int priceFirst = rs.getInt("priceFirst");
                int priceAfter = rs.getInt("priceAfter");


                out.println("<h1> " + toolName + " from the Category: " + toolCategory + "</h1>");
                out.println("<h2>Price the first day: " + priceFirst + "</h2>");
                out.println("<h2>Price after the first day: " + priceAfter + "</h2>");
                out.println("<br>");
                out.println("<p> " + toolDescription + "");
                out.println("<br>");
                out.println("<br>");

                out.print("<form action = 'toolbooking' method = 'POST'");
                out.print("<label for = 'tools'> Your chosen tool:</label>");
                out.print("<select name = 'tools' id = 'tools'><br>");

                out.print("<option value = '" + toolID + "'> " + toolName + " </option>");

                out.print("</select>");

                out.print("<label for = 'date'> Choose start date:</label>");
                out.print("<input type = 'date' id = 'date' name = 'date'><br>");
                out.println("<br>");


                out.println(" <label for='days'>Choose how many days:</label>");
                out.println("<select id='days' name = 'days'>");
                out.print("<option value='1'> 1 Day</option>");
                out.print("<option value='2'> 2 Days</option>");
                out.print("<option value='3'> 3 Days</option>");
                out.print("</select>");

                out.print("<input type = 'text' value = '" + email + "' name = 'email' readonly>");

                out.print("<input type = 'submit' value = 'Submit'>");
                out.println("</form>");

            }

            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {
            out.println("error");
        }
    }

}







