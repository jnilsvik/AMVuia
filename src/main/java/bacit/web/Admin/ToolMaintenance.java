package bacit.web.Admin;

import bacit.web.Modules.ToolModel;
import bacit.web.utils.DBUtils;
import bacit.web.utils.PageAccess;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan
@WebServlet(name = "ToolMaintenance", value = "/toolmaintenance")
public class ToolMaintenance extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkSession(request, response)) {
                Connection dbConnection = DBUtils.getNoErrorConnection();
                String toolQ = "select * from Tool order by toolID ";
                PreparedStatement statement = dbConnection.prepareStatement(toolQ);
                ResultSet rs = statement.executeQuery();

                ArrayList<ToolModel> toolList = new ArrayList<>();
                while (rs.next()) {
                    toolList.add(
                            new ToolModel(
                                    rs.getInt("toolID"),
                                    rs.getString("toolName"),
                                    rs.getString("toolCategory"),
                                    rs.getBoolean("maintenance"),
                                    rs.getInt("priceFirst"),
                                    rs.getInt("priceAfter"),
                                    rs.getInt("certificateID"),
                                    rs.getString("toolDescription"),
                                    rs.getString("picturePath")));
                }
                request.setAttribute("toolList", toolList); // ! a way to set attributes
                request.getRequestDispatcher("jspFiles/AdminFunctions/toolMaintenance.jsp").forward(request, response);

                rs.close();
                statement.close();
                dbConnection.close();
            } else {
                PageAccess.reDirFeedback(request, response, "You need to be an administrator to view this");
            }
        }
        catch(
                Exception e)

        {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String toolMaintenance = request.getParameter("toolmaintenance");
        String toolID = request.getParameter("toolID");
        maintainTool(request, response, toolMaintenance, toolID);
    }

    private void maintainTool(HttpServletRequest request, HttpServletResponse response, String toolMaintenance, String toolID) {
        try {
            Connection db = DBUtils.getNoErrorConnection();

            if (toolMaintenance.equals("ToolInMaintenanceIn")) {
                String insertUserCommand = "UPDATE Tool SET maintenance = true WHERE toolID = ?";
                PreparedStatement st1 = db.prepareStatement(insertUserCommand);
                st1.setString(1, toolID);
                st1.executeUpdate();

                PageAccess.reDirFeedback(request,response,"Tool was successfully put in maintenance!");
            }

            if (toolMaintenance.equals("ToolInMaintenanceOut")) {
                String insertUserCommand = "UPDATE Tool SET maintenance = false WHERE toolID = ?";
                PreparedStatement st2 = db.prepareStatement(insertUserCommand);
                st2.setString(1, toolID);
                st2.executeUpdate();

                PageAccess.reDirFeedback(request,response,"Tool was successfully removed from maintenance!");
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (PageAccess.isAdmin(request)){
            return true;
        }
        PageAccess.reDirWOUser(request,response);
        PageAccess.reDirWOAdmin(request,response);
        return false;
    }

}




