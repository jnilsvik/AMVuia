package bacit.web.bacit_web;

import bacit.web.bacit_models.ToolModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DetailedToolServlet", value = "/details")
public class DetailedToolServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();
        try {
            ToolModel tool = getToolFromDB(request.getParameter("toolID"), out);
            LocalDateTime[] usedDates = getUsedDates(tool.getId(), out);
            printData(tool, usedDates, out);
        } catch (SQLException e) {
            //Error in the database
            printError(e, out);
        } catch (IllegalArgumentException e){
            //For the index no data in the database could be found
            printWrongIndex(out);
        }

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
        String query = "select * form ToolType where toolTypeIS = ?;";
        PreparedStatement statement = dbConnection.prepareStatement(query);
        statement.setInt(1, toolTypeID);
        ResultSet rs = statement.executeQuery();
        if(!rs.next()) throw new IllegalArgumentException("No tool type with this ID");
        return rs.getString("toolTypeName");
    }

    private LocalDateTime[] getUsedDates(int toolID, PrintWriter out) throws SQLException{
        Connection dbConnection = getConnection(out);
        String query = "SELECT startDate, endDate FROM Booking WHERE toolID = ?;";
        PreparedStatement statement = dbConnection.prepareStatement(query);
        statement.setInt(1, toolID);
        LinkedList<LocalDateTime> dayDates = new LinkedList<>();
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            LocalDateTime start = convertToLocalDateTimeViaInstant(rs.getDate("startDate"));
            LocalDateTime end = convertToLocalDateTimeViaInstant(rs.getDate("endDate"));
            addDatesToLinkedList(dayDates, start, end);
        }
        return dayDates.toArray(new LocalDateTime[dayDates.size()]);
    }

    private LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private void addDatesToLinkedList(LinkedList<LocalDateTime> dates, LocalDateTime start, LocalDateTime end){
        while(!start.isAfter(end)){
            dates.add(start);
            start.plusDays(1);
        }
    }

    private void printData(ToolModel tool, LocalDateTime[] days, PrintWriter out){
        printHeader(out);
        out.println("<h2>Name: "+tool.getName()+"</h2>");
        out.println("<p>ToolType: "+tool.getToolType()+"</p>");
        out.println("<p>Location: "+tool.getLocation()+"</p>");
        out.println("<p>Status: "+tool.getStatus()+"</p>");
        out.println("<h3>UsedDates:</h3>");
        for(LocalDateTime day: days){
            out.println("<p>"+day.getDayOfMonth()+"."+day.getMonth()+"</p>");
        }
        printFooter(out);
    }

    private void printWrongIndex(PrintWriter out){
        printHeader(out);
        out.println("<h2>There is no data stored for this item</h2>");
        out.println("<p>Try to refresh the page or enter another index in order to get information");
        printFooter(out);
    }

    private void printHeader(PrintWriter out){
        out.println("<html>");
        out.println("<body>");
    }

    private void printFooter(PrintWriter out){
        out.println("</body>");
        out.println("</html>");
    }

    private void printError(Exception e, PrintWriter out){
        printHeader(out);
        out.println("<h2>An internal Error happend</h2>");
        out.println("<p>" + e.getMessage() + "</p>");
        printFooter(out);
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
