package bacit.web;

import bacit.web.utils.DBUtils;

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
                request.getRequestDispatcher("toolMaintenance.jsp").forward(request,response);
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

            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}




