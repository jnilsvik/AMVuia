package bacit.web.bacit_models;

import bacit.web.bacit_web.DBUtils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;

public class ToolModel {

    private int id;
    private String name;
    private String toolType;
    private String location;
    private String status;

    public ToolModel(int id, String name, String toolType, String location, String status) {
        this.id = id;
        this.name = name;
        this.toolType = toolType;
        this.location = location;
        this.status = status;
    }

    public static ToolModel getToolModel(String toolID) throws SQLException {
        Connection dbConnection = getConnection();
        String query ="select * from Tool where toolID = ?;";
        PreparedStatement statement= dbConnection.prepareStatement(query);
        statement.setString(1, toolID);
        ResultSet rs = statement.executeQuery();
        ToolModel tool = null;
        if(!rs.next()) throw new IllegalArgumentException("No tool with this ID");
        tool = new ToolModel(rs.getInt("toolID"), "Tool name will be added to database", getToolType(rs.getInt("toolTypeID"), out), rs.getString("location"), rs.getString("status"));
        return tool;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getToolType() {
        return toolType;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public LinkedList<LocalDate> getUsedDates(PrintWriter out) throws SQLException {
        Connection dbConnection = getConnection();
        String query = "SELECT startDate, endDate FROM Booking WHERE toolID = ?;";
        PreparedStatement statement = dbConnection.prepareStatement(query);
        statement.setInt(1, id);
        LinkedList<LocalDate> dayDates = new LinkedList<>();
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            LocalDate start = rs.getTimestamp("startDate").toLocalDateTime().toLocalDate();
            LocalDate end = rs.getTimestamp("endDate").toLocalDateTime().toLocalDate();
            addDatesToLinkedList(dayDates, start, end);
        }
        return dayDates;
    }

    private void addDatesToLinkedList(LinkedList<LocalDate> dates, LocalDate start, LocalDate end){
        while(start.isBefore(end)){
            start = start.plusDays(1);
            dates.add(start);
        }
    }


    private static Connection getConnection(){
        Connection dbConnection = null;

        try{
            dbConnection = DBUtils.getINSTANCE().getConnection();
        } catch (SQLException | ClassNotFoundException sqlException){
            sqlException.printStackTrace();
        }
        return dbConnection;
    }
}
