package bacit.web.z_JSP_cleared;

import bacit.web.AdminAccess;
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

        String toolMaintenance = request.getParameter("toolmaintenance");
        String toolID = request.getParameter("toolID");
        maintainTool(request, response, out, toolMaintenance, toolID);
    }

    public void maintainTool(HttpServletRequest request, HttpServletResponse response, PrintWriter out, String toolMaintenance, String toolID) {

        try {
        Connection db = DBUtils.getNoErrorConnection(out);

        if (toolMaintenance.equals("ToolInMaintenanceIn")) {
            String insertUserCommand = "UPDATE Tool SET maintenance = true WHERE toolID = ?";
            PreparedStatement st1 = db.prepareStatement(insertUserCommand);
            st1.setString(1, toolID);
            st1.executeUpdate();

            String successfulLine = "Tool was successfully put out in maintenance";
            request.setAttribute("successfulLine", successfulLine);
            request.getRequestDispatcher("successfulLine.jsp").forward(request,response);
        }

        if (toolMaintenance.equals("ToolInMaintenanceOut")) {
            String insertUserCommand = "UPDATE Tool SET maintenance = false WHERE toolID = ?";
            PreparedStatement st2 = db.prepareStatement(insertUserCommand);
            st2.setString(1, toolID);
            st2.executeUpdate();

            String successfulLine = "Tool was successfully put out of maintenance";
            request.setAttribute("successfulLine", successfulLine);
            request.getRequestDispatcher("successfulLine.jsp").forward(request,response);
        }
        db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}




