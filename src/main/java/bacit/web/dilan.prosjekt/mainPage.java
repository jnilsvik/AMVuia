package bacit.web.dilan.prosjekt;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

// by Dilan
@WebServlet(name = "MainPage", value = "/mainpage")
public class mainPage extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        try {

            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("email");

            PrintWriter out = response.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Register user form</title>");
            out.println("</head>");
            out.println("<body>");

            if (AdminAccess.accessRights(email)) {
                out.println("<h1> HALLA DU ER ADMIN</h1>");
            } else {
                out.println("<h1>You dont have access to this</h1>");
            }

            out.println("</body>");
            out.println("</html>");

        }

        catch(Exception e){
                e.printStackTrace();
            }
        }



    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
    }


}