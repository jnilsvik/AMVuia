package bacit.web.Admin;

import bacit.web.utils.PageAccess;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "AdminPage", value = "/admin")
public class AdminPage extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(PageAccess.isAdmin(request)) {
            try {
                request.getRequestDispatcher("/jspFiles/AdminFunctions/adminPage.jsp").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }else {
            PageAccess.reDirWOAdmin(request,response);
        }
    }
}
