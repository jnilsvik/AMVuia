package bacit.web.General;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet(name = "Help", value = "/help")
public class Help extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        request.getRequestDispatcher("/jspFiles/Profile/help.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.setAttribute("name", request.getParameter("name"));
        request.setAttribute("message", request.getParameter("message"));
        request.setAttribute("email", request.getParameter("email"));
        request.getRequestDispatcher("/jspFiles/Profile/helpPost.jsp").forward(request,response);
    }

}
