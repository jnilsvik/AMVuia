package bacit.web.AdminFunctions;

import bacit.web.Modules.Certificate;
import bacit.web.utils.DBUtils;

import java.sql.*;
import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan changed to jsp by paul
@WebServlet(name = "GiveCertificate", value = "/givecertificate")
public class GiveCertificate extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
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

            if (SessionCheck.isAdmin(email)) {
                List<Certificate> certificates = getCertificates();

                request.setAttribute("certificates", certificates);
                request.getRequestDispatcher("/jspFiles/AdminFunctions/giveCertificate.jsp").forward(request,response);
            } else {
                request.getRequestDispatcher("/jspFiles/AdminFunctions/noAdminAccount.jsp").forward(request,response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession(false);
        if(session == null){
            response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
            return;
        }
        String email = (String) session.getAttribute("email");

        if (SessionCheck.isAdmin(email)) {
            try {
                LocalDate accomplishDate = LocalDate.parse(request.getParameter("accomplishdate"));
                String userID = request.getParameter("userID");
                String certificateID = request.getParameter("certificateID");
                addCertificate(userID, certificateID, accomplishDate);

                String successfulLine = "<h3 style=\"text-align:center\">Certificate was given successfully!</h3>" + "<br><br><br>"  + "<a href=\"givecertificate\"> <span class=bigbutton> Go back  </span></a>";
                request.setAttribute("successfulLine", successfulLine);
                request.getRequestDispatcher("/jspFiles/AdminFunctions/successfulLine.jsp").forward(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            request.getRequestDispatcher("/jspFiles/AdminFunctions/noAdminAccount.jsp").forward(request, response);
        }
    }
    private List<Certificate> getCertificates() throws SQLException {
        List<Certificate> certificateNames = new LinkedList<>();
        Connection db = DBUtils.getNoErrorConnection();
        PreparedStatement ps = db.prepareStatement("SELECT certificateID, certificateName FROM ToolCertificate");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
           certificateNames.add(new Certificate(
                   rs.getInt("certificateId"),
                   rs.getString("certificateName")));
        }
        rs.close();
        ps.close();
        db.close();
        return certificateNames;
    }

    private void addCertificate(String userID, String certificateID, LocalDate accomplishDate) throws SQLException {
        Connection db = DBUtils.getNoErrorConnection();
        String insertUserCommand = "insert into UsersCertificate (userID, certificateID, accomplishDate) values(?, ?, ?)";
        PreparedStatement statement = db.prepareStatement(insertUserCommand);
        statement.setString(1, userID);
        statement.setString(2, certificateID);
        statement.setObject(3, accomplishDate);
        statement.executeUpdate();

        statement.close();
        db.close();
    }



}




