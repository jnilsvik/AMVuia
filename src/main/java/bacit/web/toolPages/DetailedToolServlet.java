package bacit.web.toolPages;

//by Paul

import bacit.web.utils.PageElements;
import bacit.web.models.ToolModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DetailedToolServlet", value = "/details")
public class DetailedToolServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String toolID = request.getParameter("toolID");
        PrintWriter out = response.getWriter();
        try {
            ToolModel tool = ToolModel.getToolModel(toolID, out);
            LinkedList<LocalDate> usedDates = tool.getUsedDates(out);
            printData(tool, usedDates, out);
        } catch (SQLException e) {
            //Error in the database
            printError(e, out);
        } catch (IllegalArgumentException e){
            //For the index no data in the database could be found
            printWrongIndex(out);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private void printData(ToolModel tool, LinkedList<LocalDate> days, PrintWriter out){
        PageElements.printHeader("Detailed Tool Information", out);
        out.println("<h2>Name: "+tool.getToolName()+"</h2>");
        out.println("<p>ToolType: "+tool.getToolCategory()+"</p>");
        out.println("<p>Is Broken: "+tool.getMaintenance()+"</p>");
        out.println("<h3>UsedDates:</h3>");
        for(LocalDate day: days){
            out.println("<p>"+day.getDayOfMonth()+"."+day.getMonth()+"</p>");
        }
        PageElements.printFooter(out);
    }

    private void printWrongIndex(PrintWriter out){
        PageElements.printHeader("Detailed Tool Information", out);
        out.println("<h2>There is no data stored for this item</h2>");
        out.println("<p>Try to refresh the page or enter another index in order to get information</p>");
        PageElements.printFooter(out);
    }

    private void printError(Exception e, PrintWriter out){
        PageElements.printHeader("Detailed Tool Information", out);
        out.println("<h2>An internal Error happend</h2>");
        out.println("<p>" + e.getMessage() + "</p>");
        PageElements.printFooter(out);
    }
}
