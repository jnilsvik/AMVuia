package bacit.web;

import bacit.web.*;
import bacit.web.a_models.ToolModel;
import bacit.web.a_models.UserModel;
import bacit.web.utils.DBUtils;
import bacit.web.utils.hashPassword;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet(name = "RemoveUser", value = "/removeuser")
public class RemoveUser extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print("<html>" +
                "<head>" +
                "<title>Remove User</title>" +
                "</head>" +
                "<body>" +
                "<h2>Please enter the email of the user you want to remove</h2>" +
                "<form method='POST'>" +
                "<h3>Email:</h3>" +
                "<textarea id='input' name='input' rows='1' cols='50'></textarea><br><br>" +
                "<input type = 'submit' value = 'Remove!'>" +
                "</form>" +
                "</body>" +
                "</html>");
        try {
            Connection conn = DBUtils.getNoErrorConnection();
            String a = "Select * FROM AMVUser; ";
            PreparedStatement statements = conn.prepareStatement(a);
            ResultSet rs = statements.executeQuery();
            out.println("<html><table>");
            while (rs.next()) {
                String Email = rs.getString("email");
                out.println("<tr><td>" + Email + "</td></tr>");
            }
            out.println("</table></html>");
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            Connection conn = DBUtils.getNoErrorConnection();
            PreparedStatement statement = conn.prepareStatement("DELETE FROM AMVUser WHERE email = ? ");
            statement.setString(1, String.valueOf(request.getParameter("input")));
            int noOfAffectedRows = statement.executeUpdate();
            if (noOfAffectedRows == 0) {
                out.println("<html><body>");
                out.println("<h1>Nothing is deleted<h1>");
                out.println("</body></html>");
            } else {
                String a = "Select * FROM AMVUser; ";
                PreparedStatement statements = conn.prepareStatement(a);
                ResultSet rs = statements.executeQuery();
                out.println("<html><table>");
                out.println("<h1>User Deleted!</h1>");
                while (rs.next()) {
                    String Email = rs.getString("email");
                    out.println("<tr><td>" + Email + "</td></tr>");
                }
                out.println("</table></html>");
                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
