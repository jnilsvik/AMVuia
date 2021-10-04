package bacit.web.bacit_web;

import bacit.web.bacit_models.ToolModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DetailedToolServlet", value = "/details")
public class DetailedToolServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();
        ToolModel tool = getToolFromDB(request.getParameter("toolID"), out);

    }

    private ToolModel getToolFromDB(String toolID, PrintWriter out)throws SQLException,IllegalArgumentException{
        Connection dbConnection = getConnection(out);
        String query ="select * from Tool where toolID = ?";
        PreparedStatement statement= dbConnection.prepareStatement(query);
        statement.setString(1, toolID);
        ResultSet rs = statement.executeQuery();
        ToolModel tool = null;
        if(!rs.next()) throw new IllegalArgumentException("No tool with this ID");
        tool = new ToolModel(rs.getInt("toolID"), "Tool name will be added to database", getToolType(rs.getInt("toolTypeID")), rs.getString("location"), rs.getString("status"));
        return tool;
    }

    private Connection getConnection(PrintWriter out){
        Connection dbConnection = null;

        try{
            dbConnection = DBUtils.getINSTANCE().getConnection(out);
        } catch (SQLException | ClassNotFoundException sqlException){
            out.println(sqlException);
        }
        return dbConnection;
    }

    private String getToolType(int toolTypeID){
        //will be added
    }
}
