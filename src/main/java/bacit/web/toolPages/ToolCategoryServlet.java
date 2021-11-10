package bacit.web.toolPages;

import bacit.web.utils.PageElements;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            //PageElements.printSidebar(out);
            out.println("<html><head></head>");
            out.println("<style> table, th, td {border: 1px solid black;}</style>");
            out.println("<body>");
            out.println("<form action = 'toollistservlet' method = 'GET'>");
            out.println("<table>");

            while (rs.next()) {

                String categoryName = rs.getString("toolCategory");

                out.println("<tr>");
                out.println("<td><label for = " + categoryName + "> " + categoryName.replaceAll("_", " ") + ":</label></td>");
                out.println("<td><img src = 'img/amv.png' width = '156' heigth = '151'></td>");
                out.println("<td><input type = 'radio' id = " + categoryName + " name = 'category' value = " + categoryName + "></td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<input type = 'submit' value = 'Submit'>");
            out.println("</form></body></html>");

        } catch (Exception e) {
            out.println("error");
        }
    }

}








