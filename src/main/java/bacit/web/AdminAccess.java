package bacit.web;
import java.sql.*;

// by Dilan
// TODO: 10.11.2021 -joachim: this class is probably scrap now with the session thingy, leaving it for now
public class  AdminAccess {
    public static boolean accessRights(String email)   {
        boolean isAdmin = false;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection db = DriverManager.getConnection("jdbc:mariadb://172.17.0.1:3308/AMVDatabase","root","12345");
            PreparedStatement ps = db.prepareStatement("select userAdmin from AMVUser where email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                isAdmin  = rs.getBoolean("userAdmin");
            }
            db.close();

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return isAdmin;
    }

}