package bacit.web.z_JSP_cleared.WIP.toolPages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "xToolCategoryServlet", value = "/xtoolcategories")
public class ToolCategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        try {
            request.getRequestDispatcher("/-toolALL.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

}








