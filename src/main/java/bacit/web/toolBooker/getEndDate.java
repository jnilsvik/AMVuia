package bacit.web.toolBooker;
import java.sql.*;
import java.time.LocalDate;

// by Dilan
public class getEndDate {
    public static LocalDate checkUser(LocalDate startDateInsert, String inputDays)
    {
        LocalDate endDateInsert = null;

        if (inputDays.equals("1")) {
             endDateInsert = startDateInsert.plusDays(1);
        }

        if (inputDays.equals("2")) {
             endDateInsert = startDateInsert.plusDays(2);
        }

        if (inputDays.equals("3")) {
             endDateInsert = startDateInsert.plusDays(3);
        }
        return endDateInsert;
    }

}