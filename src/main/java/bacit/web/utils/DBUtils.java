package bacit.web.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtils {
    private static final DBUtils INSTANCE = new DBUtils();
    static Connection connection;

    public static DBUtils getINSTANCE() {
        return INSTANCE;
    }

    private Connection getConnection() throws SQLException {
        try{
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource)envCtx.lookup("jdbc/database");

            return ds.getConnection();
        }
        catch(NamingException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public static Connection getNoErrorConnection(){
        Connection dbConnection = null;
        try{
            dbConnection = DBUtils.getINSTANCE().getConnection();
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return dbConnection;
    }
}

