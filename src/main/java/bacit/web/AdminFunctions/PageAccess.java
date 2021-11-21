package bacit.web.AdminFunctions;
import bacit.web.utils.DBUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

// by Dilan
// TODO: 10.11.2021 -joachim: this class is probably scrap now with the session thingy, leaving it for now
public class PageAccess {
    public static String getEmail(HttpServletRequest request, HttpServletResponse response){
        HttpSession session=request.getSession(false);
        String email = null;
        if(session != null){
            email = (String) session.getAttribute("email");
        }
        return email;
    }

    public static boolean isAdmin(HttpServletRequest request, HttpServletResponse response) {
        boolean isAdmin = false;
        try {
            Connection db = DBUtils.getNoErrorConnection();
            PreparedStatement statement = db.prepareStatement(
                    "select userAdmin from AMVUser where email=?");
            statement.setString(1, getEmail(request,response)
            );
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
        if (getEmail(request,response) == null) {
            response.sendRedirect("login");
        }
    }

    public static void reDirWOAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!isAdmin(request,response)) {
            request.getRequestDispatcher("/jspFiles/AdminFunctions/noAdminAccount.jsp").forward(request, response);
        }
    }
}