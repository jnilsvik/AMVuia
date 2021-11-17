package bacit.web.AdminFunctions;

import bacit.web.Modules.ToolModel;
import bacit.web.utils.DBUtils;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


//made by ? changed to JSP by Paul
@WebServlet(name = "RemoveTool", value = "/removetool")
public class RemoveTool extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        HttpSession session=request.getSession(false);
        String email = (String) session.getAttribute("email");
        if(email == null){
            response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
            return;
        }

        if (AdminAccess.accessRights(email)){
            try {
                List<ToolModel> tools = getTools();
                request.setAttribute("tools", tools);
                request.getRequestDispatcher("/jspFiles/AdminFunctions/removeTool.jsp").forward(request, response);
            } catch (SQLException | ServletException e) {
                e.printStackTrace();
            }
        }else {
            request.getRequestDispatcher("/jspFiles/AdminFunctions/noAdminAccount.jsp").forward(request,response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession(false);
        if(session == null){
            response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
            return;
        }
        String email = (String) session.getAttribute("email");
        if(AdminAccess.accessRights(email)){
            try {
                String id = request.getParameter("input");
                Boolean success = deleteRow(id);
                List<ToolModel> tools = getTools();

                request.setAttribute("success", success);
                request.setAttribute("tools", tools);
                request.getRequestDispatcher("/RemoveToolPost.jsp").forward(request,response);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            request.getRequestDispatcher("/jpsFiles/AdminFunctions/noAdminAccount.jsp").forward(request,response);
        }
    }

    private List<ToolModel> getTools() throws SQLException {
        List<ToolModel> tools = new LinkedList();
        Connection db = DBUtils.getNoErrorConnection();
        String query = "SELECT toolID, toolName FROM Tool;";
        PreparedStatement statement = db.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            tools.add(new ToolModel(
                    rs.getInt("toolID"),
                    rs.getString("toolName"),
                    "",false, 0,0,0,"", ""));
        }
        rs.close();
        statement.close();
        db.close();
        return tools;
    }

    private boolean deleteRow(String id) throws SQLException {
        Connection db = DBUtils.getNoErrorConnection();
        PreparedStatement statement = db.prepareStatement("DELETE FROM Tool WHERE toolID = ? ");
        statement.setString(1, String.valueOf(id));
        int noOfAffectedRows = statement.executeUpdate();

        statement.close();
        db.close();

        return noOfAffectedRows != 0;


    }
}

