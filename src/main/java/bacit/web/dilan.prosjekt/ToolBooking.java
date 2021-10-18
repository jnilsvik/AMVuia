package bacit.web.dilan.prosjekt;


import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.time.*;

import bacit.web.bacit_database.DBUtils;
import bacit.web.bacit_models.UserModel;
import bacit.web.dilan.prosjekt.hashPassword;
import jdk.vm.ci.meta.Local;

import java.io.PrintWriter;
import java.sql.Connection;
import java.io.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan
@WebServlet(name = "ToolBooking", value = "/toolbooking")
public class ToolBooking extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("email");

            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");

            PreparedStatement ps = con.prepareStatement("select * from Tool");
            ResultSet rs = ps.executeQuery();

            out.println("<html>");
            out.print("<head>");
            out.print("</head>");
            out.println("<body>");

            out.print("<form action = 'toollist' method = 'POST'");
            out.print("<label for = 'tools'> Choose a tool:</label>");
            out.print("<select name = 'tools' id = 'tools'><br>");


            while (rs.next()) {

                String nm = rs.getString("toolName");
                int n = rs.getInt("toolID");


                out.print("<option value = '" + n + "'> " + nm + " </option>");

            }


            out.print("</select>");

            out.print("<label for = 'date'> Set a date:</label>");
            out.print("<input type = 'date' id = 'date' name = 'date'><br>");
            out.println("<br>");


            out.println(" <label for='days'>Choose how many days:</label>");
            out.println("<select id='days' name = 'days'>");
            out.print("<option value='1'> 1 Day</option>");
            out.print("<option value='2'> 2 Days</option>");
            out.print("<option value='3'> 3 Days</option>");
            out.print("</select>");

            out.print("<input type = 'text' value = '" + email + "' name = 'email' readonly>");

            out.print("<input type = 'submit' value = 'Submit'>");
            out.println("</form>");

            out.println("</body>");
            out.println("</html>");

            con.close();
        } catch (Exception e) {
            out.println("error");
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        try {
            Connection db = DBUtils.getNoErrorConnection(out);
            PreparedStatement st = db
                    .prepareStatement("SELECT * FROM Booking WHERE toolID = ?");
            st.setString(1, (request.getParameter("tools")));
            ResultSet rs = st.executeQuery();

            boolean taken = false;

            while (rs.next() && !taken) {

                Date dataBaseStartDate = rs.getDate("startDate");
                Date dataBaseEndDate = rs.getDate("endDate");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                String strDate = dateFormat.format(dataBaseStartDate);
                String strDate1 = dateFormat.format(dataBaseEndDate);

                String inputDate = request.getParameter("date");

                LocalDate dateStart = LocalDate.parse(strDate);
                LocalDate dateEnd = LocalDate.parse(strDate1);

                LocalDate StartDateWanted = LocalDate.parse(inputDate);

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


            if (taken == false) {

                out.println("<html>");
                out.print("<head>");
                out.print("</head>");
                out.println("<body>");
                out.println("<h1> That tool is available</h1>");
                out.println("</body>");
                out.println("</html>");

            } else {

                out.println("<h1>Srry, that tool is already taken on that date, pls choose another one</h1>");
            }


            st.close();
            db.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




