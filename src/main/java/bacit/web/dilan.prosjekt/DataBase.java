package bacit.web.dilan.prosjekt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// This class can be used to initialize the database connection
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