package bacit.web.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
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

    public static void ReDirFeedback(HttpServletRequest request, HttpServletResponse response, String feedbackMsg){
        try {
            request.setAttribute("feedback",feedbackMsg);
            request.getRequestDispatcher("feedback.jsp").forward(request,response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}

