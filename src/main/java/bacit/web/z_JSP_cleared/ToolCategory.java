package bacit.web.z_JSP_cleared;

import bacit.web.utils.DBUtils;

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
by Joachim
prints all the tools NOW WITH IMAGES!
*/
@WebServlet(name = "xtc", value = "/xtc")
public class ToolCategory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        request.setAttribute("CAT", request.getParameter("category"));
        Connection dbc= DBUtils.getNoErrorConnection();
        try {
            PreparedStatement ps3 = dbc.prepareStatement(
                "select * from Tool where toolCategory =?");
            ps3.setString(1, String.valueOf(request.getParameter("category")));
            ResultSet rs3 = ps3.executeQuery();
            // TODO: 10.11.2021 should make this into model array b4 sendeing?
            request.setAttribute("toolByCAT", rs3);
            request.getRequestDispatcher("/-toolListAll.jsp").forward(request,response);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}