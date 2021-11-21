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
public class  AdminAccess {
    public static boolean isAdmin(String email) {
        boolean isAdmin = false;
        try {
            Connection db = DBUtils.getNoErrorConnection();
            String insertUserCommand = "select userAdmin from AMVUser where email=?";
            PreparedStatement statement = db.prepareStatement(insertUserCommand);
            statement.setString(1, email);
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

    public static String reDirWOAccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        String email = null;
        if (session != null) {
            email = (String) session.getAttribute("email");
            // TODO: 21.11.2021 should this part be redundant as we only init a session if theres a mail?
            if (email == null) {
                response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
            }
        }
        return email;
    }

    public static void reDirWOAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!AdminAccess.isAdmin(reDirWOAccess(request, response))) {
            request.getRequestDispatcher("/jspFiles/AdminFunctions/noAdminAccount.jsp").forward(request, response);
        }
    }
}