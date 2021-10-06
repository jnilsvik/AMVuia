package bacit.web.bacit_web;

import bacit.web.bacit_models.ToolModel;
import jdk.javadoc.internal.doclets.formats.html.markup.Head;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
            ToolModel tool = getToolFromDB(toolID, out);
            LinkedList<LocalDateTime> usedDates = getUsedDates(tool.getId(), out);
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

    private ToolModel getToolFromDB(String toolID, PrintWriter out)throws SQLException,IllegalArgumentException{
        Connection dbConnection = getConnection(out);
        String query ="select * from Tool where toolID = ?;";
        PreparedStatement statement= dbConnection.prepareStatement(query);
        statement.setString(1, toolID);
        ResultSet rs = statement.executeQuery();
        ToolModel tool = null;
        if(!rs.next()) throw new IllegalArgumentException("No tool with this ID");
        tool = new ToolModel(rs.getInt("toolID"), "Tool name will be added to database", getToolType(rs.getInt("toolTypeID"), out), rs.getString("location"), rs.getString("status"));
        return tool;
    }

    private String getToolType(int toolTypeID, PrintWriter out) throws SQLException {
        Connection dbConnection = getConnection(out);
        String query = "select * from ToolType where toolTypeID = ?;";
        PreparedStatement statement = dbConnection.prepareStatement(query);
        statement.setInt(1, toolTypeID);
        ResultSet rs = statement.executeQuery();
        if(!rs.next()) throw new IllegalArgumentException("No tool type with this ID");
        return rs.getString("toolTypeName");
    }

    private LinkedList<LocalDateTime> getUsedDates(int toolID, PrintWriter out) throws SQLException{
        Connection dbConnection = getConnection(out);
        String query = "SELECT startDate, endDate FROM Booking WHERE toolID = ?;";
        PreparedStatement statement = dbConnection.prepareStatement(query);
        statement.setInt(1, toolID);
        LinkedList<LocalDateTime> dayDates = new LinkedList<>();
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            LocalDateTime start = rs.getTimestamp("startDate").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("endDate").toLocalDateTime();
            addDatesToLinkedList(dayDates, start, end);
        }
        out.println(dayDates.size());
        return dayDates;
    }

    private void addDatesToLinkedList(LinkedList<LocalDateTime> dates, LocalDateTime start, LocalDateTime end){
        while(start.isBefore(end)){
            start = start.plusDays(1);
            dates.add(start);
        }
    }

    private void printData(ToolModel tool, LinkedList<LocalDateTime> days, PrintWriter out){
        HeaderFooter.printHeader(out);
        out.println("<h2>Name: "+tool.getName()+"</h2>");
        out.println("<p>ToolType: "+tool.getToolType()+"</p>");
        out.println("<p>Location: "+tool.getLocation()+"</p>");
        out.println("<p>Status: "+tool.getStatus()+"</p>");
        out.println("<h3>UsedDates:</h3>");
        for(LocalDateTime day: days){
            out.println("<p>"+day.getDayOfMonth()+"."+day.getMonth()+"</p>");
        }
        HeaderFooter.printFooter(out);
    }

    private void printWrongIndex(PrintWriter out){
        HeaderFooter.printHeader(out);
        out.println("<h2>There is no data stored for this item</h2>");
        out.println("<p>Try to refresh the page or enter another index in order to get information");
        HeaderFooter.printFooter(out);
    }

    private void printError(Exception e, PrintWriter out){
        HeaderFooter.printHeader(out);
        out.println("<h2>An internal Error happend</h2>");
        out.println("<p>" + e.getMessage() + "</p>");
        HeaderFooter.printFooter(out);
    }

    private Connection getConnection(PrintWriter out){
        Connection dbConnection = null;

        try{
            dbConnection = DBUtils.getINSTANCE().getConnection(out);
        } catch (SQLException | ClassNotFoundException sqlException){
            out.println(sqlException);
        }
        return dbConnection;
    }

}
