package bacit.web.utils;

import bacit.web.a_models.BookingModel;
import bacit.web.a_models.ToolModel;
import bacit.web.a_models.UserModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/*
By Joachim -- INTENDED FOR DatabaseBuilder2!

This class is ment to allow you the check if there are any oddeties with the database compared to the models we make

the second line is the (those in the xxx-Print class) are the direct content from the db in the correct fields

both result will print to the page upon loading,
-- if any of the first of a kind is cut there is a flaw in the naming (cannot access)
-- if the model (first line) is different, then the model is being loaded incorrectly (wrong order)
 */


@WebServlet(name = "mt", value = "/mt")
public class ModuleTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        Connection dbc = DBUtils.getNoErrorConnection(out);

        out.print("<!DOCTYPE html>");
        out.print("<head>");
        out.print("  <title>Toollist</title>");
        out.print("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />");
        out.print("  <meta charset=\"utf-8\" />");
        out.print("  <link rel=\"stylesheet\" href=\"css/list.css\">" );
        out.print("  <link rel=\"stylesheet\" href=\"css/style.css\">" );
        out.print("</head>");
        out.print("<body>");


        out.print("<section class='book'>");
        try {
            TestBookingModule(out, dbc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.print("</section>");
        out.print("<section class='printBook'>");
        try {
            TestBookingModulePrint(out,dbc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.print("</section>");
        out.print("<section class='user'>");
        try {
            TestUserModule(out,dbc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.print("</section>");
        out.print("<section class='userPrint'>");
        try {
            TestUserModulePrint(out,dbc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.print("</section>");
        out.print("<section class='tool'>");
        try {
            TestToolModule(out,dbc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.print("</section>");
        out.print("<section class='toolPrint'>");
        try {
            TestToolModulePrint(out,dbc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.print("</section>");
    }

    void TestBookingModule(PrintWriter out, Connection dbConnection) throws SQLException{
        PreparedStatement statement = dbConnection.prepareStatement(
                "select * from Booking where toolID = 2");
        ResultSet rs = statement.executeQuery();

        BookingModel model;
        if (rs.next()) {
            model = new BookingModel(
                    rs.getInt("orderID"),
                    rs.getInt("toolID"),
                    rs.getInt("userID"),
                    rs.getTimestamp("startDate").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("endDate").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("returnDate").toLocalDateTime().toLocalDate());
            out.print(model.getOrderID());
            out.print(model.getToolID());
            out.print(model.getUserID());
            out.print(model.getStartDate());
            out.print(model.getEndDate());
            out.print(model.getReturnDate());
        }
    }

    void TestUserModule(PrintWriter out, Connection dbConnection) throws SQLException{
        PreparedStatement statement = dbConnection.prepareStatement(
                "select * from AMVUser order by userID ");
        ResultSet rs = statement.executeQuery();

        UserModel model;
        if (rs.next()) {
            model = new UserModel(
                    rs.getInt("userID"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("phoneNumber"),
                    "password",
                    rs.getBoolean("unionMember"),
                    rs.getBoolean("userAdmin"),
                    rs.getString("email"));
            out.print(model.getUserID());
            out.print(model.getEmail());
            out.print(model.getPassword());
            out.print(model.getFirstname());
            out.print(model.getLastname());
            out.print(model.getPhoneNumber());
            out.print(model.isUnionMember());
            out.print(model.isUserAdmin());
        }
    }

    void TestToolModule(PrintWriter out, Connection dbConnection) throws SQLException{
        PreparedStatement statement = dbConnection.prepareStatement(
                "select * from Tool order by toolID ");
        ResultSet rs = statement.executeQuery();

        ToolModel model;
        if (rs.next()) {
            model = new ToolModel(
                    rs.getInt("toolID"),
                    rs.getString("toolName"),
                    rs.getString("toolCategory"),
                    rs.getBoolean("maintenance"),
                    rs.getInt("priceFirst"),
                    rs.getInt("priceAfter"),
                    rs.getInt("certificateID"),
                    rs.getString("description"),
                    rs.getString("picturePath"));
            out.print(model.getToolID());
            out.print(model.getToolName());
            out.print(model.getToolCategory());
            out.print(model.getMaintenance());
            out.print(model.getPriceFirst());
            out.print(model.getPriceAfter());
            out.print(model.getCertificateID());
            out.print(model.getDescription());
            out.print(model.getPicturePath());
        }
    }

    void TestBookingModulePrint(PrintWriter out, Connection dbConnection) throws SQLException{
        PreparedStatement statement = dbConnection.prepareStatement(
                "select * from Booking where toolID = 2");
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            out.print(rs.getInt("orderID"));
            out.print(rs.getInt("toolID"));
            out.print(rs.getInt("userID"));
            out.print(rs.getTimestamp("startDate").toLocalDateTime().toLocalDate());
            out.print(rs.getTimestamp("endDate").toLocalDateTime().toLocalDate());
            out.print(rs.getTimestamp("returnDate").toLocalDateTime().toLocalDate());
        }
    }

    void TestUserModulePrint(PrintWriter out, Connection dbConnection) throws SQLException{
        PreparedStatement statement = dbConnection.prepareStatement(
                "select * from AMVUser order by userID ");
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            out.print(rs.getInt("userID"));
            out.print(rs.getString("email"));
            out.print("password");
            out.print(rs.getString("firstname"));
            out.print(rs.getString("lastname"));
            out.print(rs.getString("phoneNumber"));
            out.print(rs.getBoolean("unionMember"));
            out.print(rs.getBoolean("userAdmin"));
        }
    }

    void TestToolModulePrint(PrintWriter out, Connection dbConnection) throws SQLException{
        PreparedStatement statement = dbConnection.prepareStatement(
                "select * from Tool order by toolID ");
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            out.print(rs.getInt("toolID"));
            out.print(rs.getString("toolName"));
            out.print(rs.getString("toolCategory"));
            out.print(rs.getBoolean("maintenance"));
            out.print(rs.getInt("priceFirst"));
            out.print(rs.getInt("priceAfter"));
            out.print(rs.getInt("certificateID"));
            out.print(rs.getString("description"));
            out.print(rs.getString("picturePath"));
        }
    }
}
