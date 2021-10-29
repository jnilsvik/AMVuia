package bacit.web.adminPages;

import bacit.web.utils.DBUtils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.io.*;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan
@WebServlet(name = "ToolMaintenanceOut", value = "/toolmaintenanceout")
public class ToolMaintenanceOut extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {

            Connection db = DBUtils.getNoErrorConnection(out);
            String insertUserCommand = "UPDATE Tool SET maintenance = false WHERE toolID = ?";
            PreparedStatement statement = db.prepareStatement(insertUserCommand);
            statement.setString(1, request.getParameter("toolID"));
            statement.executeUpdate();

            out.println("<html>");
            out.print("<head>");
            out.print("</head>");
            out.println("<body>");
            out.println("<h1> Tool successfully put out of maintenance</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}





