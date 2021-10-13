package dilan.prosjekt;

import java.io.PrintWriter;
import java.sql.Connection;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
@WebServlet(name = "Register", value = "/register")
public class Register extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Register user form</title>");
        out.println("</head>");

        out.println("<h2>Register User</h2>");
        out.println("<form action = 'register' method = 'POST'> ");
        out.println("<label for = 'firstname'>First Name: </label><br>");
        out.println("<input type = 'text' name = 'firstname'><br>");
        out.println("<label for = 'lastname'>Last Name: </label><br>");
        out.println("<input type = 'text' name = 'lastname'><br>");
        out.println("<label for = 'password'>Password: </label><br>");
        out.println("<input type = 'text' name = 'password'><br>");
        out.println("<label for = 'email'>Email: </label><br>");
        out.println("<input type = 'text' name = 'email'><br>");
        out.println("<label for = 'phone'>Phone: </label><br>");
        out.println("<input type = 'text' name = 'phone'><br>");
        out.println("<label for = 'unionmember'>Union Member: </label><br>");
        out.println("<input type = 'checkbox' name = 'unionmember' value = 'true'><br>");
        out.println("<label for = 'userAdmin'>Admin: </label><br>");
        out.println("<input type = 'checkbox' name = 'userAdmin' value = 'true'><br>");
        out.println("<input type = 'submit' value = 'Register User'>");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        try {

            UserModel user = new UserModel();
            if(request.getParameter("unionmember") != null) {
                user.setUnionMember(true);
            }
            else {
                user.setUnionMember(false);
            }

            user.setUserAdmin(request.getParameter("userAdmin") != null);


            user.setFirstName(request.getParameter("firstname"));
            user.setLastName(request.getParameter("lastname"));
            user.setPassword(request.getParameter("password"));
            user.setPhoneNumber(request.getParameter("phone"));
            user.setEmail(request.getParameter("email"));

            String hashedPassword = hashPassword.encryptThisString(user.getPassword());

            PrintWriter out = response.getWriter();

            Connection db = DBUtils.getINSTANCE().getConnection(out);
            String insertUserCommand = "insert into Users (email, userPass, firstName, lastName, phoneNumber, unionMember, userAdmin) values(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = db.prepareStatement(insertUserCommand);
            statement.setString(1, user.getEmail());
            statement.setString(2, hashedPassword);
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getPhoneNumber());
            statement.setBoolean(6, user.isUnionMember());
            statement.setBoolean(7, user.isUserAdmin());

            statement.executeUpdate();
            statement.close();
            db.close();

            out.println("<html>");
            out.print("<head>");
            out.print("</head>");
            out.println("<body>");
            out.println("<h1>Registration successful! Click here to login</a></h1>");
            out.println("</body>");
            out.println("</html>");


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}




