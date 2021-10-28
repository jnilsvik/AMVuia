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

@WebServlet(name = "ToolCategoryServlet", value = "/toolcategories")
public class ToolCategoryServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {

            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");

            PreparedStatement ps = con.prepareStatement("SELECT toolCategory FROM Tool GROUP BY toolCategory");
            ResultSet rs = ps.executeQuery();

            out.println("<html>");
            out.println("<head>");
            out.println("</head>");
            out.println("<body>");
            out.print("<form action = 'toollistservlet' method = 'GET'>");

            while (rs.next()) {

                String categoryName = rs.getString("toolCategory");
                out.println("<input type = 'radio' id = " + categoryName + " name = 'category' value = " + categoryName + ">");
                out.println("<label for = " + categoryName + "> " + categoryName + ":</label>");
                out.println("<br>");
            }


            out.println("<input type = 'submit' value = 'Submit'>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {
            out.println("error");
        }
    }

}








