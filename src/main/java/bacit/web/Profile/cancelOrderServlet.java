package bacit.web.Profile;

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
        try {
            HttpSession session=request.getSession(false);
            String email = null;
            if(session != null){
                email = (String) session.getAttribute("email");
            }
            if(email == null){
                response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
                return;
            }
            String orderId = request.getParameter("id");
            String result;
            if(isAllowedToChancel(orderId, email, out)){
                deleteOrder(orderId);
                result = "The booking has been successfully removed";
            } else {
                result = "The user is not allowed to cancel this booking";
            }
            DBUtils.ReDirFeedback(request,response,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteOrder(String orderId) throws SQLException {
        Connection db = DBUtils.getNoErrorConnection();
        PreparedStatement ps = db.prepareStatement("DELETE FROM Booking WHERE orderID = ?;");
        ps.setString(1, orderId);
        ps.executeUpdate();

        ps.close();
        db.close();
    }

    private boolean isAllowedToChancel(String orderID, String email, PrintWriter out) throws SQLException {
        Connection db = DBUtils.getNoErrorConnection();
        PreparedStatement ps = db.prepareStatement("SELECT email, orderID from Booking inner JOIN AMVUser AU on Booking.userID = AU.userID WHERE orderID = ?;");
        ps.setString(1, orderID);
        ResultSet rs = ps.executeQuery();
        if(!rs.next()) return false;
        boolean result = rs.getString("email").equals(email);

        rs.close();
        ps.close();
        db.close();
        return result;
    }
}
