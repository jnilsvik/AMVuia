package bacit.web.z_JSP_cleared.WIP.toolPrev_WIP;

import bacit.web.utils.DBUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/*
by Joachim

prints all the tools NOW WITH IMAGES!
*/
@WebServlet(name = "xtl", value = "/xtl")
public class ToolAllListings extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            Connection dbc= DBUtils.getNoErrorConnection(out);
            //gets n sets categories
            PreparedStatement ps1 = dbc.prepareStatement(
                    "SELECT toolCategory FROM Tool GROUP BY toolCategory");
            ResultSet rs1 = ps1.executeQuery();
            // TODO: 10.11.2021 should make this into string array before sending?
            request.setAttribute("toolCAT", rs1);

            //gets n sets tools
            PreparedStatement ps2 = dbc.prepareStatement(
                    "select * from Tool order by toolID");
            ResultSet rs2 = ps2.executeQuery();
            // TODO: 10.11.2021 should make this into model array b4 sendeing?
            request.setAttribute("toolALL", rs2);

            //sends to page
            request.getRequestDispatcher("/-toolListAll.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}