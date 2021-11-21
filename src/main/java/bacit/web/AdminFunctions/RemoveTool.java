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
        boolean valid = checkSession(request, response);
        if (valid){
            try {
                List<ToolModel> tools = getTools();
                writeGetToJSP(tools, request, response);
            } catch (SQLException | ServletException e) {
                e.printStackTrace();
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean valid = checkSession(request, response);
        if(valid){
            try {
                int id = getID(request);
                boolean success = deleteRow(id);
                List<ToolModel> tools = getTools();

                writePostToJSP(tools, success, request, response);

            } catch (Exception e) {
                PrintWriter out = response.getWriter();
                out.println(e);
            }
        }
    }

    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!PageAccess.isAdmin(request,response)){
            PageAccess.reDirWOUser(request,response);
            PageAccess.reDirWOAdmin(request,response);
            return false;
        } else return true;
    }

    protected List<ToolModel> getTools() throws SQLException {
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

    protected boolean deleteRow(int id) throws SQLException {
        Connection db = DBUtils.getNoErrorConnection();
        PreparedStatement statement = db.prepareStatement("DELETE FROM Tool WHERE toolID = ? ");
        statement.setString(1, String.valueOf(id));
        int noOfAffectedRows = statement.executeUpdate();

        statement.close();
        db.close();

        return noOfAffectedRows != 0;


    }

    protected int getID(HttpServletRequest request){
        try{
            return Integer.parseInt(request.getParameter("input"));
        } catch (NullPointerException e){
            return -1;
        }

    }

    protected void writeGetToJSP(List<ToolModel> tools, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("tools", tools);
        request.getRequestDispatcher("/jspFiles/AdminFunctions/removeTool.jsp").forward(request, response);
    }

    protected void writePostToJSP(List<ToolModel> tools, boolean success, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("user", false);
        request.setAttribute("success", success);
        request.setAttribute("tools", tools);
        request.getRequestDispatcher("/jspFiles/AdminFunctions/removeMessage.jsp").forward(request,response);
    }
}

