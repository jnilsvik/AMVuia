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
/*  by Joachim (though its all copypaste pretty much :/ )

    gets a tool id from landing page and prints all information about it
*/
@WebServlet(name = "GetTool", value = "/GetTool")
public class DetailedToolServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String toolID = request.getParameter("toolID");
        PrintWriter out = response.getWriter();

        try {
            ToolModel tool = getToolmodel(toolID, out);//prints the information!!!!
            out.println(tool.getToolName());
            out.println(tool.getToolCategory());
            out.println(tool.getPriceFirst());
            out.println(tool.getPriceAfter());
            out.println(tool.getMaintenance());

            //
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

    //yes this is supposed to be elsewhere. i have it here to to test... also bc its private ... its also breaks when i try to use the other code
    //takes inn the tool ID, searches the db for any tool with a matching toolID, return all info about this tool
    private ToolModel getToolmodel(String toolID, PrintWriter out) throws SQLException {
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
                    rs.getInt("certificateID"));
        }
        return model;
    }

    //copypaste form paul bc its private :/
    private void printData(ToolModel tool, LinkedList<LocalDate> days, PrintWriter out){
        HeaderFooter.printHeader("Detailed Tool Information", out);
        out.println("<h2>Name: "+tool.getToolName()+"</h2>");
        out.println("<p>ToolType: "+tool.getToolCategory()+"</p>");
        out.println("<p>Is Broken: "+tool.getMaintenance()+"</p>");
        out.println("<h3>UsedDates:</h3>");
        for(LocalDate day: days){
            out.println("<p>"+day.getDayOfMonth()+"."+day.getMonth()+"</p>");
        }
        HeaderFooter.printFooter(out);
    }
    private void printWrongIndex(PrintWriter out){
        HeaderFooter.printHeader("Detailed Tool Information", out);
        out.println("<h2>There is no data stored for this item</h2>");
        out.println("<p>Try to refresh the page or enter another index in order to get information</p>");
        HeaderFooter.printFooter(out);
    }

    private void printError(Exception e, PrintWriter out){
        HeaderFooter.printHeader("Detailed Tool Information", out);
        out.println("<h2>An internal Error happend</h2>");
        out.println("<p>" + e.getMessage() + "</p>");
        HeaderFooter.printFooter(out);
    }
}

