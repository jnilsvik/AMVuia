package bacit.web;

import bacit.web.bacit_database.DBUtils;
import bacit.web.bacit_models.ToolModel;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBQ {

    public static ToolModel getToolModel(String toolName, PrintWriter out) throws SQLException {
        Connection dbConnection = DBUtils.getNoErrorConnection(out);
        String toolQ = "select * from Tool where toolName like ?";
        PreparedStatement statement = dbConnection.prepareStatement(toolQ);
        statement.setString(1, "%"+ toolName +"%");

        ResultSet rs = statement.executeQuery();
        ToolModel model = null;

        out.println("<!DOCTYPE html>" +
                "<head>" +
                "  <title>Sorting Tables w/ JavaScript</title>" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />" +
                "  <meta charset=\"utf-8\" />" +
                "</head>" +
                "<body>" +
                "    <h3>Tool Stuff</h3>" +
                "    <table>" +
                "        <tr>" +
                "            <th>toolName</th>" +
                "            <th>category</th>" +
                "            <th>priceFirst</th>" +
                "            <th>priceAfter</th>" +
                "            <th>maintenance</th>" +
                "            <th>certificateID</th>");
        while (rs.next()) {
            model = new ToolModel(
                    rs.getInt("toolID"),
                    rs.getString("toolName"),
                    rs.getString("toolCategory"),
                    rs.getBoolean("maintenance"),
                    rs.getInt("priceFirst"),
                    rs.getInt("priceAfter"),
                    rs.getInt("certificateID"),
                    rs.getString("description"));

            out.println("<tr>");
            out.println("<td>" + model.getToolName() + "</th>");
            out.println("<td>" + model.getToolCategory() + "</th>");
            out.println("<td>" + model.getPriceFirst() + "</th>");
            out.println("<td>" + model.getPriceAfter() + "</th>");
            out.println("<td>" + model.getMaintenance() + "</th>");
            out.println("<td>" + model.getCertificateID() + "</th>");
            out.println("</tr>");
        }
        return model;
    }

    public static ToolModel getToolModelByID(String toolID, PrintWriter out) throws SQLException {
        Connection dbConnecton = DBUtils.getNoErrorConnection(out);
        String toolQ = "select * from Tool where ToolID = ?";
        PreparedStatement statement = dbConnecton.prepareStatement(toolQ);
        statement.setString(1, toolID);

        ResultSet rs = statement.executeQuery();
        ToolModel model = null;

        while (rs.next()) {
            model = new ToolModel(
                    rs.getInt("toolID"),
                    rs.getString("toolName"),
                    rs.getString("toolCategory"),
                    rs.getBoolean("maintenance"),
                    rs.getInt("priceFirst"),
                    rs.getInt("priceAfter"),
                    rs.getInt("certificateID"),
                    rs.getString("description"));
        }
        return model;
    }
}
