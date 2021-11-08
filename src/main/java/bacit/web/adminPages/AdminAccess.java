package bacit.web.adminPages;
import java.sql.*;

// by Dilan
public class  AdminAccess {

    private static boolean adminAlreadySet = false;
    private static boolean isAdmin = false;

    public static boolean accessRights(String email){
        if(!adminAlreadySet) setRights(email);
        return isAdmin;
    }

    private static void setRights(String email)   {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mariadb://172.17.0.1:3308/AMVDatabase","root","12345");
            PreparedStatement ps = con.prepareStatement("select userAdmin from AMVUser where email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                isAdmin  = rs.getBoolean("userAdmin");
                adminAlreadySet = true;
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}

