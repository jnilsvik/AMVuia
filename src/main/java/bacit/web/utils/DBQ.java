package bacit.web.utils;

import bacit.web.a_models.ToolModel;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBQ {
    public static ToolModel getAToolModelByID(String toolID, PrintWriter out) throws SQLException {
        Connection dbConnecton = DBUtils.getNoErrorConnection(out);
        String toolQ = "select * from Tool where ToolID = ?";
        PreparedStatement statement = dbConnecton.prepareStatement(toolQ);
        statement.setString(1, toolID);

        ResultSet rs = statement.executeQuery();
        ToolModel model = null;

        while (rs.next()) {
            model = new ToolModel(
                    rs.getInt("toolID"),
                    rs.getString("toolName"),
                    rs.getString("toolCategory"),
                    rs.getBoolean("maintenance"),
                    rs.getInt("priceFirst"),
                    rs.getInt("priceAfter"),
                    rs.getInt("certificateID"),
                    rs.getString("toolDescription"),
                    rs.getString("picturePath"));
        }
        return model;
    }
}
