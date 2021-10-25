package bacit.web.bew;

import bacit.web.bacit_database.DBUtils;
import bacit.web.bacit_models.ToolModel;

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

/*  by Joachim (though its all copypaste pretty much :/ )

    returns a list of tools with a name that correlates with what the user specified in the calling method
*/
@WebServlet(name = "GetTool2", value = "/GetTool2")
public class GetToolsByNameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String toolName = request.getParameter("toolName");
        PrintWriter out = response.getWriter();

        try {
            getToolModel(toolName, out);//prints the information!!!!
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    private ToolModel getToolModel(String toolName, PrintWriter out) throws SQLException {
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
                    rs.getInt("certificateID"));

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
}
