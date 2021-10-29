package bacit.web.bew;

import bacit.web.bacit_database.DBUtils;
import bacit.web.bacit_headerFooter.PageElements;
import bacit.web.bacit_models.UserModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
by Joachim

prints all the tools
*/
@WebServlet(name = "el", value = "/el")
public class ListEmployee extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            PageElements.printSidebar(out);
            out.println("<!DOCTYPE html>" +
                        "<head>" +
                        "  <title>Sorting Tables w/ JavaScript</title>" +
                        "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />" +
                        "  <meta charset=\"utf-8\" />" +
                        "</head>" +
                        "<body>" +
                        "    <h3>User Stuff</h3>" +
                        "    <table>" +
                        "        <tr>" +
                        "            <th>User ID</th>" +
                        "            <th>Firstname</th>" +
                        "            <th>Lastname</th>" +
                        "            <th>Phone nmbr</th>" +
                        "            <th>Union</th>" +
                        "            <th>Admin</th>" +
                        "        </tr>");

            Connection dbConnection = DBUtils.getNoErrorConnection(out);
            String userQ = "select * from AMVUser order by userID ";
            PreparedStatement statement = dbConnection.prepareStatement(userQ);
            ResultSet rs = statement.executeQuery();
            UserModel model = null;
            //create a user model as long as there are RS's left
            while (rs.next()) {
                model = new UserModel(
                        rs.getInt("userID"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("phoneNumber"),
                        "",
                        rs.getBoolean("unionMember"),
                        rs.getBoolean("userAdmin"),
                        rs.getString("email"));
                //prints them to the table
                out.println("<tr>");
                out.println("<td>" + model.getUserID() + "</th>");
                out.println("<td>" + model.getFirstname() + "</th>");
                out.println("<td>" + model.getLastname() + "</th>");
                out.println("<td>" + model.getPhoneNumber() + "</th>");
                out.println("<td>" + model.isUnionMember() + "</th>");
                out.println("<td>" + model.isUserAdmin() + "</th>");
                out.println("</tr>");
            }
            out.println("</table>" +
                    "</body>" +
                    "</html>");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
}