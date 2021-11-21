package bacit.web.ToolBooking;

import bacit.web.Modules.ToolModel;
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
import java.util.ArrayList;
import java.util.List;

/*
by Joachim

prints all the tools NOW WITH IMAGES!
*/
@WebServlet(name = "xtl", value = "/xtl")
public class ToolAllListings extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

            GetSetCategories(request);
            GetSetTools(request);
            request.getRequestDispatcher("/jspFiles/ToolBooking/toolListAll.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void GetSetCategories(HttpServletRequest request){
        try {
            Connection dbc= DBUtils.getNoErrorConnection();
            PreparedStatement ps1 = dbc.prepareStatement(
                    "SELECT toolCategory FROM Tool GROUP BY toolCategory");
            ResultSet rs1 = ps1.executeQuery();

            ArrayList<String> Categories = new ArrayList<String>();
            while (rs1.next()){
                Categories.add(rs1.getString("toolCategory"));
            }
            request.setAttribute("toolCAT", Categories);
            dbc.close();
            ps1.close();
            rs1.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void GetSetTools(HttpServletRequest request){
        try {
            Connection dbc = DBUtils.getNoErrorConnection();
            PreparedStatement ps2 = dbc.prepareStatement(
                    "select * from Tool order by toolID");
            ResultSet rs2 = ps2.executeQuery();

            ArrayList<ToolModel> toolALL = new ArrayList<>();
            while (rs2.next()){
                toolALL.add(
                        new ToolModel(
                                rs2.getInt("toolID"),
                                rs2.getString("toolName"),
                                rs2.getString("toolCategory"),
                                rs2.getBoolean("maintenance"),
                                rs2.getInt("priceFirst"),
                                rs2.getInt("priceAfter"),
                                rs2.getInt("certificateID"),
                                rs2.getString("toolDescription"),
                                rs2.getString("picturePath")));
            }
            request.setAttribute("toolALL", toolALL);

            dbc.close();
            ps2.close();
            rs2.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}