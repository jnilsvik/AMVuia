package bacit.web.LogInOut;

// by Dilan
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
@WebServlet(name = "logout", value = "/logout")
public class LogOut extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession(false);
            session.invalidate();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        try {
            request.getRequestDispatcher("/jspFiles/LogIn/login.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
