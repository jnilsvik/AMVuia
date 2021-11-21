package bacit.web.AdminFunctions;

import bacit.web.Modules.Certificate;
import bacit.web.Modules.FileModel;
import bacit.web.utils.DBUtils;
import bacit.web.utils.FileDAO;

import java.nio.file.Paths;
import java.sql.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan changed by paul
@WebServlet(name = "RegisterTool", value = "/toolregister")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 5, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5)
public class RegisterTool extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

            if (AdminAccess.isAdmin(email)) {
                List<String> categories = getCategories();
                List<Certificate> certificates = getCertificates();
                request.setAttribute("categories", categories);
                request.setAttribute("certificates", certificates);
                request.getRequestDispatcher("/jspFiles/AdminFunctions/registerTool.jsp").forward(request,response);
            }else {
                request.getRequestDispatcher("/jspFiles/AdminFunctions/noAdminAccount.jsp").forward(request,response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try{
            int toolID = addTool(
                    request.getParameter("toolname"),
                    Integer.parseInt(request.getParameter("pricefirst")),
                    Integer.parseInt(request.getParameter("priceafter")),
                    request.getParameter("toolCategory"),
                    Integer.parseInt(request.getParameter("toolcertificate")),
                    request.getParameter("tooldesc")
            );

            try {
                addFile(request.getPart("file"), toolID);
            } catch (Exception e){}

            String successfulLine = "<h1>The tool has been registered successfully</h1>";
            request.setAttribute("successfulLine", successfulLine);
            request.getRequestDispatcher("/jspFiles/AdminFunctions/successfulLine.jsp").forward(request,response);
        }catch (Exception e){
            out.println(e.getMessage());
        }
    }

    private List<String> getCategories() throws SQLException {
        Connection db = DBUtils.getNoErrorConnection();
        PreparedStatement statement = db.prepareStatement("SELECT toolCategory FROM Tool GROUP BY toolCategory");
        ResultSet rs = statement.executeQuery();
        LinkedList<String> categories = new LinkedList<>();
        while(rs.next()){
            categories.add(rs.getString("toolCategory"));
        }
        rs.close();
        statement.close();
        db.close();
        return categories;
    }

    private List<Certificate> getCertificates() throws SQLException{
        Connection db = DBUtils.getNoErrorConnection();
        PreparedStatement statement = db.prepareStatement("SELECT certificateID, certificateName FROM ToolCertificate;");
        ResultSet rs = statement.executeQuery();
        LinkedList<Certificate> certificates = new LinkedList<>();
        while(rs.next()){
            certificates.add(new Certificate(
                    rs.getInt("certificateID"),
                    rs.getString("certificateName")
                    ));
        }
        rs.close();
        statement.close();
        db.close();
        return certificates;
    }

    private int addTool(String toolName,int priceFirst, int priceAfter, String toolCategory, int certificateID, String toolDescription) throws SQLException{
        int result = 0;
        Connection db = DBUtils.getNoErrorConnection();
        PreparedStatement statement = db.prepareStatement(
                "insert into Tool (toolName, maintenance, priceFirst, priceAfter, toolCategory, certificateID, toolDescription) values(?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, toolName);
        statement.setBoolean(2, false);
        statement.setInt(3, priceFirst);
        statement.setInt(4, priceAfter);
        statement.setString(5, toolCategory);
        statement.setInt(6, certificateID);
        statement.setString(7, toolDescription);
        statement.executeUpdate();
        statement.close();
        PreparedStatement statement1 = db.prepareStatement("SELECT toolID FROM Tool WHERE toolId = LAST_INSERT_ID();");
        ResultSet rs = statement1.executeQuery();
        if(rs.next()) result = rs.getInt("toolID");
        rs.close();
        statement1.close();
        db.close();
        return result;
    }

    private void addFile(Part filePart, int toolID) throws Exception {
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        InputStream fileContent = filePart.getInputStream();
        byte[] fileBytes = fileContent.readAllBytes();

        FileModel fileModel = new FileModel(
                fileName,
                fileBytes,
                filePart.getContentType(),
                toolID);

        FileDAO dao = new FileDAO();
        dao.persistFile(fileModel);
    }
}




