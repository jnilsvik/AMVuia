package bacit.web;



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
            out.print("<html><head><style> table, th, td {border: 1px solid black;}</style></head>");
            out.print("<body>");
            out.print("<form action = 'toollistservlet' method ='GET'>");
            out.print("<table>");

            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");
            PreparedStatement ps = con.prepareStatement("SELECT toolCategory FROM Tool GROUP BY toolCategory");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String categoryName = rs.getString("toolCategory");

                out.print("<tr>");
                out.print("<td><label for = " + categoryName + "> " + categoryName.replaceAll("_", " ") + ":</label></td>");
                out.print("<td><img src = 'img/amv.png' width = '156' heigth = '151'></td>");
                out.print("<td><input type = 'radio' id = " + categoryName + " name = 'category' value = " + categoryName + "></td>");
                out.print("</tr>");
            }
            out.print("</table>");
            out.print("<input type = 'submit' value = 'Submit'>");
            out.print("</form></body></html>");

        } catch (Exception e) {
            out.print("error");
        }
    }

}








