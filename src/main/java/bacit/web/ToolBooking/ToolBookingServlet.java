package bacit.web.ToolBooking;

import java.sql.SQLException;
import java.time.*;

import bacit.web.utils.PageAccess;
import bacit.web.Modules.BookingModel;
import bacit.web.Modules.ToolModel;
import bacit.web.utils.DBUtils;

import java.sql.Connection;
import java.io.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
@WebServlet(name = "ToolBookingServlet", value = "/toolbooking")
public class ToolBookingServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setContentType(response);
        try {
            if (checkSession(request,response)){
                int toolID = getToolID(request);

                ToolModel tool = getTool(toolID);

                BookingModel booking = getBooking(tool, request);

                //checkDate class sees if the wanted booked days are already taken. The hasCertificate method checks if the user has the needed certificate.
                if(dateBookedTaken(booking)){
                    printFeedBackJSP("Sorry, the tools is already been booked for that date", request,response);
                }else if(!hasCertificate(booking.getUserID(), tool.getCertificateID())){
                    printFeedBackJSP("Sorry, you don't have the needed certificate for this tool.", request,response);
                }else{
                    boolean isUnionMember = isUnionMember(booking.getUserID());
                    if(isUnionMember) booking.setTotalPrice(0);
                    registerBooking(booking);
                    printSuccessJSP(booking, request, response);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setContentType(HttpServletResponse response) {
        response.setContentType("text/html");
    }

    protected int getToolID(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("tools"));
    }

    protected int getUserID(String email) {
        int userID = 0;
        try {
            Connection db = DBUtils.getNoErrorConnection();
            PreparedStatement st1 = db.prepareStatement(
                    "SELECT * FROM AMVUser WHERE email = ?");
            st1.setString(1, email);
            ResultSet rs1 = st1.executeQuery();
            rs1.next();
            userID = rs1.getInt("userID");

            rs1.close();
            st1.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userID;
    }

    protected boolean hasCertificate(int userID, int toolCertificateID) {
        if(toolCertificateID == 1) return true;
        boolean hasTheCertificate = false;
        try {
            Connection db = DBUtils.getNoErrorConnection();
            PreparedStatement ps = db.prepareStatement("SELECT certificateID FROM UsersCertificate WHERE userID = ?");
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            List<Integer> totalCertificateID = new ArrayList<>();
            int userCertificateID;

            while (rs.next()) {
                userCertificateID = rs.getInt("certificateID");
                totalCertificateID.add(userCertificateID);
            }

            //This checks if the user has the needed certificationID for the tool.
            if (totalCertificateID.contains(toolCertificateID)) {
                hasTheCertificate = true;
            }

            rs.close();
            ps.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasTheCertificate;
    }

    protected void registerBooking(BookingModel booking) throws SQLException {
            Connection db = DBUtils.getNoErrorConnection();
            PreparedStatement statement = db.prepareStatement(
                    "insert into Booking (startDate, endDate, totalPrice, userID, toolID) values(?, ?, ?, ?, ?)");
            statement.setObject(1, booking.getStartDate());
            statement.setObject(2, booking.getEndDate());
            statement.setInt(3, booking.getTotalPrice());
            statement.setInt(4, booking.getUserID());
            statement.setInt(5, booking.getToolID());
            statement.executeUpdate();

            statement.close();
            db.close();
    }

    protected ToolModel getTool(int toolID) throws SQLException {
        ToolModel tool = null;
        Connection db = DBUtils.getNoErrorConnection();
        String query = "SELECT toolID, toolName, priceFirst, priceAfter, certificateID FROM Tool WHERE toolID = ?;";
        PreparedStatement statement = db.prepareStatement(query);
        statement.setInt(1, toolID);
        ResultSet rs = statement.executeQuery();
        if(rs.next()){
            tool = new ToolModel(
                    rs.getInt("toolID"),
                    rs.getString("toolName"),
                    "",false,
                    rs.getInt("priceFirst"),
                    rs.getInt("priceAfter"),
                    rs.getInt("certificateID")
                    ,"", "");
        }
        rs.close();
        statement.close();
        db.close();
        return tool;
    }

    protected BookingModel getBooking(ToolModel tool, HttpServletRequest request) {
        int inputDays = Integer.parseInt(request.getParameter("days")) - 1;
        int userID = getUserID(PageAccess.getEmail(request));
        LocalDate startDate = LocalDate.parse(request.getParameter("date"));
        LocalDate endDate = startDate.plusDays(inputDays);
        int totalPrice =  tool.getPriceFirst() + tool.getPriceAfter() * (inputDays);
        return new BookingModel(0,userID, tool.getToolID(), totalPrice, startDate, endDate, null);
    }

    protected boolean isUnionMember(int userID) throws SQLException {
        Connection db = DBUtils.getNoErrorConnection();
        PreparedStatement statement = db.prepareStatement("SELECT unionMember FROM AMVUser WHERE userID = ?;");
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();
        if(rs.next()){
            return rs.getBoolean("unionMember");
        } else{
            return false;
        }
    }

    protected boolean dateBookedTaken(BookingModel booking) {
        boolean taken = false;
        try {
            Connection db = DBUtils.getNoErrorConnection();
            PreparedStatement st = db.prepareStatement("SELECT startDate, endDate FROM Booking WHERE toolID = ?");
            st.setInt(1, booking.getToolID());
            ResultSet rs = st.executeQuery();

            while (rs.next() && !taken) {
                LocalDate dateStart = rs.getDate("startDate").toLocalDate();
                LocalDate dateEnd = rs.getDate("endDate").toLocalDate();
                if(!dateEnd.isBefore(booking.getStartDate()) && !dateStart.isAfter(booking.getEndDate())){
                    taken = true;
                }
            }
            rs.close();
            st.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taken;
    }

    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (PageAccess.isUser(request)){
            return true;
        }
        PageAccess.reDirWOUser(request,response);
        return false;
    }

    protected void printFeedBackJSP(String message, HttpServletRequest request, HttpServletResponse response){
        PageAccess.reDirFeedback(request,response,message);
    }

    protected void printSuccessJSP(BookingModel booking, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("booking", booking);
        request.getRequestDispatcher("/jspFiles/ToolBooking/bookingComplete.jsp").forward(request,response);
    }

}