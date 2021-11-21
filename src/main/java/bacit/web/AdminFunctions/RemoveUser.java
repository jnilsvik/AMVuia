package bacit.web.AdminFunctions;

import bacit.web.Modules.UserModel;
import bacit.web.utils.DBUtils;
import bacit.web.utils.PageAccess;

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

//by ? changed to jsp by paul
@WebServlet(name = "RemoveUser", value = "/removeuser")
public class RemoveUser extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(checkSession(request,response)) {
            try {
                List<UserModel> users = getUsers();
                writeGetToJSP(users, request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(checkSession(request,response)) {
            try {
                String user = getUser(request);
                boolean success = deleteUser(user);
                List<UserModel> users = getUsers();

                writePostToJSP(users, success, request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected boolean deleteUser(String email) throws  SQLException{
        Connection db = DBUtils.getNoErrorConnection();
        PreparedStatement statement = db.prepareStatement("DELETE FROM AMVUser WHERE email = ? ");
        statement.setString(1, String.valueOf(email));
        int noOfAffectedRows = statement.executeUpdate();

        statement.close();
        db.close();
        return noOfAffectedRows != 0;
    }

    protected List<UserModel> getUsers() throws SQLException {
        List<UserModel> users = new LinkedList<>();
        Connection db = DBUtils.getNoErrorConnection();
        String a = "Select userID, email FROM AMVUser SORT ORDER BY userID; ";
        PreparedStatement statements = db.prepareStatement(a);
        ResultSet rs = statements.executeQuery();
        while (rs.next()) {
            users.add(new UserModel(
                    rs.getInt("userID"),
                    "", "", "", "", false, false,
                    rs.getString("email")
            ));
        }
        rs.close();
        statements.close();
        db.close();
        return users;
    }

    protected String getUser(HttpServletRequest request){
        return request.getParameter("input");
    }

    protected void writePostToJSP(List<UserModel> users, boolean success, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("user", true);
        request.setAttribute("success", success);
        request.setAttribute("users", users);
        request.getRequestDispatcher("/jspFiles/AdminFunctions/removeMessage.jsp").forward(request, response);
    }

    protected void writeGetToJSP(List<UserModel> users, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("users", users);
        request.getRequestDispatcher("/jspFiles/AdminFunctions/removeUser.jsp").forward(request, response);
    }

    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!PageAccess.isAdmin(request,response)){
            PageAccess.reDirWOUser(request,response);
            PageAccess.reDirWOAdmin(request,response);
            return false;
        } else return true;
    }
}