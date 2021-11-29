package bacit.web.AdminFunctions;

import bacit.web.Modules.ToolModel;
import bacit.web.utils.DBUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ToolUpdate extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getCurrentToolData();
        storeCurrentToolData();

        req.getRequestDispatcher("toolUpdate.jsp").forward(req,resp);
    }

    private void storeCurrentToolData() {
    }

    private ToolModel getCurrentToolData() throws SQLException {
        Connection dbc = DBUtils.getNoErrorConnection();
        PreparedStatement ps = dbc.prepareStatement("SELECT * FROM Tool WHERE toolID =?");
        ps.setInt(1,1); // TODO: 29.11.2021 change this
        ResultSet rs = ps.executeQuery();

        return ToolModel(
                rs.getString("too"),
                rs.getString("toolCategory"),
                rs.getString("tool"),
                rs.getString(""),
                rs.getString(""),
                rs.getString(""),
                rs.getString(""),
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
