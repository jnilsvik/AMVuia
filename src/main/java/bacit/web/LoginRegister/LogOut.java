package bacit.web.LoginRegister;

// by Dilan
import java.io.PrintWriter;
import java.sql.*;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
@WebServlet(name = "logout", value = "/logout")
public class LogOut extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        HttpSession session = request.getSession(false);
        session.invalidate();

        out.println("<html>");
        out.print("<head>");
        out.print("</head>");
        out.println("<body>");
        out.println("<h1>You are now logged out</h1>");
        out.println("<a href = 'http://localhost:8081/bacit-web-1.0-SNAPSHOT/login'> Take me to the login page</a>");
        out.println("</body>");
        out.println("</html>");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");


    }

}
