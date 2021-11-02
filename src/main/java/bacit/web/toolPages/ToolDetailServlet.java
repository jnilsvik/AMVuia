package bacit.web.toolPages;

import bacit.web.utils.DBUtils;
import bacit.web.utils.PageElements;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//Marius wrote this code

@WebServlet(name = "ToolDetailServlet", value = "/tooldetail")
public class ToolDetailServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("email");

            Connection db = DBUtils.getNoErrorConnection(out);

            int toolID = Integer.parseInt(request.getParameter("tool"));

            PreparedStatement st1 = db
                    .prepareStatement("SELECT * FROM Tool WHERE toolID = ?");
            st1.setInt(1, toolID);
            ResultSet rs1 = st1.executeQuery();

            out.println("<html>");
            out.println("<head>");
            out.println("<style>");
            out.println("table, th, td {border: 1px solid black;}");
            out.println("th, td {padding: 0 5px;}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            PageElements.printSidebar(out);

            while (rs1.next()) {

                String toolName = rs1.getString("toolName");
                String toolDescription = rs1.getString("toolDescription");
                String toolCategory = rs1.getString("toolCategory");
                int priceFirst = rs1.getInt("priceFirst");
                int priceAfter = rs1.getInt("priceAfter");

                out.println("<h1> " + toolName.replaceAll("_", " ") + " from the Category: " + toolCategory.replaceAll("_", " ") + "</h1>");
                out.println("<br>");
                out.println("<img src = 'img/amv.png' width = '156' heigth = '151'>");
                out.println("<h2>Price the first day: " + priceFirst + "</h2>");
                out.println("<h2>Price after the first day: " + priceAfter + "</h2>");
                out.println("<br>");
                out.println("<p> " + toolDescription + "");
                out.println("<br>");
                out.println("<br>");

                out.print("<form action = 'toolbooking' method = 'POST'>");
                out.print("<label for = 'tools'> Your chosen tool:</label>");
                out.print("<select name = 'tools' id = 'tools'><br>");

                out.print("<option value = '" + toolID + "'> " + toolName.replaceAll("_", " ") + " </option>");

                out.print("</select>");
                out.println("<br>");
                out.println("<br>");

                out.print("<label for = 'date'> Choose start date:</label>");
                out.print("<input type = 'date' id = 'date' name = 'date'><br>");
                out.println("<br>");

                out.println(" <label for='days'>Choose how many days:</label>");
                out.println("<select id='days' name = 'days'>");
                out.print("<option value='1'> 1 Day</option>");
                out.print("<option value='2'> 2 Days</option>");
                out.print("<option value='3'> 3 Days</option>");
                out.print("</select>");
                out.println("<br>");
                out.println("<br>");

                out.println(" <label for='name'>You're booking as:</label>");
                out.print("<input type = 'text' value = '" + email + "' name = 'email' readonly>");

                out.print("<input type = 'submit' value = 'Submit'>");
                out.println("</form>");
            }


            //Calendar of available and booked dates
            PreparedStatement st2 = db
                    .prepareStatement("SELECT * FROM Booking WHERE toolID = ? AND toolReturnDate IS NULL");
            st2.setInt(1, toolID);
            ResultSet rs2 = st2.executeQuery();

            List<LocalDate> totalDates = new ArrayList<>();
            while(rs2.next()) {

                LocalDate dateStart = rs2.getDate("startDate").toLocalDate();
                LocalDate dateEnd = rs2.getDate("endDate").toLocalDate();

                while (!dateStart.isAfter(dateEnd)) {
                    totalDates.add(dateStart);
                    dateStart = dateStart.plusDays(1);
                }
            }

                LocalDate currentDate = LocalDate.now();
                currentDate = currentDate.with(DayOfWeek.MONDAY);
                int days = 0;
                int resetWeek = 1;
                DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            out.println("<h2>Available dates</h2>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Monday</th>");
            out.println("<th>Tuesday</th>");
            out.println("<th>Wednesday</th>");
            out.println("<th>Thursday</th>");
            out.println("<th>Friday</th>");
            out.println("<th>Saturday</th>");
            out.println("<th>Sunday</th>");
            out.println("</tr>");
            out.println("<tr>");

                while (days <= 60) {
                    String status = "Available";

                    if (totalDates.contains(currentDate)) {
                        status = "Booked";
                    }
                    String currentDateFormat = currentDate.format(formatters);
                    out.println("<td>" + currentDateFormat + "<br>" + status + "</td>");

                    if(resetWeek == 7) {
                        out.println("</tr>");
                        out.println("<tr>");
                        resetWeek = 0;
                    }

                    currentDate = currentDate.plusDays(1);
                    days++;
                    resetWeek++;
                }

            out.println("</tr>");
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {
            out.println("error");
        }
    }

}








