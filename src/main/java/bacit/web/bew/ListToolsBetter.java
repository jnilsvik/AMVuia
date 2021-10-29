package bacit.web.bew;

import bacit.web.models.ToolModel;
import bacit.web.utils.DBUtils;
import bacit.web.utils.PageElements;

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
@WebServlet(name = "tlb", value = "/tlb")
public class ListToolsBetter extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            Connection dbConnection = DBUtils.getNoErrorConnection(out);
            String toolQ = "select * from Tool order by toolID ";
            PreparedStatement statement = dbConnection.prepareStatement(toolQ);
            ResultSet rs = statement.executeQuery();
            ToolModel model;

            //HTML SPAM!
            out.println("<!DOCTYPE html>");
            out.println("<head>");
            out.println("  <title>Sorting Tables w/ JavaScript</title>");
            out.println("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />");
            out.println("  <meta charset=\"utf-8\" />");
            out.println("  <link rel=\"stylesheet\" href=\"CSS/list.css\">" );
            out.println("</head>");
            out.println("<body>");
            out.println("<section class=\"main\">");
            out.println("    <h3>Tool Stuff</h3>");
            out.println("    <section class='featured-products'>");


            //create a tool model as long as there are RS's left
            while (rs.next()) {
                model = new ToolModel(
                        rs.getInt("toolID"),
                        rs.getString("toolName"),
                        rs.getString("toolCategory"),
                        rs.getBoolean("maintenance"),
                        rs.getInt("priceFirst"),
                        rs.getInt("priceAfter"),
                        rs.getInt("certificateID"),
                        rs.getString("toolDescription"),
                        rs.getString("picturePath"));

                out.println("<div class='featured-product-item'>");
                out.println("    <div");
                out.println("        style='background-image: url(img/"+model.getPicturePath().replaceAll(" ","%20")+");'");
                out.println("        class='featured-product-item-image'");
                out.println("    ></div>");
                out.println("    <p class='title'>");
                out.println(model.getToolName().replaceAll("_"," "));
                out.println("    </p>");
                out.println("    <button>");
                out.println("        View item");
                out.println("    </button>");
                out.println("</div>");
            }
            out.println("</table></section></body></html>");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
}