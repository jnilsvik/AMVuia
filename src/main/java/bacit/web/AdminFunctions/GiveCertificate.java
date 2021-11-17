package bacit.web.AdminFunctions;

import bacit.web.Modules.Certificate;
import bacit.web.utils.DBUtils;

import java.io.PrintWriter;
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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession(false);
            if(session == null){
                response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
                return;
            }
            String email = (String) session.getAttribute("email");

            if (AdminAccess.accessRights(email)) {
                List<Certificate> certificates = getCertificates();

                request.setAttribute("certificates", certificates);
                request.getRequestDispatcher("/jspFiles/AdminFunctions/giveCertificate.jsp").forward(request,response);
            } else {
                request.getRequestDispatcher("/jspFiles/AdminFunctions/noAdminAccount.jsp").forward(request,response);
            }

        } catch (Exception e) {
            out.println(e);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if(session == null){
            response.sendRedirect("/bacit-web-1.0-SNAPSHOT/login");
            return;
        }
        String email = (String) session.getAttribute("email");

        if (AdminAccess.accessRights(email)) {
            try {
                LocalDate accomplishDate = LocalDate.parse(request.getParameter("accomplishdate"));
                String userID = request.getParameter("userID");
                String certificateID = request.getParameter("certificateID");
                addCertificate(userID, certificateID, accomplishDate);

                String successfulLine = "Certificate was successfully given!";
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
        PreparedStatement ps = db.prepareStatement("SELECT * FROM ToolCertificate");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
           certificateNames.add(new Certificate(
                   rs.getInt("certificateId"),
                   rs.getString("certificateName")));
        }
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
    }



}




