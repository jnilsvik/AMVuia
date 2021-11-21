package bacit.web.Profile;

import bacit.web.utils.PageAccess;
import bacit.web.utils.hashPassword;
import bacit.web.utils.DBUtils;

import java.sql.*;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan
@WebServlet(name = "ChangePassword", value = "/changepassword")
public class ChangePassword extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkSession(request,response)){
                request.getRequestDispatcher("jspFiles/Profile/passwordChange.jsp").forward(request,response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("email");

            Connection db = DBUtils.getNoErrorConnection();
            String oldPassword = hashPassword.encryptThisString(request.getParameter("oldpass"));
            String newPassword1 = hashPassword.encryptThisString(request.getParameter("newpass1"));
            String newPassword2 = hashPassword.encryptThisString(request.getParameter("newpass2"));
            PreparedStatement st1 = db
                    .prepareStatement("SELECT passwordHash FROM AMVUser WHERE email = ?");
            st1.setString(1, email);
            ResultSet rs = st1.executeQuery();
            boolean isOldPassValid = false;
            while(rs.next()) {
                if(oldPassword.equals(rs.getString("passwordHash"))) {
                    isOldPassValid = true;
                }
            }
            if(newPassword1.equals(newPassword2) && isOldPassValid) {
                PreparedStatement st2 = db
                        .prepareStatement("UPDATE AMVUser SET passwordHash = ? WHERE email = ?");
                st2.setString(1, newPassword1);
                st2.setString(2, email);
                st2.executeUpdate();

                String successfulLine = "Password was successfully changed!";
                request.setAttribute("successfulLine", successfulLine);
                request.getRequestDispatcher("jspFiles/AdminFunctions/successfulLine.jsp").forward(request,response);
            }
        } catch (Exception e) {
            PageAccess.ReDirFeedback(request,response,"Something went wrong. Either you wrote the wrong current password, or the 2 new passwords didnt match.");
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





