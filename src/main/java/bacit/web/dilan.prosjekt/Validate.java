package bacit.web.dilan.prosjekt;
import java.sql.*;
public class Validate {
    public static boolean checkUser(String email,String pass)
    {
        boolean st =false;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mariadb://172.17.0.1:3308/AMVDatabase","root","12345");
            PreparedStatement ps = con.prepareStatement("select * from Users where email=? and passwordHash=?");
            ps.setString(1, email);
            ps.setString(2, pass);
            ResultSet rs =ps.executeQuery();
            st = rs.next();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return st;
    }
}