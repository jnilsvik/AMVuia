package bacit.web.AdminFunctions;

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

/*
by Joachim

prints all the users
*/
@WebServlet(name = "lu", value = "/lu")
public class ListUsers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            HttpSession session=request.getSession(false);
            String email = (String) session.getAttribute("email");
            if(email == null){
                response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
                return;
            }

            Connection dbConnection = DBUtils.getNoErrorConnection();
            String userQ = "select * from AMVUser order by userID ";
            PreparedStatement statement = dbConnection.prepareStatement(userQ);
            ResultSet rs = statement.executeQuery();

            request.setAttribute("userList", rs); // ! a way to set attributes
            request.getRequestDispatcher("/listUsers.jsp").forward(request,response);

            rs.close();
            statement.close();
            dbConnection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}