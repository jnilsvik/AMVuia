package bacit.web.z_JSP_cleared.lists_WIP.toolPages;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//Marius wrote this code

@WebServlet(name = "xToolListServlet", value = "/xtoollistservlet")
public class ToolListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {

            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM Tool WHERE toolCategory = ? AND NOT maintenance = true");
            ps.setString(1, (request.getParameter("toolCategory")));
            ResultSet rs = ps.executeQuery();

            out.print("<html>");
            out.print("<head>");
            out.print("</head>");
            out.print("<style>");
            out.print("table, th, td {border: 1px solid black;}");
            out.print("</style>");
            out.print("<body>");
            //PageElements.printSidebar(out);
            out.print("<form action = 'tooldetail' method = 'GET'>");
            out.print("<table>");

            while (rs.next()) {

                String toolName = rs.getString("toolName");
                int toolID = rs.getInt("toolID");


                out.print("<tr>");
                out.print("<td><label for = " + toolName + "> " + toolName.replaceAll("_", " ") + ":</label></td>");
                out.print("<td><img src = 'img/amv.png' width = '156' heigth = '151'></td>");
                out.print("<td><input type = 'radio' id = " + toolName + " name = 'tool' value = " + toolID + "></td>");
                out.print("</tr>");

            }

            out.print("</table>");

            out.print("<input type = 'submit' value = 'Submit'>");
            out.print("</form>");
            out.print("</body>");
            out.print("</html>");

        } catch (Exception e) {
            out.print("error");
        }
    }

}








