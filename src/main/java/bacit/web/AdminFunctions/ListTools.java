package bacit.web.AdminFunctions;

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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "lt", value = "/lt")
public class ListTools extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (checkSession(request,response)){
                Connection dbConnection = DBUtils.getNoErrorConnection();
                String toolQ = "select * from Tool order by toolID ";
                PreparedStatement statement = dbConnection.prepareStatement(toolQ);
                ResultSet rs = statement.executeQuery();

                ArrayList<ToolModel> toolList = new ArrayList<>();
                while (rs.next()){
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
                request.getRequestDispatcher("/jspFiles/AdminFunctions/listTools.jsp").forward(request,response);

                rs.close();
                statement.close();
                dbConnection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (PageAccess.isAdmin(request,response)){
            return true;
        }
        PageAccess.reDirWOUser(request,response);
        PageAccess.reDirWOAdmin(request,response);
        return false;
    }
}