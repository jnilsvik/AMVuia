package bacit.web;

import bacit.web.utils.hashPassword;
import bacit.web.utils.DBUtils;

import java.io.PrintWriter;
import java.sql.*;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan
@WebServlet(name = "ChangePassword", value = "/changepassword")
public class ChangePassword extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            request.getRequestDispatcher("/passwordChange.jsp").forward(request,response);
        } catch (Exception e) {
            out.print("error");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            String email = (String) request.getAttribute("email");
            String oldPassword = hashPassword.encryptThisString(request.getParameter("oldpass"));
            String newPassword1 = hashPassword.encryptThisString(request.getParameter("newpass1"));
            String newPassword2 = hashPassword.encryptThisString(request.getParameter("newpass2"));

            Connection db = DBUtils.getNoErrorConnection();
            PreparedStatement st1 = db.prepareStatement("SELECT passwordHash FROM AMVUser WHERE email = ?");
            st1.setString(1, email);
            ResultSet rs = st1.executeQuery();
            // TODO: 11.11.2021 we could do this with a short js insert in the page,,,
            //  https://stackoverflow.com/questions/9142527/can-you-require-two-form-fields-to-match-with-html5
            boolean isOldPassValid = false;
            while(rs.next()) {
                if(oldPassword.equals(rs.getString("password"))) {
                    isOldPassValid = true;
                }
            }
            boolean doPasswordsMatch = false;
            if(newPassword1.equals(newPassword2)) {
                doPasswordsMatch = true;
            }
            if(isOldPassValid && doPasswordsMatch) {
                PreparedStatement st2 = db
                        .prepareStatement("UPDATE AMVUser SET passwordHash = ? WHERE email = ?");
                st2.setString(1, newPassword1);
                st2.setString(2, email);
                st2.executeUpdate();

                out.print("Your password has been successfully changed!");
                out.print("<a href = '/profile'> Go back to profile</a>");
            }
            db.close();
        } catch (Exception e) {
            out.print("Something went wrong. Either you wrote the wrong current password, or the 2 new passwords didnt match.");
        }
    }
}





