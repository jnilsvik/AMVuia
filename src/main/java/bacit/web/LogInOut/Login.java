package bacit.web.LogInOut;

import bacit.web.Modules.ToolModel;
import bacit.web.utils.DBUtils;
import bacit.web.utils.PageAccess;
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
import java.util.List;

@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            if (checkSession(request,response)) {
                response.sendRedirect("toolList");
            }
            else {
                try {
                    request.getRequestDispatcher("/jspFiles/LogIn/login.jsp").forward(request,response);
                } catch (ServletException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = hashPassword.encryptThisString(request.getParameter("pass"));
        if(Validation(email,password)){
            HttpSession session= request.getSession();
            session.setAttribute("email",email);

            response.sendRedirect("toolList");

        } else {
            PageAccess.ReDirFeedback(request,response,"Invalid email or password");
        }
    }
    private boolean Validation(String email, String pw){
        try {
            Connection db = DBUtils.getNoErrorConnection();
            String insertUserCommand = "select userID from AMVUser where email=? and passwordHash=?";
            PreparedStatement ps = db.prepareStatement(insertUserCommand);

            ps.setString(1, email);
            ps.setString(2, pw);
            ResultSet rs1 = ps.executeQuery();
            if (rs1.next()) return true;

            rs1.close();
            ps.close();
            db.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (PageAccess.isUser(request,response)){
            return true;
        }
        else {
            return false;
        }
    }
}