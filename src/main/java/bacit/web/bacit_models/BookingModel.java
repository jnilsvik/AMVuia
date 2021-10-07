package bacit.web.bacit_models;

import bacit.web.bacit_web.DBUtils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Period;

public class BookingModel {

    private int bookingID;
    private int userID;
    private int toolID;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double totalPrice;

    public BookingModel(int bookingID, int userID, int toolID, LocalDateTime startDate, LocalDateTime endDate) {
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public double getTotalPrice(PrintWriter out) throws SQLException {
        Connection db = getConnection(out);
        String query = "SELECT ToolType.priceAfterFirstDay, ToolType.priceFirstDay\n" +
                        "FROM ToolType\n" +
                        "INNER JOIN Tool\n" +
                        "ON Tool.toolID = "+toolID+" && Tool.toolTypeID = ToolType.toolTypeID;";
        PreparedStatement statement = db.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        if(!rs.next()) throw new SQLException("No tool could be found");
        double priceFirstDay = rs.getDouble("priceFirstDay");
        double priceAfterFirstDay = rs.getDouble("priceAfterFirstDay");
        return calculateTotalPrice(priceFirstDay, priceAfterFirstDay);
    }

    private double calculateTotalPrice(double priceFirstDay, double priceAfterFirstDay){
        Period period = Period.between(startDate.toLocalDate(), endDate.toLocalDate());
        period.minusDays(1);
        return priceAfterFirstDay + priceAfterFirstDay * period.getDays();
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
