package bacit.web;

import bacit.web.utils.DBUtils;
import bacit.web.z_JSP_cleared.AdminAccess;

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
            /*
            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("email");

            if (AdminAccess.accessRights(email)) {*/
                Class.forName("org.mariadb.jdbc.Driver");
                Connection db = DriverManager.getConnection("jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");
                PreparedStatement ps = db.prepareStatement("SELECT toolCategory FROM Tool GROUP BY toolCategory");
                ResultSet rs = ps.executeQuery();

                PreparedStatement ps1 = db.prepareStatement("SELECT * FROM ToolCertificate");
                ResultSet rs1 = ps1.executeQuery();

                out.print("<html>");
                out.print("<head>");
                out.print("<title>Register Tool</title>");
                out.print("</head>");

                out.print("<h2>Register Tool</h2>");
                out.print("<form action = 'toolregister' method = 'POST'> ");
                out.print("<label for = 'toolname'>Tool Name: </label><br>");
                out.print("<input type = 'text' name = 'toolname'><br>");
                out.print("<label for = 'pricefirst'>Price First Day: </label><br>");
                out.print("<input type = 'text' name = 'pricefirst'><br>");
                out.print("<label for = 'priceafter'>Price After First Day: </label><br>");
                out.print("<input type = 'text' name = 'priceafter'><br>");

                out.print("<label for = 'toolCategory'>Tool Category: </label><br>");
                out.print("<select name = 'toolCategory' id = 'toolCategory'><br>");

                while (rs.next()) {
                    String categoryName = rs.getString("toolCategory");
                    out.print("<option value = '" + categoryName + "'> " + categoryName + " </option>");
                }
                out.print("</select>");
                out.print("<br>");
                out.print("<br>");

                out.print("<label for = 'toolcertificate'>Tool Certificate: </label><br>");
                out.print("<select name = 'toolcertificate' id = 'toolcertificate'><br>");

                while (rs1.next()) {
                    int certificateID = rs1.getInt("certificateID");
                    String certificateName = rs1.getString("certificateName");
                    out.print("<option value = '" + certificateID + "'> " + certificateName + " </option>");
                }

                out.print("</select>");
                out.print("<br>");
                out.print("<br>");
                out.print("<label for = 'tooldesc'>Tool Description: </label><br>");
                out.print("<input type = 'text' name = 'tooldesc'><br>");
                out.print("<input type = 'submit' value = 'Register Tool'>");
                out.print("</form>");
                out.print("</body>");
                out.print("</html>");
                db.close();

        } catch (Exception e) {
            out.print("error");
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {

            Connection db = DBUtils.getNoErrorConnection();
            PreparedStatement statement = db.prepareStatement(
                    "insert into Tool (toolName, maintenance, priceFirst, priceAfter, toolCategory, certificateID, toolDescription) values(?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, request.getParameter("toolname"));
            statement.setBoolean(2, false);
            statement.setInt(3, Integer.parseInt(request.getParameter("pricefirst")));
            statement.setInt(4, Integer.parseInt(request.getParameter("priceafter")));
            statement.setString(5, request.getParameter("toolCategory"));
            statement.setString(6, request.getParameter("toolcertificate"));
            statement.setString(7, request.getParameter("tooldesc"));
            statement.executeUpdate();

            out.print("<html>");
            out.print("<head>");
            out.print("</head>");
            out.print("<body>");
            out.print("<h1> Tool suceccfully registered</h1>");
            out.print("</body>");
            out.print("</html>");
            db.close();

        }
        catch (Exception e) {
            out.println(e);
        }
    }

}




