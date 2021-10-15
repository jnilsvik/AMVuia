package bacit.web.bew;

import bacit.web.bacit_database.DBUtils;
import bacit.web.bacit_headerFooter.HeaderFooter;
import bacit.web.bacit_models.ToolModel;
import bacit.web.bacit_models.UserModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
by Joachim

prints all the tools
*/
@WebServlet(name = "tl", value = "/tl")
public class ToolList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            Connection dbConnecton = DBUtils.getNoErrorConnection(out);
            String toolQ = "select * from Tool order by toolID ";
            //could probably change the "toolID" with some variable to have different ordering
            PreparedStatement statement = dbConnecton.prepareStatement(toolQ);
            ResultSet rs = statement.executeQuery();
            ToolModel model = null;

            //HTML SPAM!
            out.println("<!DOCTYPE html>" +
                        "<html lang=\"en\">" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                        "    <title>Document</title>" +
                        "</head>" +
                        "<body>" +
                        "    <h3>Tool Stuff</h3>" +
                        "    <table bgcolor=\"black\">" +
                        "        <tr bgcolor=\"grey\">" +
                        "            <th width=100>toolName</th>" +
                        "            <th width=100>category</th>" +
                        "            <th width=100>priceFirst</th>" +
                        "            <th width=100>priceAfter</th>" +
                        "            <th width=100>maintenance</th>" +
                        "            <th width=100>certificateID</th>" +
                        "        </tr>");
            //create a tool model as lond as there are RS's left
            while (rs.next()) {
                model = new ToolModel(
                        rs.getInt("toolID"),
                        rs.getString("toolName"),
                        rs.getString("toolCategory"),
                        rs.getBoolean("maintenance"),
                        rs.getInt("priceFirst"),
                        rs.getInt("priceAfter"),
                        rs.getInt("certificateID"));
                //prints them to the table
                out.println("<tr bgcolor=\"white\" align=\"center\">");
                out.println("<td>" + model.getToolName() + "</th>");
                out.println("<td>" + model.getToolCategory() + "</th>");
                out.println("<td>" + model.getPriceFirst() + "</th>");
                out.println("<td>" + model.getPriceAfter() + "</th>");
                out.println("<td>" + model.getMaintenance() + "</th>");
                out.println("<td>" + model.getCertificateID() + "</th>");
                out.println("</tr>");
            }
            out.println("</table>" +
                    "</body>" +
                    "</html>);");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
}