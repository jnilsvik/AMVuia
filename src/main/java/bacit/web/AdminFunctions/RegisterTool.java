package bacit.web.AdminFunctions;

import bacit.web.Modules.Certificate;
import bacit.web.Modules.FileModel;
import bacit.web.Modules.ToolModel;
import bacit.web.utils.DBUtils;
import bacit.web.utils.FileDAO;
import bacit.web.utils.PageAccess;

import java.nio.file.Paths;
import java.sql.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// by Dilan changed by paul
@WebServlet(name = "RegisterTool", value = "/toolregister")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 5, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5)
public class RegisterTool extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            if (checkSession(request,response)) {
                List<String> categories = getCategories();
                List<Certificate> certificates = getCertificates();
                printJspGet(request, response, categories, certificates);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            /*
            int toolID = addTool(getToolFromRequest(request));
            try {
                addFile(request.getPart("file"), toolID);
            } catch (Exception e){}*/

            printJspPost(request, response);
        }catch (Exception e){
            printJspError(request, response);
        }
    }

    protected List<String> getCategories() throws SQLException {
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

    protected List<Certificate> getCertificates() throws SQLException{
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

    protected int addTool(ToolModel tool) throws SQLException{
        int result = 0;
        Connection db = DBUtils.getNoErrorConnection();
        PreparedStatement statement = db.prepareStatement(
                "insert into Tool (toolName, maintenance, priceFirst, priceAfter, toolCategory, certificateID, toolDescription) values(?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, tool.getToolName());
        statement.setBoolean(2, false);
        statement.setInt(3, tool.getPriceFirst());
        statement.setInt(4, tool.getPriceAfter());
        statement.setString(5, tool.getToolCategory());
        statement.setInt(6, tool.getCertificateID());
        statement.setString(7, tool.getDescription());
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

    protected void addFile(Part filePart, int toolID) throws Exception {
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

    protected void printJspGet(HttpServletRequest request, HttpServletResponse response, List<String> categories, List<Certificate> certificates) throws ServletException, IOException {
        request.setAttribute("categories", categories);
        request.setAttribute("certificates", certificates);
        request.getRequestDispatcher("/jspFiles/AdminFunctions/registerTool.jsp").forward(request,response);
    }

    protected ToolModel getToolFromRequest(HttpServletRequest request){
        return new ToolModel(
                0,
                request.getParameter("toolname"),
                request.getParameter("toolCategory"),
                false,
                Integer.parseInt(request.getParameter("pricefirst")),
                Integer.parseInt(request.getParameter("priceafter")),
                Integer.parseInt(request.getParameter("toolcertificate")),
                request.getParameter("tooldesc"),
                "");
    }

    protected void printJspPost(HttpServletRequest request, HttpServletResponse response){
        PageAccess.reDirFeedback(request,response, "The tool has been registered successfully");
    }

    protected void printJspError(HttpServletRequest request, HttpServletResponse response){
        PageAccess.reDirFeedback(request, response, "Data could not be added to the database");
    }

    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (PageAccess.isAdmin(request)){
            return true;
        }
        PageAccess.reDirWOUser(request,response);
        PageAccess.reDirWOAdmin(request,response);
        return false;
    }
}




