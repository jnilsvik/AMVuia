package bacit.web.LoginRegister;

import bacit.web.utils.DBUtils;
import bacit.web.a_models.UserModel;
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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        printPage(out);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            UserModel user = new UserModel();
            if(request.getParameter("union") != null) {
                user.setUnionMember(true);
            } else {
                user.setUnionMember(false);
            }

            user.setUserAdmin(request.getParameter("admin") != null);
            user.setFirstname(request.getParameter("fname"));
            user.setLastname(request.getParameter("lname"));
            user.setPassword(hashPassword.encryptThisString(request.getParameter("password")));
            user.setPhoneNumber(request.getParameter("phone"));
            user.setEmail(request.getParameter("email"));

            Connection db = DBUtils.getNoErrorConnection(out);
            PreparedStatement statement = db.prepareStatement(
                    "insert into AMVUser (email, passwordHash, firstName, lastName, phoneNumber, unionMember, userAdmin) values(?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstname());
            statement.setString(4, user.getLastname());
            statement.setString(5, user.getPhoneNumber());
            statement.setBoolean(6, user.isUnionMember());
            statement.setBoolean(7, user.isUserAdmin());
            statement.executeUpdate();
            statement.close();
            db.close();

            out.println("<html><body>");
            out.println("<a href=\"login\"><h1>Registration successful! Click here to login</a></h1></a>");
            out.println("</body></html>");

        }
        catch (Exception e) {
            e.printStackTrace();
            out.print("test");
        }
    }
    private void printPage(PrintWriter out){
        out.print("<html>\n" +
                "<head>\n" +
                "    <title>Title</title>\n" +
                "\n" +
                "    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>\n" +
                "    <style> .page {\n" +
                "        flex-grow: 1;\n" +
                "        width: 100%;\n" +
                "        display: flex;\n" +
                "        justify-content: center;\n" +
                "        align-items: center;\n" +
                "        padding-bottom: 70px;\n" +
                "\n" +
                "        background-image: url(https://media.discordapp.net/attachments/472062607646261249/702987431653277705/unknown.png);\n" +
                "        background-repeat: no-repeat;\n" +
                "        background-attachment: fixed;\n" +
                "        background-size: 100% 100%;\n" +
                "    }\n" +
                "    .amv-register {\n" +
                "        width: 480px;\n" +
                "        background: #fff;\n" +
                "        box-shadow: 0 25px 75px rgba(16, 30, 54, .25);\n" +
                "        border-radius: 6px;\n" +
                "        padding: 30px 60px 26px;\n" +
                "        margin-top: -75px;\n" +
                "    }\n" +
                "</style>\n" +
                "\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class='page'>\n" +
                "    <article class='my-3 amv-register' id='floating-labels'>\n" +
                "        <div class='bd-heading sticky-xl-top align-self-start mt-5 mb-3 mt-xl-0 mb-xl-2'>\n" +
                "            <h3>Register</h3>\n" +
                "        </div>\n" +
                "\n" +
                "        <div>\n" +
                "            <div class='bd-example'>\n" +
                "                <form action='register' method='POST'>\n" +
                "                    <div class='form-floating mb-3'>\n" +
                "                        <input type='email' class='form-control' id='email' name='email' placeholder='name@example.com'>\n" +
                "                        <label for='email'>Email address</label>\n" +
                "                    </div>\n" +
                "                    <div class='form-floating mb-3'>\n" +
                "                        <input type='password' class='form-control' id='password' name='password' placeholder='Password'>\n" +
                "                        <label for='password'>Password</label>\n" +
                "                    </div>\n" +
                "                    <div class='form-floating mb-3'>\n" +
                "                        <input type='text' class='form-control' id='fName' name='fname'placeholder='name'>\n" +
                "                        <label for='fName'>First name</label>\n" +
                "                    </div>\n" +
                "                    <div class='form-floating mb-3'>\n" +
                "                        <input type='text' class='form-control' id='lName' name='lname' placeholder='name'>\n" +
                "                        <label for='lName'>Last name</label>\n" +
                "                    </div>\n" +
                "                    <div class='form-floating mb-3'>\n" +
                "                        <input type='tel' class='form-control' id='phone' name='phone' placeholder='98979695'>\n" +
                "                        <label for='phone'>Phone Number</label>\n" +
                "                    </div>\n" +
                "                    <div class='mb-3 form-check'>\n" +
                "                        <input type='checkbox' class='form-check-input' id='admin' name='admin'>\n" +
                "                        <label class='form-check-label' for='admin'>Administrator</label>\n" +
                "                    <div class='mb-3 form-check'>\n" +
                "                    </div>\n" +
                "                        <input type='checkbox' class='form-check-input' id='union' name='union'>\n" +
                "                        <label class='form-check-label' for='union'>Union member</label>\n" +
                "                    </div>\n" +
                "                    <div class='col-12' >\n" +
                "                        <button class='btn btn-primary' style='width: 100%' type='submit'>Register</button>\n" +
                "                    </div>\n" +
                "                    <div style='text-align:center'>\n" +
                "                        <a href='login'>Already have an account? Login here!</a>\n" +
                "                    </div>\n" +
                "                </form>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </article>\n" +
                "</div>");
    }
}




