package bacit.web;

import bacit.web.utils.DBUtils;

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

                out.print("<html>");
                out.print("<head>");
                out.print("<title>Give a user a certificate</title>");
                out.print("</head>");

                out.print("<h2>Register User</h2>");
                out.print("<form action = 'givecertificate' method = 'POST'> ");
                out.print("<label for = 'userID'>User ID: </label><br>");
                out.print("<input type = 'text' name = 'userID'><br>");
                out.print("<label for = 'accomplishdate'>Accomplish Date: </label><br>");
                out.print("<input type = 'date' name = 'accomplishdate'><br>");


                out.print("<label for = 'certificateID'>Tool Certificate: </label><br>");
                out.print("<select name = 'certificateID' id = 'certificateID'><br>");

                while (rs.next()) {

                    String certificateName = rs.getString("certificateName");
                    int certificateID = rs.getInt("certificateID");

                    out.print("<option value = '" + certificateID + "'> " + certificateName + " </option>");

                }
                out.print("</select>");
                out.print("<br>");
                out.print("<br>");

                out.print("<input type = 'submit' value = 'Register User'>");
                out.print("</form>");

                out.print("</body>");
                out.print("</html>");
            } else {
                out.print("<h1> Sorry, you don't have access to this page");
            }

        } catch (Exception e) {
            out.print("error");
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

            out.print("<html>");
            out.print("<head>");
            out.print("</head>");
            out.print("<body>");
            out.print("<h1> Task successful!</h1>");
            out.print("</body>");
            out.print("</html>");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}




