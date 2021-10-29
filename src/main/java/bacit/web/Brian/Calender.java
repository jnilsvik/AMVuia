package bacit.web.Brian;


import bacit.web.utils.DBUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet(name = "c", value = "/c")
public class Calender extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        Connection dbConnecton = DBUtils.getNoErrorConnection(out);
/*
        Connection dbConnecton = DBUtils.getNoErrorConnection(out);
        String toolQ = "select * from Tool where ToolID = ?";
        PreparedStatement statement = dbConnecton.prepareStatement(toolQ);
        statement.setString(1, toolID);
 */

        response.setContentType("text/html");


        out.println("<html><body>");
        out.println("<h1></h1>");
        out.println("<h1>Find Tool by id in the database :-)</h1>");
        out.println("<form action='GetTool' method='GET'>");
        out.println("  <label for='toolID'>Tool ID:</label>");
        out.println("  <input type='number' name='toolID'/>");
        out.println("  <input type='submit' />");
        out.println("</form>");
    }
}

