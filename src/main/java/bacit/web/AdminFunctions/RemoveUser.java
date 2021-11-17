package bacit.web.AdminFunctions;

import bacit.web.Modules.UserModel;
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

//by ? changed to jsp by paul
@WebServlet(name = "RemoveUser", value = "/removeuser")
public class RemoveUser extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        HttpSession session = request.getSession(false);
        if(session == null){
            response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
            return;
        }
        String email = (String) session.getAttribute("email");
        if(AdminAccess.accessRights(email)) {
            try {
                List<UserModel> users = getUsers();
                request.setAttribute("users", users);
                request.getRequestDispatcher("/RemoveUser.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            request.getRequestDispatcher("/NoAdminAccount.jsp").forward(request,response);
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
        if(AdminAccess.accessRights(email)) {
            try {
                boolean success = deleteUser(request.getParameter("input"));
                List<UserModel> users = getUsers();

                request.setAttribute("success", success);
                request.setAttribute("users", users);
                request.getRequestDispatcher("/RemoveUserPost.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            request.getRequestDispatcher("/NoAdminAccount.jsp").forward(request,response);
        }
    }

    private boolean deleteUser(String email) throws  SQLException{
        Connection db = DBUtils.getNoErrorConnection();
        PreparedStatement statement = db.prepareStatement("DELETE FROM AMVUser WHERE email = ? ");
        statement.setString(1, String.valueOf(email));
        int noOfAffectedRows = statement.executeUpdate();

        statement.close();
        db.close();
        return noOfAffectedRows != 0;
    }

    private List<UserModel> getUsers() throws SQLException {
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

}
