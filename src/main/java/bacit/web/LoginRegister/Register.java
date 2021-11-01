package bacit.web.LoginRegister;

import bacit.web.utils.DBUtils;
import bacit.web.models.UserModel;

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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html>" +
                    "<head>" +
                    "<title>Register user form</title>" +
                    "</head>" +
                    "<body>" +
                        "<h2>Register User</h2>" +
                        "<form action = 'register' method = 'POST'> " +
                            "<label for = 'firstname'>First Name: </label><br>" +
                            "<input type = 'text' name = 'firstname' required><br>" +
                            "<label for = 'lastname'>Last Name: </label><br>" +
                            "<input type = 'text' name = 'lastname' required><br>" +
                            "<label for = 'password'>Password: </label><br>" +
                            "<input type = 'password' name = 'password' required><br>" +
                            "<label for = 'email'>Email: </label><br>" +
                            "<input type = 'email' name = 'email' required><br>" +
                            "<label for = 'phone'>Phone: </label><br>" +
                            "<input type = 'tel' name = 'phone' required pattern=\"[0-9]{5,9}\"><br>" +
                            "<label for = 'unionmember'>Union Member: </label><br>" +
                            "<input type = 'checkbox' name = 'unionmember' value = 'true'><br>" +
                            "<label for = 'userAdmin'>Admin: </label><br>" +
                            "<input type = 'checkbox' name = 'userAdmin' value = 'true'><br>" +
                            "<input type = 'submit' value = 'Register User'>" +
                        "</form>" +
                    "</body>" +
                    "</html>");
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


            user.setFirstname(request.getParameter("firstname"));
            user.setLastname(request.getParameter("lastname"));
            user.setPassword(request.getParameter("password"));
            user.setPhoneNumber(request.getParameter("phone"));
            user.setEmail(request.getParameter("email"));

            String hashedPassword = hashPassword.encryptThisString(user.getPassword());

            PrintWriter out = response.getWriter();

            Connection db = DBUtils.getNoErrorConnection(out);
            String insertUserCommand = "insert into AMVUser (email, passwordHash, firstName, lastName, phoneNumber, unionMember, userAdmin) values(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = db.prepareStatement(insertUserCommand);
            statement.setString(1, user.getEmail());
            statement.setString(2, hashedPassword);
            statement.setString(3, user.getFirstname());
            statement.setString(4, user.getLastname());
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
            out.println("<a href=\"login\"><h1>Registration successful! Click here to login</a></h1></a>");
            out.println("</body>");
            out.println("</html>");


        }
        catch (Exception e) {
            e.printStackTrace();
            PrintWriter out = response.getWriter();
            out.print("test");
        }
    }

}




