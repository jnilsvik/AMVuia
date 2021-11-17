package bacit.web.ToolBooking;

import bacit.web.utils.DBUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
            HttpSession session=request.getSession(false);
            String email = (String) session.getAttribute("email");
            if(email == null){
                response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
                return;
            }

            GetSetCategories(out, request);
            GetSetTools(out,request);
            request.getRequestDispatcher("/jspFiles/ToolBooking/toolListAll.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void GetSetCategories(PrintWriter out, HttpServletRequest request){
        try {
            Connection dbc= DBUtils.getNoErrorConnection();
            PreparedStatement ps1 = null;
            ps1 = dbc.prepareStatement(
                    "SELECT toolCategory FROM Tool GROUP BY toolCategory");
            ResultSet rs1 = ps1.executeQuery();
            // TODO: 10.11.2021 should make this into string array before sending?

            request.setAttribute("toolCAT", rs1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void GetSetTools(PrintWriter out, HttpServletRequest request){
        try {
            Connection dbc= DBUtils.getNoErrorConnection();
            PreparedStatement ps2 = dbc.prepareStatement(
                    "select * from Tool order by toolID");
            ResultSet rs2 = ps2.executeQuery();
            // TODO: 10.11.2021 should make this into model array b4 sending?
            request.setAttribute("toolALL", rs2);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}