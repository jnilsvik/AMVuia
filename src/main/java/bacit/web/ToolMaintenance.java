package bacit.web;

import bacit.web.utils.DBUtils;
import bacit.web.z_JSP_cleared.AdminAccess;

import java.io.PrintWriter;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan
@WebServlet(name = "ToolMaintenance", value = "/toolmaintenance")
public class ToolMaintenance extends HttpServlet {

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
                out.print("<body>");

                out.print("<form action = 'toolmaintenance' method = 'POST'>");

                out.print("<label for = 'toolmaintenance'>Put tool in maintenance</label></td>");
                out.print("<input type = 'radio' id = 'toolmaintenance'  name = 'toolmaintenance' value = 'ToolInMaintenanceIn'>");
                out.print("<br>");

                out.print("<label for = 'toolmaintenance'>Put tool out of maintenance</label>");
                out.print("<input type = 'radio' id = 'toolmaintenance'  name = 'toolmaintenance' value = 'ToolInMaintenanceOut'>");
                out.print("<br>");

                out.print("<label for = 'toolID'>Tool ID: </label><br>");
                out.print("<input type = 'text' name = 'toolID'><br>");
                out.print("<br>");
                out.print("<input type = 'submit' value = 'Submit'>");
                out.print("</form>");
                out.print("<br>");


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

            if (request.getParameter("toolmaintenance").equals("ToolInMaintenanceIn")) {
                String insertUserCommand = "UPDATE Tool SET maintenance = true WHERE toolID = ?";
                PreparedStatement st1 = db.prepareStatement(insertUserCommand);
                st1.setString(1, request.getParameter("toolID"));
                st1.executeUpdate();

                out.print("<html>");
                out.print("<head>");
                out.print("</head>");
                out.print("<body>");
                out.print("<h1> Tool successfully put in maintenance</h1>");
                out.print("</body>");
                out.print("</html>");
            }

            if (request.getParameter("toolmaintenance").equals("ToolInMaintenanceOut")) {
                String insertUserCommand = "UPDATE Tool SET maintenance = false WHERE toolID = ?";
                PreparedStatement st2 = db.prepareStatement(insertUserCommand);
                st2.setString(1, request.getParameter("toolID"));
                st2.executeUpdate();

                out.print("<html>");
                out.print("<head>");
                out.print("</head>");
                out.print("<body>");
                out.print("<h1> Tool successfully put out of maintenance</h1>");
                out.print("</body>");
                out.print("</html>");
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}




