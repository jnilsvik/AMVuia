package bacit.web.toolBooker;
import java.sql.*;
import java.time.LocalDate;

// by Dilan
public class getTotalPrice {
    public static int checkTotalPrice(String inputDays, int priceFirst, int priceAfter)
    {
        int totalPrice = 0;
        if (inputDays.equals("1")) {
            totalPrice = priceFirst;
        }

        if (inputDays.equals("2")) {
            totalPrice = priceFirst + priceAfter;
        }

        if (inputDays.equals("3")) {
            totalPrice = priceFirst + priceAfter + priceAfter;
        }

        return totalPrice;
    }

}