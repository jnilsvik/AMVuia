package bacit.web.adminPages;

import bacit.web.bacit_database.DBUtils;
import bacit.web.LoginRegister.AdminAccess;

import java.io.PrintWriter;
import java.sql.Connection;
import java.io.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan
@WebServlet(name = "ToolMaintenanceIn", value = "/toolmaintenancein")
public class ToolMaintenanceIn extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {

            Connection db = DBUtils.getNoErrorConnection(out);
            String insertUserCommand = "UPDATE Tool SET maintenance = true WHERE toolID = ?";
            PreparedStatement statement = db.prepareStatement(insertUserCommand);
            statement.setString(1, request.getParameter("toolID"));
            statement.executeUpdate();

            out.println("<html>");
            out.print("<head>");
            out.print("</head>");
            out.println("<body>");
            out.println("<h1> Tool successfully put in maintenance</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}





