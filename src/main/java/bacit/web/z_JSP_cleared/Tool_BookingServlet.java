package bacit.web.z_JSP_cleared;

import java.time.*;

import bacit.web.checkDate;
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
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

        try {
            //Connection is set up
            Connection db = DBUtils.getNoErrorConnection(out);

            //Values from the booking form is retrieved
            String email = request.getParameter("email");
            LocalDate StartDateWanted = LocalDate.parse(request.getParameter("date"));

            int inputDays = Integer.parseInt(request.getParameter("days"));
            String tool = request.getParameter("tools");

            //getUserID method retrieves the userID from the email in the session ID.
            int userID = getUserID(db, email);

            //Info of the tool is retrieved
            PreparedStatement st2 = db
                    .prepareStatement("SELECT * FROM Tool INNER JOIN ToolCertificate ON Tool.certificateID = ToolCertificate.certificateID WHERE toolID = ?");
            st2.setString(1, (request.getParameter("tools")));
            ResultSet rs2 = st2.executeQuery();

            int priceFirst = 0;
            int priceAfter = 0;
            int toolID = 0;
            int toolCertificateID = 0;
            String toolCertificateName = null;

            while(rs2.next()) {
                priceFirst = rs2.getInt("priceFirst");
                priceAfter = rs2.getInt("priceAfter");
                toolID = rs2.getInt("toolID");
                toolCertificateID = rs2.getInt("certificateID");
                toolCertificateName = rs2.getString("certificateName");
            }

            //getEndDate class finds the end date.
            LocalDate endingDate = getEndDate(StartDateWanted, inputDays);

            //getTotalPrice class calculates the total price.
            int totalPrice = checkTotalPrice(inputDays, priceFirst, priceAfter);

            //checkDate class sees if the wanted booked days are already taken. The hasCertificate method checks if the user has the needed certificate.
            if (!checkDate.dateBookedTaken(db, StartDateWanted, inputDays, tool) && hasCertificate(db, userID, toolCertificateID, toolCertificateName)) {
                registerBooking(db, StartDateWanted, endingDate, totalPrice, userID, toolID);

                printBookingDetails(out, StartDateWanted, tool, endingDate, totalPrice, email);
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
            PreparedStatement st1 = db
                    .prepareStatement("SELECT * FROM AMVUser WHERE email = ?");
            st1.setString(1, email);
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()) {
                userID = rs1.getInt("userID");

            }
            return userID;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userID;
    }

    public  LocalDate getEndDate(LocalDate startDateInsert, int inputDays)
    {
        LocalDate endDateInsert;
        inputDays = inputDays - 1;
        endDateInsert = startDateInsert.plusDays(inputDays);
        return endDateInsert;
    }

    public  int checkTotalPrice(int inputDays, int priceFirst, int priceAfter)
    {
        int totalPrice = 0;
        if (inputDays == 1) {
            totalPrice = priceFirst;
        }
        if (inputDays == 2) {
            totalPrice = priceFirst + priceAfter;
        }
        if (inputDays == 3) {
            totalPrice = priceFirst + priceAfter + priceAfter;
        }
        return totalPrice;
    }

    public  boolean hasCertificate(Connection db, int userID, int toolCertificateID, String toolCertificateName) {
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

    public void printBookingDetails(PrintWriter out, LocalDate StartDateWanted, String tool, LocalDate endingDate, int totalPrice, String email) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String StartDateWantedFormat = StartDateWanted.format(formatters);
        String EndingDateForm = endingDate.format(formatters);
        out.print("<h1> Tool has been booked. Here is your the order details:</h1>");
        out.print("<p>Tool: " + tool + "</p>");
        out.print("<p>Start Date: " + StartDateWantedFormat + "</p>");
        out.print("<p>Start Date: " + EndingDateForm + "</p>");
        out.print("<p>Total price: " + totalPrice + "</p>");
        out.print("<p>Booked as: " + email + "</p>");
    }

}




