package bacit.web.AdminFunctions;

import bacit.web.utils.DBUtils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.io.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan
@WebServlet(name = "RegisterTool", value = "/toolregister")
public class RegisterTool extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            if (AdminAccess.accessRights(email)) {
                Class.forName("org.mariadb.jdbc.Driver");
                Connection db = DriverManager.getConnection("jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");
                PreparedStatement ps1 = db.prepareStatement("SELECT toolCategory FROM Tool GROUP BY toolCategory");
                ResultSet rs1 = ps1.executeQuery();

                PreparedStatement ps2 = db.prepareStatement("SELECT * FROM ToolCertificate");
                ResultSet rs2 = ps2.executeQuery();

                request.setAttribute("regTool1",rs1);
                request.setAttribute("regTool2",rs2);
                request.getRequestDispatcher("registerTool").forward(request,response);

                ps1.close();
                ps2.close();
                rs1.close();
                rs2.close();
                db.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {

            Connection db = DBUtils.getNoErrorConnection();
            PreparedStatement statement = db.prepareStatement(
                    "insert into Tool (toolName, maintenance, priceFirst, priceAfter, toolCategory, certificateID, toolDescription) values(?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, request.getParameter("toolname"));
            statement.setBoolean(2, false);
            statement.setInt(3, Integer.parseInt(request.getParameter("pricefirst")));
            statement.setInt(4, Integer.parseInt(request.getParameter("priceafter")));
            statement.setString(5, request.getParameter("toolCategory"));
            statement.setString(6, request.getParameter("toolcertificate"));
            statement.setString(7, request.getParameter("tooldesc"));
            statement.executeUpdate();

            DBUtils.ReDirFeedback(request,response,"Tool successfully registered");

            statement.close();
            db.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}




