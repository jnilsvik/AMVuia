package bacit.web.ToolBooking;

import java.sql.SQLException;
import java.time.*;

import bacit.web.Modules.BookingModel;
import bacit.web.Modules.ToolModel;
import bacit.web.utils.DBUtils;

import java.io.PrintWriter;
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
public class Tool_BookingServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session=request.getSession(false);
            String email = (String) session.getAttribute("email");
            if(email == null){
                response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
                return;
            }
            int toolID = Integer.parseInt(request.getParameter("tools"));
            int inputDays = Integer.parseInt(request.getParameter("days"));
            int userID = getUserID(email);
            LocalDate StartDateWanted = LocalDate.parse(request.getParameter("date"));
            LocalDate endingDate = StartDateWanted.plusDays(inputDays);

            ToolModel tool = getTool(toolID);

            //getTotalPrice class calculates the total price.
            int totalPrice =  tool.getPriceFirst() + tool.getPriceAfter() * (inputDays-1);

            //checkDate class sees if the wanted booked days are already taken. The hasCertificate method checks if the user has the needed certificate.
            if(dateBookedTaken(StartDateWanted, inputDays, toolID)){
                out.print("<h1>Sorry, that tool is already taken.</h1>");
            }else if(!hasCertificate(userID, tool.getCertificateID())){
                out.print("<h1>Sorry, you don't have the needed certificate.</h1>");
            }else{
                registerBooking(StartDateWanted, endingDate, totalPrice, userID, toolID);
                request.setAttribute("booking", new BookingModel(0, userID, toolID, totalPrice, StartDateWanted, StartDateWanted.plusDays(inputDays), null));
                request.getRequestDispatcher("/bookingComplete.jsp").forward(request,response);
            }
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }

    public static int getUserID(String email) {
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

    public boolean hasCertificate(int userID, int toolCertificateID) {
        if(toolCertificateID == 1) return true;
        boolean hasTheCertificate = false;
        try {
            Connection db = DBUtils.getNoErrorConnection();
            PreparedStatement ps = db.prepareStatement("SELECT * FROM UsersCertificate WHERE userID = ?");
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

    public void registerBooking(LocalDate StartDateWanted, LocalDate endingDate, int totalPrice, int userID, int toolID ) {
        try {
            Connection db = DBUtils.getNoErrorConnection();
            PreparedStatement statement2 =
                    db.prepareStatement("insert into Booking (startDate, endDate, totalPrice, userID, toolID) values(?, ?, ?, ?, ?)");
            statement2.setObject(1, StartDateWanted);
            statement2.setObject(2, endingDate);
            statement2.setInt(3, totalPrice);
            statement2.setInt(4, userID);
            statement2.setInt(5, toolID);
            statement2.executeUpdate();

            statement2.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ToolModel getTool(int toolID) throws SQLException {
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

    public static boolean dateBookedTaken(LocalDate StartDateWanted, int inputDays, int toolID) {
        boolean taken = false;
        try {
            Connection db = DBUtils.getNoErrorConnection();
            PreparedStatement st = db.prepareStatement("SELECT * FROM Booking WHERE toolID = ?");
            st.setInt(1, toolID);
            ResultSet rs = st.executeQuery();

            while (rs.next() && !taken) {
                LocalDate dateStart = rs.getDate("startDate").toLocalDate();
                LocalDate dateEnd = rs.getDate("endDate").toLocalDate();
                if(!dateEnd.isBefore(StartDateWanted) && !dateStart.isAfter(StartDateWanted.plusDays(inputDays))){
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
}