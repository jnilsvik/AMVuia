package bacit.web.General;

import bacit.web.utils.PageAccess;
import bacit.web.utils.hashPassword;
import bacit.web.utils.DBUtils;

import java.sql.*;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "ChangePassword", value = "/changepassword")
public class ChangePassword extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (checkSession(request,response)){
                request.getRequestDispatcher("jspFiles/UserBookings/passwordChange.jsp").forward(request,response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            // TODO: 24.11.2021 -joachim: this could use some refactoring
            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("email");

            Connection db = DBUtils.getNoErrorConnection();
            String oldPassword = hashPassword.encryptThisString(request.getParameter("oldpass"));
            String newPassword1 = hashPassword.encryptThisString(request.getParameter("newpass1"));
            String newPassword2 = hashPassword.encryptThisString(request.getParameter("newpass2"));
            PreparedStatement st1 = db.prepareStatement(
                    "SELECT passwordHash FROM AMVUser WHERE email = ?");
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

                PageAccess.reDirFeedback(request,response,"Password was successfully changed!");
            }

            db.close();
        } catch (Exception e) {
            PageAccess.reDirFeedback(request,response,"Something went wrong. Either you wrote the wrong current password, or the new passwords didnt match.");
        }
    }

    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (PageAccess.isUser(request)){
            return true;
        }
        PageAccess.reDirWOUser(request,response);
        return false;
    }
}





