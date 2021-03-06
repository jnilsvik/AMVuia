package bacit.web.utils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

public class PageAccess {

    public static String getEmail(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        String email = null;
        if(session != null){
            email = (String) session.getAttribute("email");
        }
        return email;
    }

    public static boolean isUser(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        return session != null;
    }

    public static boolean isAdmin(HttpServletRequest request) {
        boolean isAdmin = false;
        try {
            Connection db = DBUtils.getNoErrorConnection();
            PreparedStatement statement = db.prepareStatement(
                    "select userAdmin from AMVUser where email=?");
            statement.setString(1, getEmail(request));
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                isAdmin = rs.getBoolean("userAdmin");
            }

            rs.close();
            statement.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAdmin;
    }

    public static void reDirWOUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!isUser(request)) {
            response.sendRedirect("login");
        }
    }

    public static void reDirWOAdmin(HttpServletRequest request, HttpServletResponse response) {
        if (!isAdmin(request)) {
            reDirFeedback(request,response,"You tried to access a function which is only accessible by Admins");
        }
    }

    public static void reDirFeedback(HttpServletRequest request, HttpServletResponse response, String feedbackMsg){
        try {
            request.setAttribute("feedback",feedbackMsg);
            request.getRequestDispatcher("jspFiles/PageElements/feedback.jsp").forward(request,response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}