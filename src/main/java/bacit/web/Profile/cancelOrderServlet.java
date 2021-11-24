package bacit.web.Profile;

import bacit.web.utils.DBUtils;
import bacit.web.utils.PageAccess;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//by Paul
@WebServlet(name = "Cancellation", value = "/cancellation")
public class cancelOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            if (checkSession(request,response)){
                String email = PageAccess.getEmail(request);
                String orderId = request.getParameter("id");
                String result;

                if(isAllowedToChancel(orderId, email)){
                    deleteOrder(orderId);
                    result = "The booking has been successfully removed";
                } else {
                    result = "The user is not allowed to cancel this booking";
                }
                PageAccess.reDirFeedback(request,response,result);
            }

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

    private boolean isAllowedToChancel(String orderID, String email) throws SQLException {
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
    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (PageAccess.isAdmin(request,response)){
            return true;
        }
        PageAccess.reDirWOUser(request,response);
        return true;
    }

}
