package bacit.web.bacit_web;

//By Paul

import bacit.web.utils.DBUtils;
import bacit.web.utils.PageElements;
import bacit.web.models.ToolModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CreateNewBookingServlet", value = "/newBooking")
public class CreateNewBookingServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        //Later the username will be added through the role based login
        String userEmail = "testMail";
        String toolID = req.getParameter("toolID");

        PageElements.printHeader("Place new Bookings", out);
        writeNewBookingForm(toolID, userEmail, out);
        PageElements.printFooter(out);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        PageElements.printHeader("Booking result", out);

        //unsure how UserID and ToolID will be added to this request
        String userID = "1";

        String toolID = "1";

        LinkedList<LocalDate> usedDates = null;
        try {
            ToolModel tool = ToolModel.getToolModel(toolID, out);
            usedDates = tool.getUsedDates(out);

            //unsure how to reach the right datatype
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String startDateString = req.getParameter("startDate");
            LocalDate startDate = LocalDate.parse(startDateString, formatter);
            String endDateString = req.getParameter("endDate");
            LocalDate endDate = LocalDate.parse(endDateString, formatter);

            if(!checkValidDates(startDate, endDate, usedDates)) printInvalidBooking("The Tool is not free for the period", toolID, userID, out);
            else if(!checkCertificate(userID, toolID, out)) printInvalidBooking("The user is not authorized for the tool",toolID, userID, out);
            else{
                placeNewBooking(userID, toolID, startDate, endDate, out);
                printBookingSucceed(userID, toolID, startDate, endDate, out);
            }
        } catch (SQLException e) {
            printError(e, out);
        }

        PageElements.printFooter(out);
    }

    private void writeNewBookingForm(String toolName, String userName, PrintWriter out){
        out.println("<h3>User: " + userName + "is booking tool: " + toolName);
        out.println("<form action='place_new_booking' method='POST'>");
        out.println("<label for='startDate'>Start Date</label> ");
        out.println("<input type='date' name='startDate'/>");
        out.println("<label for='endDate'>End Date</label> ");
        out.println("</form>");
    }

    private void printInvalidBooking(String message, String toolName, String userName, PrintWriter out){
        out.println("<h1>"+message+"</h1>");
        out.println("<h3>Try again</h3>");
        writeNewBookingForm(toolName, userName, out);
    }

    private void printBookingSucceed(String userID, String toolID, LocalDate startDate, LocalDate endDate, PrintWriter out){
        out.println("<h1>Booking successfully placed</h3>");
        out.println("<p>"+userID+" got "+toolID+" form "+startDate.toString()+" to "+endDate.toString()+"</p>");
    }

    private void printError(Exception e, PrintWriter out){
        out.println("<h2>An internal error happened</h2>");
        out.println("<p>"+e.getMessage()+"</p>");
    }

    private boolean checkValidDates(LocalDate start, LocalDate end, LinkedList<LocalDate> usedDates){
        if(usedDates == null) return true;
        while(!start.isAfter(end)){
            if(usedDates.contains(start)) return false;
            start.plusDays(1);
        }
        return true;
    }

    private boolean checkCertificate(String userID, String toolID, PrintWriter out){
        Connection db = DBUtils.getNoErrorConnection(out);
        //Not sure how the database will be like
        String query = "";
        try{
            PreparedStatement statement = db.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException sqlE){
            sqlE.printStackTrace();
        }
        return false;
    }

    private void placeNewBooking(String userID, String toolID, LocalDate startDate, LocalDate endDate, PrintWriter out) throws SQLException {
        Connection db = DBUtils.getNoErrorConnection(out);
        String query = "INSERT INTO Booking (userID, toolID, startDate, endDate) VALUES (?,?,?,?);";
        PreparedStatement statement = db.prepareStatement(query);
        statement.setString(1, userID);
        statement.setString(2, toolID);
        statement.setObject(3, startDate);
        statement.setObject(4, endDate);
        statement.executeUpdate();
    }
}
