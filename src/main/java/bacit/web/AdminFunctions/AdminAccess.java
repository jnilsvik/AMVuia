package bacit.web.AdminFunctions;
import bacit.web.utils.DBUtils;

import java.sql.*;

// by Dilan
// TODO: 10.11.2021 -joachim: this class is probably scrap now with the session thingy, leaving it for now
public class  AdminAccess {
    public static boolean accessRights(String email)   {
        boolean isAdmin = false;
        try {
            Connection db = DBUtils.getNoErrorConnection();
            String insertUserCommand = "select userAdmin from AMVUser where email=?";
            PreparedStatement statement = db.prepareStatement(insertUserCommand);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                isAdmin  = rs.getBoolean("userAdmin");
            }
            rs.close();
            statement.close();
            db.close();

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return isAdmin;
    }

}