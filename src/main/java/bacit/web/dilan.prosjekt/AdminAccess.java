
package bacit.web.dilan.prosjekt;
import java.sql.*;

public class  AdminAccess {

    public static boolean accessRights(String email)   {
        boolean isAdmin = false;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mariadb://172.17.0.1:3308/AMVDatabase","root","12345");
            PreparedStatement ps = con.prepareStatement("select userAdmin from Users where email=?");
            ps.setString(1, email);
            ResultSet rs =ps.executeQuery();

            while (rs.next())
            {
                isAdmin  = rs.getBoolean("userAdmin");
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return isAdmin;
    }

}

