
package bacit.web.toolBooker;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class checkCertificate {
    public static boolean hasCertificate(Connection db, int userID, int toolCertificateID) {

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

           if (totalCertificateID.contains(toolCertificateID)) {
               hasTheCertificate = true;
           }

       } catch (Exception e) {
           e.printStackTrace();
       }
     return hasTheCertificate;
    }
}

