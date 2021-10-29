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

@WebServlet(name = "ToolListServlet", value = "/toollistservlet")
public class ToolListServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {

            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM Tool WHERE toolCategory = ? AND NOT maintenance = true");
            ps.setString(1, (request.getParameter("category")));
            ResultSet rs = ps.executeQuery();

            out.println("<html>");
            out.println("<head>");
            out.println("</head>");
            out.println("<style>");
            out.println("table, th, td {border: 1px solid black;}");
            out.println("</style>");
            out.println("<body>");
            PageElements.printSidebar(out);
            out.println("<form action = 'tooldetail' method = 'GET'>");
            out.println("<table>");

            while (rs.next()) {

                String toolName = rs.getString("toolName");
                int toolID = rs.getInt("toolID");


                out.println("<tr>");
                out.println("<td><label for = " + toolName + "> " + toolName.replaceAll("_", " ") + ":</label></td>");
                out.println("<td><img src = 'img/amv.png' width = '156' heigth = '151'></td>");
                out.println("<td><input type = 'radio' id = " + toolName + " name = 'tool' value = " + toolID + "></td>");
                out.println("</tr>");

            }

            out.println("</table>");

            out.println("<input type = 'submit' value = 'Submit'>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {
            out.println("error");
        }
    }

}








