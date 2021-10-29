package bacit.web.LoginRegister;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// by Dilan
@WebServlet(name = "Login", value = "/login")
public class Login extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("   <title>Register user form</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("   <form action = 'login' method = 'POST'>");
        out.println("       <label for = 'email' required>Email: </label><br>");
        out.println("       <input type = 'email' name = 'email'><br>");
        out.println("       <label for = 'pass' required>Password: </label><br>");
        out.println("       <input type = 'password' name = 'pass'><br>");
        out.println("       <input type = 'submit' value = 'Login User'><br>");
        out.println("       <a href='register'>Don't have an account already? Register here!</a>");
        out.println("   </form>");
        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String pass = hashPassword.encryptThisString(request.getParameter("pass"));

        if(Validate.checkUser(email, pass))
        {
            HttpSession session=request.getSession();
            session.setAttribute("email", email);

            out.println("<html>");
            out.print("<head>");
            out.print("</head>");
            out.println("<body>");
            out.println("<header>");
            out.println("<h2> Welcome </h2>");
            out.println("</header>");
            out.println("Welcome " + email);
            out.println("<br>");
            out.println("<a href = 'http://localhost:8081/bacit-web-1.0-SNAPSHOT/toolcategories'> Browse Tools</a>");
            out.println("<br>");
            out.println("<a href = 'http://localhost:8081/bacit-web-1.0-SNAPSHOT/logout'> Log out</a>");

            out.println("</body>");
            out.println("</html>");

        }
        else
        {
            out.println("Username or Password incorrect");

        }
    }


}