package bacit.web.adminPages;

import bacit.web.bacit_database.DBUtils;
import bacit.web.LoginRegister.AdminAccess;

import java.io.PrintWriter;
import java.sql.Connection;
import java.io.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan
@WebServlet(name = "GiveCertificate", value = "/givecertificate")
public class GiveCertificate extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("email");

            if (AdminAccess.accessRights(email)) {

                Class.forName("org.mariadb.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");

                PreparedStatement ps = con.prepareStatement("SELECT * FROM ToolCertificate");
                ResultSet rs = ps.executeQuery();

                out.println("<html>");
                out.println("<head>");
                out.println("<title>Give a user a certificate</title>");
                out.println("</head>");

                out.println("<h2>Register User</h2>");
                out.println("<form action = 'givecertificate' method = 'POST'> ");
                out.println("<label for = 'userID'>User ID: </label><br>");
                out.println("<input type = 'text' name = 'userID'><br>");
                out.println("<label for = 'accomplishdate'>Accomplish Date: </label><br>");
                out.println("<input type = 'date' name = 'accomplishdate'><br>");


                out.println("<label for = 'certificateID'>Tool Certificate: </label><br>");
                out.print("<select name = 'certificateID' id = 'certificateID'><br>");

                while (rs.next()) {

                    String certificateName = rs.getString("certificateName");
                    int certificateID = rs.getInt("certificateID");

                    out.print("<option value = '" + certificateID + "'> " + certificateName + " </option>");

                }
                out.print("</select>");
                out.println("<br>");
                out.println("<br>");

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
            LocalDate accomplishDate = LocalDate.parse(request.getParameter("accomplishdate"));

            Connection db = DBUtils.getNoErrorConnection(out);
            String insertUserCommand = "insert into UsersCertificate (userID, certificateID, accomplishDate) values(?, ?, ?)";
            PreparedStatement statement = db.prepareStatement(insertUserCommand);
            statement.setString(1, request.getParameter("userID"));
            statement.setString(2, request.getParameter("certificateID"));
            statement.setObject(3, accomplishDate);
            statement.executeUpdate();

            out.println("<html>");
            out.print("<head>");
            out.print("</head>");
            out.println("<body>");
            out.println("<h1> Task successful!</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}




