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

@WebServlet(name = "CarTrailersServlet", value = "/car-trailers")
public class CarTrailersServlet extends HttpServlet {


/*
Ideally this should become a generic method that would be called with different Strings in each servlet.
It first creates an ArrayList to store every result from the preparedstatement.
The method then uses "try" to connect to the database and execute the sql statement.
"While resultSet.next" means that while there are still tools within the selected database category,
the method adds the current name to the ArrayList, then moves on to the next name.
*/
    public ArrayList carTrailersList(PrintWriter out) throws SQLException {
        ArrayList<String> CarTrailers = new ArrayList();

        try (
                Connection dbConnection = DBUtils.getNoErrorConnection(out);
                PreparedStatement statement = dbConnection.prepareStatement("SELECT toolName FROM Tool WHERE toolCategory = ('Car Trailers')");
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                String carTrailersReturn = resultSet.getString("toolName");
                CarTrailers.add(carTrailersReturn);
            }
        }

        return CarTrailers;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        HeaderFooter.printHeader("Car Trailers", out);

        out.println("Hello!");
        out.println("");
        try {
            out.println(carTrailersList(out));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}