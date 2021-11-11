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
            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("email");

                out.print("<html>");
                out.print("<head>");
                out.print("<title>Change Pasword</title>");
                out.print("</head>");

                out.print("<h2>Change Password</h2>");
                out.print("<form action = 'changepassword' method = 'POST'> ");
                out.print("<label for = 'oldpass'>Old password: </label><br>");
                out.print("<input type = 'text' name = 'oldpass' required><br>");
                out.print("<label for = 'newpass1'>New password: </label><br>");
                out.print("<input type = 'text' name = 'newpass1' required><br>");
                out.print("<label for = 'newpass2'>Repeat new password: </label><br>");
                out.print("<input type = 'text' name = 'newpass2' required><br>");
                out.print("<input type = 'submit' value = 'Change Password'>");
                out.print("</form>");


                out.print("</body>");
                out.print("</html>");


        } catch (Exception e) {
            out.print("error");
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("email");

            Connection db = DBUtils.getNoErrorConnection(out);

            String oldPassword = hashPassword.encryptThisString(request.getParameter("oldpass"));
            String newPassword1 = hashPassword.encryptThisString(request.getParameter("newpass1"));
            String newPassword2 = hashPassword.encryptThisString(request.getParameter("newpass2"));


            PreparedStatement st1 = db
                    .prepareStatement("SELECT * FROM AMVUser WHERE email = ?");
            st1.setString(1, email);
            ResultSet rs = st1.executeQuery();

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
                out.print("<a href = 'http://localhost:8081/bacit-web-1.0-SNAPSHOT/profile'> Go back to profile</a>");
            }

            db.close();

        } catch (Exception e) {
            out.print("Something went wrong. Either you wrote the wrong current password, or the 2 new passwords didnt match.");
        }


    }

}





