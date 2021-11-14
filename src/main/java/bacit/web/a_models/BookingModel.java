package bacit.web.a_models;

import bacit.web.utils.DBUtils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

public class BookingModel {
    private int orderID;
    private int userID;
    private int toolID;
    private int totalPrice;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate returnDate;

    public BookingModel(int orderID, int userID, int toolID,int totalPrice, LocalDate startDate, LocalDate endDate, LocalDate returnDate) {
        this.orderID = orderID;
        this.userID = userID;
        this.toolID = toolID;
        this.totalPrice = totalPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returnDate = returnDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
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

    public int getTotalPrice(){return totalPrice;}

    public void setTotalPrice(int totalPrice){this.totalPrice=totalPrice;}

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

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getToolName(){
        try {
            PrintWriter out = null;
            Connection db = DBUtils.getNoErrorConnection();
            String query = "SELECT toolName FROM Tool WHERE toolID = ?;";
            PreparedStatement statement = db.prepareStatement(query);
            statement.setInt(1, toolID);
            ResultSet rs = statement.executeQuery();
            db.close();

            if (!rs.next()) throw new SQLException("No tool could be found");
            return  rs.getString("toolName");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "currently no toolName stored";
    }
}
