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
@WebServlet(name = "ToolRegister", value = "/toolregister")
public class ToolRegister extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("email");

            if (AdminAccess.accessRights(email)) {

                Class.forName("org.mariadb.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");

                PreparedStatement ps = con.prepareStatement("SELECT toolCategory FROM Tool GROUP BY toolCategory");
                ResultSet rs = ps.executeQuery();

                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM ToolCertificate");
                ResultSet rs1 = ps1.executeQuery();

                out.println("<html>");
                out.println("<head>");
                out.println("<title>Register Tool</title>");
                out.println("</head>");

                out.println("<h2>Register User</h2>");
                out.println("<form action = 'toolregister' method = 'POST'> ");
                out.println("<label for = 'toolname'>Tool Name: </label><br>");
                out.println("<input type = 'text' name = 'toolname'><br>");
                out.println("<label for = 'pricefirst'>Price First Day: </label><br>");
                out.println("<input type = 'text' name = 'pricefirst'><br>");
                out.println("<label for = 'priceafter'>Price After First Day: </label><br>");
                out.println("<input type = 'text' name = 'priceafter'><br>");

                out.println("<label for = 'category'>Tool Category: </label><br>");
                out.print("<select name = 'category' id = 'category'><br>");

                while (rs.next()) {

                    String categoryName = rs.getString("toolCategory");
                    out.print("<option value = '" + categoryName + "'> " + categoryName + " </option>");

                }
                out.print("</select>");
                out.println("<br>");
                out.println("<br>");

                out.println("<label for = 'toolcertificate'>Tool Certificate: </label><br>");
                out.print("<select name = 'toolcertificate' id = 'toolcertificate'><br>");

                while (rs1.next()) {

                    int certificateID = rs1.getInt("certificateID");
                    String certificateName = rs1.getString("certificateName");
                    out.print("<option value = '" + certificateID + "'> " + certificateName + " </option>");

                }
                out.print("</select>");
                out.println("<br>");
                out.println("<br>");

                out.println("<label for = 'tooldesc'>Tool Description: </label><br>");
                out.println("<input type = 'text' name = 'tooldesc'><br>");
                out.println("<input type = 'submit' value = 'Register User'>");
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
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {

            Connection db = DBUtils.getNoErrorConnection(out);
            String insertUserCommand = "insert into Tool (toolName, maintenance, priceFirst, priceAfter, toolCategory, certificateID, toolDescription) values(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = db.prepareStatement(insertUserCommand);
            statement.setString(1, request.getParameter("toolname"));
            statement.setBoolean(2, false);
            statement.setString(3, request.getParameter("pricefirst"));
            statement.setString(4, request.getParameter("priceafter"));
            statement.setString(5, request.getParameter("category"));
            statement.setString(6, request.getParameter("toolcertificate"));
            statement.setString(7, request.getParameter("tooldesc"));
            statement.executeUpdate();

            out.println("<html>");
            out.print("<head>");
            out.print("</head>");
            out.println("<body>");
            out.println("<h1> Tool suceccfully registered</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}




