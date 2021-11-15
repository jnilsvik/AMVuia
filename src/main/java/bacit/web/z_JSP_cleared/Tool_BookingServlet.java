package bacit.web.z_JSP_cleared;

import java.time.*;

import bacit.web.utils.DBUtils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.io.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
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
            Connection db = DBUtils.getNoErrorConnection();

            String email = request.getParameter("email");
            String tool = request.getParameter("tools");
            int inputDays = Integer.parseInt(request.getParameter("days"));
            int userID = getUserID(db, email);
            LocalDate StartDateWanted = LocalDate.parse(request.getParameter("date"));
            LocalDate endingDate = StartDateWanted.plusDays(inputDays);

            PreparedStatement st2 = db.prepareStatement(
                    "SELECT * FROM Tool INNER JOIN ToolCertificate ON Tool.certificateID = ToolCertificate.certificateID WHERE toolID = ?");
            st2.setString(1, (request.getParameter("tools")));
            ResultSet rs2 = st2.executeQuery();

            int priceFirst = 0;
            int priceAfter = 0;
            int toolID = 0;
            int toolCertificateID = 0;
            String toolCertificateName = null;

            if (rs2.next()) {
                priceFirst = rs2.getInt("priceFirst");
                priceAfter = rs2.getInt("priceAfter");
                toolID = rs2.getInt("toolID");
                toolCertificateID = rs2.getInt("certificateID");
                toolCertificateName = rs2.getString("certificateName");
            }
            //getTotalPrice class calculates the total price.
            int totalPrice =  priceFirst + priceAfter * (inputDays-1);
            //checkDate class sees if the wanted booked days are already taken. The hasCertificate method checks if the user has the needed certificate.
            if (!dateBookedTaken(db, StartDateWanted, inputDays, tool) && hasCertificate(db, userID, toolCertificateID, toolCertificateName)) {
                registerBooking(db, StartDateWanted, endingDate, totalPrice, userID, toolID);
                request.getRequestDispatcher("/bookingComplete.jsp").forward(request,response);
            } else {
                out.print("<h1>Sorry, that tool is already taken or you dont have the needed ID./h1>");
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getUserID(Connection db, String email) {
        int userID = 0;
        try {
            PreparedStatement st1 = db.prepareStatement(
                    "SELECT * FROM AMVUser WHERE email = ?");
            st1.setString(1, email);
            ResultSet rs1 = st1.executeQuery();

            rs1.next();
            userID = rs1.getInt("userID");
            return userID;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userID;
    }

    public boolean hasCertificate(Connection db, int userID, int toolCertificateID, String toolCertificateName) {
        boolean hasTheCertificate = false;
        try {
            PreparedStatement st3 = db
                    .prepareStatement("SELECT * FROM UsersCertificate WHERE userID = ?");
            st3.setInt(1, userID);
            ResultSet rs3 = st3.executeQuery();
            List<Integer> totalCertificateID = new ArrayList<>();
            int userCertificateID;

            while (rs3.next()) {
                userCertificateID = rs3.getInt("certificateID");
                totalCertificateID.add(userCertificateID);
            }
            //This checks if the user has the needed certificationID for the tool.
            if (totalCertificateID.contains(toolCertificateID) || toolCertificateName.equals("none")) {
                hasTheCertificate = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasTheCertificate;
    }

    public void registerBooking(Connection db, LocalDate StartDateWanted, LocalDate endingDate, int totalPrice, int userID, int toolID ) {
        try {
            PreparedStatement statement2 =
                    db.prepareStatement("insert into Booking (startDate, endDate, totalPrice, userID, toolID) values(?, ?, ?, ?, ?)");
            statement2.setObject(1, StartDateWanted);
            statement2.setObject(2, endingDate);
            statement2.setInt(3, totalPrice);
            statement2.setInt(4, userID);
            statement2.setInt(5, toolID);
            statement2.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: 11.11.2021 -joachim: finish the booking here
    public void printBookingDetails(PrintWriter out, LocalDate StartDateWanted, String tool, LocalDate endingDate, int totalPrice, String email) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String StartDateWantedFormat = StartDateWanted.format(formatters);
        String EndingDateForm = endingDate.format(formatters);
        out.print("<h1> Tool has been booked. Here is your the order details:</h1>");
        out.print("<p>Tool: " + tool + "</p>");
        out.print("<p>Start Date: " + StartDateWantedFormat + "</p>");
        out.print("<p>End Date: " + EndingDateForm + "</p>");
        out.print("<p>Total price: " + totalPrice + "</p>");
        out.print("<p>Booked as: " + email + "</p>");
    }

    // TODO: 11.11.2021 -joachim: this works but i genuinely hate this method
    public static boolean dateBookedTaken(Connection db, LocalDate StartDateWanted, int inputDays, String tool) {
        boolean taken = false;
        try {
            PreparedStatement st = db.prepareStatement("SELECT * FROM Booking WHERE toolID = ?");
            st.setString(1, tool);
            ResultSet rs = st.executeQuery();

            while (rs.next() && !taken) {
                LocalDate dateStart = rs.getDate("startDate").toLocalDate();
                LocalDate dateEnd = rs.getDate("endDate").toLocalDate();
                List<LocalDate> totalDates = new ArrayList<>();
                while (!dateStart.isAfter(dateEnd)) {
                    totalDates.add(dateStart);
                    dateStart = dateStart.plusDays(1);
                }
                if (inputDays == 1) {
                    LocalDate EndDateWanted = StartDateWanted;
                    if (totalDates.contains(StartDateWanted) || totalDates.contains(EndDateWanted)) {
                        taken = true;
                    }
                }
                if (inputDays == 2) {
                    LocalDate EndDateWanted = StartDateWanted.plusDays(1);
                    if (totalDates.contains(StartDateWanted) || totalDates.contains(EndDateWanted)) {
                        taken = true;
                    }
                }
                if (inputDays == 3) {
                    LocalDate EndDateWanted = StartDateWanted.plusDays(2);
                    if (totalDates.contains(StartDateWanted) || totalDates.contains(EndDateWanted)) {
                        taken = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taken;
    }
}