package bacit.web.AdminFunctions;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "AdminPage", value = "/admin")
public class AdminPage extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        if(SessionCheck.isAdmin(SessionCheck.checkEmail(request,response))){
            try {
                request.getRequestDispatcher("/jspFiles/AdminFunctions/adminPage.jsp").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }else {
            request.getRequestDispatcher("/jspFiles/AdminFunctions/noAdminAccount.jsp").forward(request,response);
        }
    }
}
