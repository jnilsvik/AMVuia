package bacit.web.bacit_models;

import bacit.web.bacit_web.DBUtils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

public class BookingModel {

    private int bookingID;
    private int userID;
    private int toolID;
    private LocalDate startDate;
    private LocalDate endDate;

    public BookingModel(int bookingID, int userID, int toolID, LocalDate startDate, LocalDate endDate) {
        this.bookingID = bookingID;
        this.userID = userID;
        this.toolID = toolID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getToolID() {
        return toolID;
    }

    public void setToolID(int toolID) {
        this.toolID = toolID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getTotalPrice(PrintWriter out){
        double totalPrice = 0;
        try {
            Connection db = getConnection(out);
            String query = "SELECT ToolType.priceAfterFirstDay, ToolType.priceFirstDay\n" +
                    "FROM ToolType\n" +
                    "INNER JOIN Tool\n" +
                    "ON Tool.toolID = ? && Tool.toolTypeID = ToolType.toolTypeID;";
            PreparedStatement statement = db.prepareStatement(query);
            statement.setInt(1, toolID);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) throw new SQLException("No tool could be found");
            double priceFirstDay = rs.getDouble("priceFirstDay");
            double priceAfterFirstDay = rs.getDouble("priceAfterFirstDay");
            totalPrice = calculateTotalPrice(priceFirstDay, priceAfterFirstDay);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return totalPrice;
    }

    private double calculateTotalPrice(double priceFirstDay, double priceAfterFirstDay){
        Period period = Period.between(startDate, endDate);
        period.minusDays(1);
        return priceAfterFirstDay + priceAfterFirstDay * period.getDays();
    }

    public String getToolName(PrintWriter out){
        String toolName = "";
        try {
            Connection db = getConnection(out);
            String query = "SELECT toolName FROM Tool WHERE toolID = ?;";
            PreparedStatement statement = db.prepareStatement(query);
            statement.setInt(1, toolID);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) throw new SQLException("No tool could be found");
            toolName = rs.getString("toolName");
        } catch (SQLException e){
            e.printStackTrace();
        }
        //Currently, there is no toolName stored in the db will be added
        return "currently no toolName stored";
    }

    private Connection getConnection(PrintWriter out){
        Connection dbConnection = null;
        try{
            dbConnection = DBUtils.getINSTANCE().getConnection();
        } catch (SQLException | ClassNotFoundException sqlException){
            out.println(sqlException);
        }
        return dbConnection;
    }
}
