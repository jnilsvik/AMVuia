package bacit.web.adminPages;

import java.io.PrintWriter;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan
@WebServlet(name = "ToolMaintenance", value = "/toolmaintenance")
public class ToolMaintenance extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("email");

            if (AdminAccess.accessRights(email)) {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Tool maintenance</title>");
                out.println("</head>");
                out.println("<body>");

                out.println("<h2>Put a tool in maintenance</h2>");
                out.println("<form action = 'toolmaintenancein' method = 'POST'>");
                out.println("<label for = 'toolID'>Tool ID: </label><br>");
                out.println("<input type = 'text' name = 'toolID'><br>");
                out.println("<input type = 'submit' value = 'Submit'>");
                out.println("</form>");
                out.println("<br>");

                out.println("<h2>Put a tool out of maintenance</h2>");
                out.println("<form action = 'toolmaintenanceout' method = 'POST'> ");
                out.println("<label for = 'toolID'>Tool ID: </label><br>");
                out.println("<input type = 'text' name = 'toolID'><br>");
                out.println("<input type = 'submit' value = 'Submit'>");
                out.println("</form>");

                out.println("</body>");
                out.println("</html>");
            } else {
                out.println("<h1> Sorry, you don't have access to this page");
            }

        } catch (Exception e) {
            out.println("error");
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}




