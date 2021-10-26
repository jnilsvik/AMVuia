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
            //Connection is set up
            Connection db = DBUtils.getNoErrorConnection(out);

            //Values from the booking form is retrieved
            String email = request.getParameter("email");
            LocalDate StartDateWanted = LocalDate.parse(request.getParameter("date"));
            String inputDays = request.getParameter("days");
            String tool = request.getParameter("tools");
            int userID = getUserID(db, email);

            //Info of the tool is retrieved
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

            //getEndDate class finds the end date.
            LocalDate endingDate = getEndDate.checkUser(StartDateWanted, inputDays);

            //getTotalPrice class calculates the total price.
            int totalPrice = getTotalPrice.checkTotalPrice(inputDays, priceFirst, priceAfter);

            //checkDate class sees if the wanted booked days are already taken. The checkCertificate class checks if the user has the needed certificate.
            if (!checkDate.dateBookedTaken(db, StartDateWanted, inputDays, tool) && checkCertificate.hasCertificate(db, userID, toolCertificateID)) {

                PreparedStatement statement2 =
                        db.prepareStatement("insert into Booking (startDate, endDate, totalPrice, userID, toolID) values(?, ?, ?, ?, ?)");
                statement2.setObject(1, StartDateWanted);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getUserID(Connection db, String email)
    {
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

}




