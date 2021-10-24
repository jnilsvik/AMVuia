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
public class ToolListServlet extends HttpServlet {
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
                        "<head>" +
                        "  <title>Sorting Tables w/ JavaScript</title>" +
                        "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />" +
                        "  <meta charset=\"utf-8\" />" +
                        "  <style>" +
                        "    .table-sortable th {" +
                        "      cursor: pointer;" +
                        "    .table-sortable .th-sort-asc::after {" +
                        "      content: \"\\25b4\";" +
                        "    }" +
                        "    .table-sortable .th-sort-desc::after {" +
                        "      content: \"\\25be\";" +
                        "    }" +
                        "    .table-sortable .th-sort-asc::after," +
                        "    .table-sortable .th-sort-desc::after {" +
                        "      margin-left: 5px;" +
                        "    }" +
                        "    .table-sortable .th-sort-asc," +
                        "    .table-sortable .th-sort-desc {" +
                        "      background: rgba(0, 0, 0, 0.1);" +
                        "    }" +
                        "  </style>" +
                        "  <script>" +
                        "    function sortTableByColumn(table, column, asc = true) {" +
                        "      const dirModifier = asc ? 1 : -1;" +
                        "      const tBody = table.tBodies[0];" +
                        "      const rows = Array.from(tBody.querySelectorAll(\"tr\"));" +
                        "      // Sort each row" +
                        "      const sortedRows = rows.sort((a, b) => {" +
                        "        const aColText = a.querySelector(`td:nth-child(${ column + 1 })`).textContent.trim();" +
                        "        const bColText = b.querySelector(`td:nth-child(${ column + 1 })`).textContent.trim();" +
                        "        return aColText > bColText ? (1 * dirModifier) : (-1 * dirModifier);" +
                        "      });" +
                        "      // Remove all existing TRs from the table" +
                        "      while (tBody.firstChild) {" +
                        "        tBody.removeChild(tBody.firstChild);" +
                        "      }" +
                        "      // Re-add the newly sorted rows" +
                        "      tBody.append(...sortedRows);" +
                        "" +
                        "      // Remember how the column is currently sorted" +
                        "      table.querySelectorAll(\"th\").forEach(th => th.classList.remove(\"th-sort-asc\", \"th-sort-desc\"));" +
                        "      table.querySelector(`th:nth-child(${ column + 1})`).classList.toggle(\"th-sort-asc\", asc);" +
                        "      table.querySelector(`th:nth-child(${ column + 1})`).classList.toggle(\"th-sort-desc\", !asc);" +
                        "    }" +

                        "    document.querySelectorAll(\".table-sortable th\").forEach(headerCell => {" +
                        "      headerCell.addEventListener(\"click\", () => {" +
                        "        const tableElement = headerCell.parentElement.parentElement.parentElement;" +
                        "        const headerIndex = Array.prototype.indexOf.call(headerCell.parentElement.children, headerCell);" +
                        "        const currentIsAscending = headerCell.classList.contains(\"th-sort-asc\");" +

                        "        sortTableByColumn(tableElement, headerIndex, !currentIsAscending);" +
                        "      });" +
                        "    });" +
                        "  </script>" +
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
                        "            <th>certificateID</th>" +
                        "        </tr>");
            //create a tool model as long as there are RS's left
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