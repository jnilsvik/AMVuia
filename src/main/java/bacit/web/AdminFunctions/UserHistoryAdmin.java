package bacit.web.AdminFunctions;

import bacit.web.Modules.BookingModel;
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
import java.time.LocalDate;
import java.util.ArrayList;


@WebServlet(name = "User History", value = "/userhistoryadmin")
public class UserHistoryAdmin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if(checkSession(request,response)) {
                int userID = Integer.parseInt(request.getParameter("userID"));
                Connection dbConnection = DBUtils.getNoErrorConnection();
                String history = "select orderID, userID, toolID, totalPrice, startDate, endDate, returnDate from Booking WHERE userID = ? order by orderID desc";
                PreparedStatement statement = dbConnection.prepareStatement(history);
                statement.setInt(1, userID);
                ResultSet rs = statement.executeQuery();

                ArrayList<BookingModel> bookingList = new ArrayList<>();
                while (rs.next()) {
                    BookingModel model = new BookingModel();
                    model.setOrderID(rs.getInt("orderID"));
                    model.setUserID(rs.getInt("userID"));
                    model.setToolID(rs.getInt("toolID"));
                    model.setTotalPrice(rs.getInt("totalPrice"));
                    model.setStartDate(rs.getDate("startDate").toLocalDate());
                    model.setEndDate(rs.getDate("endDate").toLocalDate());
                    if ((rs.getDate("returnDate") == null)) {
                        model.setReturnDate(LocalDate.parse("1999-12-12"));
                    } else {
                        model.setReturnDate(rs.getDate("returnDate").toLocalDate());
                    }
                    bookingList.add(model);
                }
                request.setAttribute("bookingList", bookingList); // ! a way to set attributes
                request.getRequestDispatcher("jspFiles/AdminFunctions/toolHistory.jsp").forward(request, response);
                rs.close();
                statement.close();
                dbConnection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (PageAccess.isAdmin(request)){
            return true;
        }
        PageAccess.reDirWOUser(request,response);
        PageAccess.reDirWOAdmin(request,response);
        return true;
    }
}