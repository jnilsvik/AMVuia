package bacit.web.ToolBooking;

import bacit.web.Modules.ToolModel;
import bacit.web.utils.DBUtils;


import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ToolDetailServlet", value = "/tooldetail")
public class ToolDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session=request.getSession(false);
        String email = null;
        if(session != null){
            email = (String) session.getAttribute("email");
        }
        if(email == null){
            response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
            return;
        }
        int toolID = Integer.parseInt(request.getParameter("toolID"));
        try{
            ToolModel tool = getTool(toolID);
            List<LocalDate> dates = getBookings(toolID);

            request.setAttribute("tool", tool);
            request.setAttribute("dates", dates);
            request.getRequestDispatcher("/jspFiles/ToolBooking/toolDetailed.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ToolModel getTool(int toolID) throws SQLException {
        Connection db = DBUtils.getNoErrorConnection();
        PreparedStatement ps = db.prepareStatement(
                "SELECT * FROM Tool WHERE toolID = ?");
        ps.setInt(1, toolID);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return new ToolModel(
                    rs.getInt("toolID"),
                    rs.getString("toolName"),
                    rs.getString("toolCategory"),
                    rs.getBoolean("maintenance"),
                    rs.getInt("priceFirst"),
                    rs.getInt("priceAfter"),
                    rs.getInt("certificateID"),
                    rs.getString("toolDescription"),
                    rs.getString("picturePath")
            );
        }
        else {
            return null;
        }

    }

    private List<LocalDate> getBookings(int toolID) throws SQLException {
        Connection db = DBUtils.getNoErrorConnection();
        PreparedStatement ps = db.prepareStatement("SELECT startDate, endDate FROM Booking WHERE toolID = ? AND returnDate IS NULL;");
        ps.setInt(1, toolID);
        ResultSet rs = ps.executeQuery();
        List<LocalDate> dates = new LinkedList<>();
        while(rs.next()){
            LocalDate dateStart = rs.getDate("startDate").toLocalDate();
            LocalDate dateEnd = rs.getDate("endDate").toLocalDate();

            while (!dateStart.isAfter(dateEnd)) {
                dates.add(dateStart);
                dateStart = dateStart.plusDays(1);
            }
        }

        rs.close();
        ps.close();
        db.close();
        return dates;
    }
}








