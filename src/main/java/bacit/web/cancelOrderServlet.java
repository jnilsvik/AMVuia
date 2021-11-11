package bacit.web.profilePage;

import bacit.web.a_models.BookingModel;
import bacit.web.utils.DBUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//by Paul
@WebServlet(name = "Cancellation", value = "/cancellation")
public class cancelOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("TEST");
        try {
            HttpSession session = request.getSession(false);
            if(session == null){
                response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
                return;
            }
            String email = (String) session.getAttribute("email");
            out.println(email);
            String orderId = request.getParameter("id");
            out.println(orderId);
            String result;
            if(isAllowedToChancel(orderId, email, out)){
                deleteOrder(orderId, out);
                result = "The booking has been successfully removed";
            } else {
                result = "The user is not allowed to cancel this booking";
            }

            out.println(result);
            request.setAttribute("result", result);
            request.setAttribute("orderId", orderId);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/Cancellation.jsp").forward(request,response);
        } catch (Exception e) {
            out.println(e);
        }
    }

    private void deleteOrder(String orderId, PrintWriter out) throws SQLException {
        Connection db = DBUtils.getNoErrorConnection(out);
        PreparedStatement ps = db.prepareStatement("DELETE FROM Booking WHERE orderID = ?;");
        ps.setString(1, orderId);
        ps.executeUpdate();
        db.close();
    }

    private boolean isAllowedToChancel(String orderID, String email, PrintWriter out) throws SQLException {
        Connection db = DBUtils.getNoErrorConnection(out);
        PreparedStatement ps = db.prepareStatement("SELECT email, orderID from Booking inner JOIN AMVUser AU on Booking.userID = AU.userID WHERE orderID = ?;");
        ps.setString(1, orderID);
        ResultSet rs = ps.executeQuery();
        if(!rs.next()) return false;
        boolean result = rs.getString("email").equals(email);
        db.close();
        return result;
    }
}
