package bacit.web.toolBooker;


import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.time.*;

import bacit.web.bacit_database.DBUtils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.io.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan
@WebServlet(name = "ToolBookingServlet", value = "/toolbooking")
public class ToolBookingServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Connection db = DBUtils.getNoErrorConnection(out);
            PreparedStatement st1 = db
                    .prepareStatement("SELECT * FROM AMVUser WHERE email = ?");
            st1.setString(1, (request.getParameter("email")));
            ResultSet rs1 = st1.executeQuery();

            int userID = 0;
            while (rs1.next()) {
                 userID = rs1.getInt("userID");
            }


            LocalDate startDateInsert = LocalDate.parse(request.getParameter("date"));
            String inputDays = request.getParameter("days");

            PreparedStatement st = db
                    .prepareStatement("SELECT * FROM Booking WHERE toolID = ?");
            st.setString(1, (request.getParameter("tools")));
            ResultSet rs = st.executeQuery();

            boolean taken = false;
            while (rs.next() && !taken) {

                Date dataBaseStartDate = rs.getDate("startDate");
                Date dataBaseEndDate = rs.getDate("endDate");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                LocalDate dateStart = LocalDate.parse(dateFormat.format(dataBaseStartDate));
                LocalDate dateEnd = LocalDate.parse(dateFormat.format(dataBaseEndDate));

                LocalDate StartDateWanted = LocalDate.parse(request.getParameter("date"));

                List<LocalDate> totalDates = new ArrayList<>();
                while (!dateStart.isAfter(dateEnd)) {
                    totalDates.add(dateStart);
                    dateStart = dateStart.plusDays(1);
                }

                if (request.getParameter("days").equals("1")) {
                    LocalDate EndDateWanted = StartDateWanted.plusDays(1);
                    if (totalDates.contains(StartDateWanted) || totalDates.contains(EndDateWanted)) {
                        taken = true;

                    }
                }

                if (request.getParameter("days").equals("2")) {
                    LocalDate EndDateWanted = StartDateWanted.plusDays(2);
                    if (totalDates.contains(StartDateWanted) || totalDates.contains(EndDateWanted)) {
                        taken = true;

                    }
                }

                if (request.getParameter("days").equals("3")) {

                    LocalDate EndDateWanted = StartDateWanted.plusDays(3);
                    if (totalDates.contains(StartDateWanted) || totalDates.contains(EndDateWanted)) {
                        taken = true;

                    }
                }

            }

            PreparedStatement st2 = db
                    .prepareStatement("SELECT * FROM Tool WHERE toolID = ?");
            st2.setString(1, (request.getParameter("tools")));
            ResultSet rs2 = st2.executeQuery();

            int priceFirst = 0;
            int priceAfter = 0;
            int toolID = 0;
            int toolCertificateID = 0;

            while(rs2.next()) {
                priceFirst = rs2.getInt("priceFirst");
                priceAfter = rs2.getInt("priceAfter");
                toolID = rs2.getInt("toolID");
                toolCertificateID = rs2.getInt("certificateID");

            }

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
            boolean hasTheCertificate = false;
            if (totalCertificateID.contains(toolCertificateID)) {
                hasTheCertificate = true;
            }

            //getEndDate class finds the end date.
            LocalDate endingDate = getEndDate.checkUser(startDateInsert, inputDays);

            //getTotalPrice class calculates the total price.
            int totalPrice = getTotalPrice.checkTotalPrice(inputDays, priceFirst, priceAfter);

            if (taken == false && hasTheCertificate == true) {

                PreparedStatement statement2 =
                        db.prepareStatement("insert into Booking (startDate, endDate, totalPrice, userID, toolID) values(?, ?, ?, ?, ?)");
                statement2.setObject(1, startDateInsert);
                statement2.setObject(2, endingDate);
                statement2.setInt(3, totalPrice);
                statement2.setInt(4, userID);
                statement2.setInt(5, toolID);
                statement2.executeUpdate();


                out.println("<html>");
                out.print("<head>");
                out.print("</head>");
                out.println("<body>");
                out.println("<br>");
                out.print("<br>");
                out.println("<br>");
                out.println("<h1> Tool has been booked</h1>");
                out.println("</body>");
                out.println("</html>");

            } else {

                out.println("<h1>Srry, that tool is already taken or you dont have the needed ID./h1>");
            }

            st.close();
            st1.close();
            st2.close();
            db.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}




