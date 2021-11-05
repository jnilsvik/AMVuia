package bacit.web.z_scraped;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// by Dilan
public class DataBase {
    protected static Connection initializeDatabase()
            throws SQLException, ClassNotFoundException
    {
        String dbDriver = "org.mariadb.jdbc.Driver";
        String dbURL = "jdbc:mariadb://172.17.0.1:3308/";
        String dbName = "AMVDatabase";
        String dbUsername = "root";
        String dbPassword = "12345";

        Class.forName(dbDriver);
        Connection con = DriverManager.getConnection(dbURL + dbName,
                dbUsername,
                dbPassword);
        return con;
    }
}