package bacit.web.adminPages;

import bacit.web.a_models.BookingModel;
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
import java.time.LocalDate;
import java.util.ArrayList;


@WebServlet(name = "Tool History", value = "/toolhistory")
public class ToolHistory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection dbConnection = DBUtils.getNoErrorConnection();
            String history = "select * from Booking WHERE toolID = ? order by orderID desc";
            PreparedStatement statement = dbConnection.prepareStatement(history);
            statement.setInt(1, Integer.parseInt(request.getParameter("toolID")));
            ResultSet rs = statement.executeQuery();

            ArrayList<BookingModel> bookingList = new ArrayList<>();
            while (rs.next()){

                BookingModel model = new BookingModel();
                        model.setOrderID(rs.getInt("orderID"));
                        model.setUserID(rs.getInt("userID"));
                        model.setToolID(rs.getInt("toolID"));
                        model.setTotalPrice(rs.getInt("totalPrice"));
                        model.setStartDate(rs.getDate("startDate").toLocalDate());
                        model.setEndDate(rs.getDate("endDate").toLocalDate());
                        if ((rs.getDate("returnDate") ==null)) {
                            model.setReturnDate(LocalDate.parse("1999-12-12"));
                        }
                        else {
                            model.setReturnDate(rs.getDate("returnDate").toLocalDate());
                        }
                bookingList.add(model);
            }
            request.setAttribute("bookingList", bookingList); // ! a way to set attributes
            request.getRequestDispatcher("/toolHistory.jsp").forward(request,response);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}