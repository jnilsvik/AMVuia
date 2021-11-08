package bacit.web.z_scraped;
import bacit.web.utils.hashPassword;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// by Dilan
@WebServlet(name = "xLogin", value = "/xlogin")
public class Login extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        try {
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
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
            out.println("<a href = 'toolcategories'> Browse Tools</a>");
            out.println("<br>");
            out.println("<a href = 'logout'> Log out</a>");

            out.println("</body>");
            out.println("</html>");

        }
        else
        {
            out.println("Username or Password incorrect");

        }
    }


}