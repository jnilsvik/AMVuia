package bacit.web.bacit_web;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//Marius wrote this code

@WebServlet(name = "ToolCategoryServlet", value = "/toolcategories")
public class ToolCategoryServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("Tool Categories");
        out.println("<li><a href=various-tools> View Various Tools</a></li>");
        out.println("<li><a href=nailguns> View Nailguns</a></li>");
        out.println("<li><a href=woodcutting> View Woodcutting</a></li>");
        out.println("<li><a href=car-trailers> View Car Trailers</a></li>");
        out.println("<li><a href=large-equipment> View Large Equipment</a></li>");
        out.println("</body></html>");
    }

}








