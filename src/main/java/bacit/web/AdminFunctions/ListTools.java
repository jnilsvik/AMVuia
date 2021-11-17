package bacit.web.AdminFunctions;

import bacit.web.Modules.ToolModel;
import bacit.web.utils.DBUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
by Joachim

prints all the tools
*/
@WebServlet(name = "lt", value = "/lt")
public class ListTools extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: 10.11.2021 need to implement the non-admin prevention
        try {
            HttpSession session=request.getSession(false);
            String email = (String) session.getAttribute("email");
            if(email == null){
                response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
                return;
            }

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

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}