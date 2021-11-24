package bacit.web.ToolBooking;

import bacit.web.Modules.ToolModel;
import bacit.web.utils.DBUtils;
import bacit.web.utils.PageAccess;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ToolList", value = "/toolList")
public class ToolAllListings extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (checkSession(request,response)){
                List<String> categories = getCategories(request);
                List<ToolModel> tools = GetSetTools(request);
                printJsp(categories, tools, request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected List<String> getCategories(HttpServletRequest request) throws SQLException {
        Connection dbc= DBUtils.getNoErrorConnection();
        PreparedStatement ps1 = dbc.prepareStatement(
                    "SELECT toolCategory FROM Tool GROUP BY toolCategory");
        ResultSet rs1 = ps1.executeQuery();

        ArrayList<String> categories = new ArrayList<>();
        while (rs1.next()){
            categories.add(rs1.getString("toolCategory"));
        }
        rs1.close();
        ps1.close();
        dbc.close();
        return categories;
    }

    protected List<ToolModel> GetSetTools(HttpServletRequest request) throws SQLException {

        Connection dbc = DBUtils.getNoErrorConnection();
        PreparedStatement ps2 = dbc.prepareStatement(
                    "select * from Tool order by toolID");
        ResultSet rs2 = ps2.executeQuery();

        ArrayList<ToolModel> toolALL = new ArrayList<>();
        while (rs2.next()){
            toolALL.add(
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

        rs2.close();
        ps2.close();
        dbc.close();
        return toolALL;

    }

    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (PageAccess.isUser(request,response)){
            return true;
        }
        PageAccess.reDirWOUser(request,response);
        return false;
    }

    protected void printJsp(List<String> categories, List<ToolModel> tools, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("toolCAT", categories);
        request.setAttribute("toolALL", tools);
        request.getRequestDispatcher("/jspFiles/ToolBooking/toolListAll.jsp").forward(request,response);
    }
}