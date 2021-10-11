package bacit.web.bacit_web;

import bacit.web.bacit_headerFooter.HeaderFooter;
import bacit.web.bacit_models.ToolModel;

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

        PrintWriter out = response.getWriter();
        String toolID = request.getParameter("toolID");
        try {
            ToolModel tool = ToolModel.getToolModel(toolID);
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

    private String getToolType(int toolTypeID, PrintWriter out) throws SQLException {
        Connection dbConnection = getConnection();
        String query = "select * from ToolType where toolTypeID = ?;";
        PreparedStatement statement = dbConnection.prepareStatement(query);
        statement.setInt(1, toolTypeID);
        ResultSet rs = statement.executeQuery();
        if(!rs.next()) throw new IllegalArgumentException("No tool type with this ID");
        return rs.getString("toolTypeName");
    }

    private void printData(ToolModel tool, LinkedList<LocalDate> days, PrintWriter out){
        HeaderFooter.printHeader("Detailed Tool Information", out);
        out.println("<h2>Name: "+tool.getName()+"</h2>");
        out.println("<p>ToolType: "+tool.getToolType()+"</p>");
        out.println("<p>Location: "+tool.getLocation()+"</p>");
        out.println("<p>Status: "+tool.getStatus()+"</p>");
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

    private Connection getConnection(){
        Connection dbConnection = null;

        try{
            dbConnection = DBUtils.getINSTANCE().getConnection();
        } catch (SQLException | ClassNotFoundException sqlException){
            sqlException.printStackTrace();
        }
        return dbConnection;
    }

}
