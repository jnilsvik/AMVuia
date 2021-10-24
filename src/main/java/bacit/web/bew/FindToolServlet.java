package bacit.web.bew;

import java.io.*;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "findTool", value = "/findTool")
public class FindToolServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h1></h1>");
        out.println("<h1>Find Tool by id in the database :-)</h1>");
        out.println("<form action='GetTool' method='GET'>");
        out.println("  <label for='toolID'>Tool ID:</label>");
        out.println("  <input type='number' name='toolID'/>");
        out.println("  <input type='submit' />");
        out.println("</form>");

        out.println("<h1>Find Tool by name in the database :-)</h1>");
        out.println("<form action='GetTool2' method='GET'>");
        out.println("  <label for='toolName'>Tool ID:</label>");
        out.println("  <input type='text' name='toolName'/>");
        out.println("  <input type='submit' />");
        out.println("</form>");

    }

    public void destroy() {
    }
}