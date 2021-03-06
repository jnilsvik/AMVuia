package bacit.web.Admin;

import bacit.web.Modules.CertificateModel;
import bacit.web.utils.DBUtils;
import bacit.web.utils.PageAccess;

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
        try {
            if (checkSession(request,response)) {
                List<CertificateModel> certificates = getCertificates();

                request.setAttribute("certificates", certificates);
                request.getRequestDispatcher("/jspFiles/AdminFunctions/giveCertificate.jsp").forward(request,response);
            } else {
                PageAccess.reDirWOAdmin(request,response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (checkSession(request,response)) {
            try {
                LocalDate accomplishDate = LocalDate.parse(request.getParameter("accomplishdate"));
                String userID = request.getParameter("userID");
                String certificateID = request.getParameter("certificateID");
                addCertificate(userID, certificateID, accomplishDate);

                PageAccess.reDirFeedback(request,response,"Certificate was given successfully!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            PageAccess.reDirWOAdmin(request,response);
        }
    }
    private List<CertificateModel> getCertificates() throws SQLException {
        List<CertificateModel> certificateNames = new LinkedList<>();
        Connection db = DBUtils.getNoErrorConnection();
        PreparedStatement ps = db.prepareStatement("SELECT certificateID, certificateName FROM ToolCertificate");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
           certificateNames.add(new CertificateModel(
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

    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (PageAccess.isAdmin(request)){
            return true;
        }
        PageAccess.reDirWOUser(request,response);
        PageAccess.reDirWOAdmin(request,response);
        return true;
    }
}




