package bacit.web.bacit_web;

import bacit.web.bacit_database.DBUtils;
import bacit.web.bacit_headerFooter.HeaderFooter;

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
import java.util.ArrayList;

//Marius wrote this code

@WebServlet(name = "LargeEquipmentServlet", value = "/large-equipment")
public class LargeEquipmentServlet extends HttpServlet {


/*
Ideally this should become a generic method that would be called with different Strings in each servlet.
It first creates an ArrayList to store every result from the preparedstatement.
The method then uses "try" to connect to the database and execute the sql statement.
"While resultSet.next" means that while there are still tools within the selected database category,
the method adds the current name to the ArrayList, then moves on to the next name.
*/
    public ArrayList largeEquipmentList(PrintWriter out) throws SQLException {
        ArrayList<String> LargeEquipment = new ArrayList();

        try (
                Connection dbConnection = DBUtils.getNoErrorConnection(out);
                PreparedStatement statement = dbConnection.prepareStatement("SELECT toolName FROM Tool WHERE toolCategory = ('Large Equipment')");
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                String largeEquipmentReturn = resultSet.getString("toolName");
                LargeEquipment.add(largeEquipmentReturn);
            }
        }

        return LargeEquipment;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        HeaderFooter.printHeader("Large Equipment", out);

        out.println("Hello!");
        out.println("");
        try {
            out.println(largeEquipmentList(out));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}