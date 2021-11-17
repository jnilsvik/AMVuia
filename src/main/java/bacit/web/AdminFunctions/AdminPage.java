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
        HttpSession session=request.getSession(false);
        String email = (String) session.getAttribute("email");
        if(email == null){
            response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
            return;
        }

        if(AdminAccess.accessRights(email)) {
            try {
                request.getRequestDispatcher("/adminPage.jsp").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }else {
            request.getRequestDispatcher("/NoAdminAccount.jsp").forward(request,response);
        }
    }
}
