package bacit.web.AdminFunctions;

import bacit.web.utils.DBUtils;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan
@WebServlet(name = "ToolMaintenance", value = "/toolmaintenance")
public class ToolMaintenance extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        try {
            HttpSession session=request.getSession(false);
            String email = null;
            if(session != null){
                email = (String) session.getAttribute("email");
            }
            if(email == null){
                response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
                return;
            }

            if (AdminAccess.accessRights(email)) {
                request.getRequestDispatcher("jspFiles/AdminFunctions/toolMaintenance.jsp").forward(request,response);
            } else {
                DBUtils.ReDirFeedback(request,response,"You need to be an administrator to view this");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String toolMaintenance = request.getParameter("toolmaintenance");
        String toolID = request.getParameter("toolID");
        maintainTool(request, response, toolMaintenance, toolID);
    }

    public void maintainTool(HttpServletRequest request, HttpServletResponse response, String toolMaintenance, String toolID) {

        try {
        Connection db = DBUtils.getNoErrorConnection();

        if (toolMaintenance.equals("ToolInMaintenanceIn")) {
            String insertUserCommand = "UPDATE Tool SET maintenance = true WHERE toolID = ?";
            PreparedStatement st1 = db.prepareStatement(insertUserCommand);
            st1.setString(1, toolID);
            st1.executeUpdate();

            String successfulLine = "<h3 style=\"text-align:center\">Tool was successfully put in maintenance!</h3>" + "<br><br><br>"  + "<a href=\"toolmaintenance\"> <span class=bigbutton> Go back  </span></a>";
            request.setAttribute("successfulLine", successfulLine);
            request.getRequestDispatcher("/jspFiles/AdminFunctions/successfulLine.jsp").forward(request,response);
        }

        if (toolMaintenance.equals("ToolInMaintenanceOut")) {
            String insertUserCommand = "UPDATE Tool SET maintenance = false WHERE toolID = ?";
            PreparedStatement st2 = db.prepareStatement(insertUserCommand);
            st2.setString(1, toolID);
            st2.executeUpdate();

            String successfulLine = "<h3 style=\"text-align:center\">Tool was successfully put out of maintenance!</h3>" + "<br><br><br>"  + "<a href=\"toolmaintenance\"> <span class=bigbutton> Go back  </span></a>";
            request.setAttribute("successfulLine", successfulLine);
            request.getRequestDispatcher("/jspFiles/AdminFunctions/successfulLine.jsp").forward(request,response);
        }
        db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}




