package bacit.web.Profile;

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
            HttpSession session=request.getSession(false);
            String email = null;
            if(session != null){
                email = (String) session.getAttribute("email");
            }
            if(email == null){
                response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
                return;
            }

            request.getRequestDispatcher("jspFiles/Profile/passwordChange.jsp").forward(request,response);
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

                String successfulLine = "Password was successfully changed!";
                request.setAttribute("successfulLine", successfulLine);
                request.getRequestDispatcher("jspFiles/AdminFunctions/successfulLine.jsp").forward(request,response);
            }
        } catch (Exception e) {
            out.println("Something went wrong. Either you wrote the wrong current password, or the 2 new passwords didnt match.");
        }

    }
}





