package bacit.web.AdminFunctions;

import bacit.web.utils.DBUtils;
import bacit.web.utils.hashPassword;

import java.io.PrintWriter;
import java.sql.Connection;
import java.io.*;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan
@WebServlet(name = "Register", value = "/register")
public class Register extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        HttpSession session=request.getSession(false);
        String email = null;
        if(session != null){
            email = (String) session.getAttribute("email");
        }
        if(email == null){
            response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
            return;
        }
        if(AdminAccess.accessRights(email)) {
            request.getRequestDispatcher("/jspFiles/AdminFunctions/register.jsp").forward(request,response);
        }else {
            request.getRequestDispatcher("/jspFiles/AdminFunctions/noAdminAccount.jsp").forward(request,response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
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
            // TODO: 10.11.2021 -joachim: redirect to login? not the way it should be for final but for is fine
            //  @dilan
            out.print("<html><body>");
            out.print("<a href=\"login\"><h1>Registration successful! Click here to login</a></h1></a>");
            out.print("</body></html>");
        }
        catch (Exception e) {
            e.printStackTrace();
            out.print("test");
        }
    }
}




