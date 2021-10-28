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
            out.println("<body>");
            out.print("<form action = 'tooldetail' method = 'GET'");

            while (rs.next()) {

                String toolName = rs.getString("toolName");
                int toolID = rs.getInt("toolID");
                out.println("<input type = 'radio' id = " + toolName + " name = 'tool' value = " + toolID + ">");
                out.println("<label for = " + toolName + "> " + toolName.replaceAll("_", " ") + ":</label>");
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








