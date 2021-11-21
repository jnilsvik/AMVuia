package bacit.web.ToolBooking;

import bacit.web.utils.DBUtils;
import bacit.web.utils.PageAccess;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "xtc", value = "/xtc")
public class ToolCategory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            if (!checkSession(request, response))
            request.setAttribute("CAT", request.getParameter("category"));
            Connection dbc= DBUtils.getNoErrorConnection();
            PreparedStatement ps3 = dbc.prepareStatement(
                "select * from Tool where toolCategory =?");
            ps3.setString(1, String.valueOf(request.getParameter("category")));
            ResultSet rs3 = ps3.executeQuery();
            // TODO: 10.11.2021 should make this into model array b4 sendeing?
            request.setAttribute("toolByCAT", rs3);

            request.getRequestDispatcher("jspFiles/ToolBooking/-toolListByCAT.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!PageAccess.isAdmin(request,response)){
            PageAccess.reDirWOUser(request,response);
            PageAccess.reDirWOAdmin(request,response);
            return false;
        } else return true;
    }

}