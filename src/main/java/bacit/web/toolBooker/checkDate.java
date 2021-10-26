
package bacit.web.toolBooker;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class checkDate {
    public static boolean dateBookedTaken(Connection db, LocalDate StartDateWanted, String inputDays, String tool) {
        boolean taken = false;

        try {

            PreparedStatement st = db
                    .prepareStatement("SELECT * FROM Booking WHERE toolID = ?");
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

                if (inputDays.equals("1")) {
                    LocalDate EndDateWanted = StartDateWanted.plusDays(1);
                    if (totalDates.contains(StartDateWanted) || totalDates.contains(EndDateWanted)) {
                        taken = true;

                    }
                }

                if (inputDays.equals("2")) {
                    LocalDate EndDateWanted = StartDateWanted.plusDays(2);
                    if (totalDates.contains(StartDateWanted) || totalDates.contains(EndDateWanted)) {
                        taken = true;

                    }
                }

                if (inputDays.equals("3")) {

                    LocalDate EndDateWanted = StartDateWanted.plusDays(3);
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

