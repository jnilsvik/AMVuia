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
        HttpSession session = request.getSession(false);
        session.invalidate();
        try {
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
