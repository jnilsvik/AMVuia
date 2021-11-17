package bacit.web.LogInOut;

import bacit.web.utils.hashPassword;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        try {
            HttpSession session = request.getSession(false);
            if(session == null){
                response.sendRedirect("/bacit-web-1.0-SNAPSHOT/xtl");
                return;
            }
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String password = hashPassword.encryptThisString(request.getParameter("pass"));
        if(Validation(email,password)){
            request.getSession().setAttribute("email", email);
            try {
                // TODO: 09.11.2021 make this send you straigth to tools thingy 
                request.getRequestDispatcher("/landing.jsp").forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        } else {
            out.print("Invalid email or password");
        }
    }
    public boolean Validation(String email, String pw){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");
            PreparedStatement ps = con.prepareStatement(
                    "select * from AMVUser where email=? and passwordHash=?");
            ps.setString(1, email);
            ps.setString(2, pw);
            ResultSet rs1 = ps.executeQuery();
            if (rs1.next()) return true;

            rs1.close();
            ps.close();
            con.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // TODO: 11.11.2021 this could probably be done better
    void SetUserSessionAttributes(String email, HttpServletRequest request){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");
            PreparedStatement ps = con.prepareStatement(
                    "select * from AMVUser where email=?");
            ps.setString(1, email);
            ResultSet rs1 = ps.executeQuery();
            request.setAttribute("uID", rs1.getInt("userID"));
            request.setAttribute("ufname", rs1.getString("firstName"));
            request.setAttribute("ulname", rs1.getString("lastName"));
            request.setAttribute("uemail", rs1.getString("phoneNumber"));
            //request.setAttribute("email", rs1.getString("email"));

            rs1.close();
            ps.close();
            con.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}