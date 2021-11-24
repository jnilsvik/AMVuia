package bacit.web.ToolBooking;

import bacit.web.Modules.ToolModel;
import bacit.web.utils.DBUtils;
import bacit.web.utils.PageAccess;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "xtc", value = "/xtc")
public class ToolCategory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkSession(request,response)){
                GetSetTools(request, request.getParameter("category"));

                request.getRequestDispatcher("jspFiles/ToolBooking/toolCategory.jsp").forward(request,response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void GetSetTools(HttpServletRequest request, String toolCategory){
        try {
            Connection dbc = DBUtils.getNoErrorConnection();
            PreparedStatement ps2 = dbc.prepareStatement(
                    "select * from Tool where toolCategory = ? ");
            ps2.setString(1, toolCategory);
            ResultSet rs2 = ps2.executeQuery();

            ArrayList<ToolModel> toolALL1 = new ArrayList<>();
            while (rs2.next()){
                toolALL1.add(
                        new ToolModel(
                                rs2.getInt("toolID"),
                                rs2.getString("toolName"),
                                rs2.getString("toolCategory"),
                                rs2.getBoolean("maintenance"),
                                rs2.getInt("priceFirst"),
                                rs2.getInt("priceAfter"),
                                rs2.getInt("certificateID"),
                                rs2.getString("toolDescription"),
                                rs2.getString("picturePath")));
            }
            request.setAttribute("toolALL1", toolALL1);

            rs2.close();
            ps2.close();
            dbc.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (PageAccess.isUser(request)){
            return true;
        }
        PageAccess.reDirWOUser(request,response);
        PageAccess.reDirWOAdmin(request,response);
        return false;
    }

}