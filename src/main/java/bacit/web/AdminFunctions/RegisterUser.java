package bacit.web.AdminFunctions;

import bacit.web.utils.DBUtils;
import bacit.web.utils.PageAccess;
import bacit.web.utils.hashPassword;

import java.sql.Connection;
import java.io.*;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan
@WebServlet(name = "RegisterUser", value = "/register")
public class RegisterUser extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(checkSession(request,response)) {
            request.getRequestDispatcher("/jspFiles/AdminFunctions/registerUser.jsp").forward(request,response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        try {
            Connection db = DBUtils.getNoErrorConnection();
            PreparedStatement statement = db.prepareStatement(
                    "insert into AMVUser (email, passwordHash, firstName, lastName, phoneNumber, unionMember, userAdmin) values(?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, request.getParameter("email"));
            statement.setString(2, hashPassword.encryptThisString(request.getParameter("password")));
            statement.setString(3, request.getParameter("fname"));
            statement.setString(4, request.getParameter("lname"));
            statement.setString(5, request.getParameter("phone"));
            statement.setBoolean(6,(request.getParameter("union") != null));
            statement.setBoolean(7,(request.getParameter("admin") != null));
            statement.executeUpdate();
            statement.close();
            db.close();

            request.getRequestDispatcher("/jspFiles/AdminFunctions/adminPage.jsp").forward(request,response);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (PageAccess.isAdmin(request,response)){
            return true;
        }
        PageAccess.reDirWOUser(request,response);
        return false;
    }

}




