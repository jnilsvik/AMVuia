package bacit.web.AdminFunctions;


import bacit.web.Modules.FileModel;
import bacit.web.utils.FileDAO;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;

//by Paul
@WebServlet(name = "fileUpload", value = "/toolPicture")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 5, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5)
public class ToolPicture extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        printHeader(out);
        writeFileUploadForm(out,null);
        printFooter(out);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        printHeader(out);
        try{
            Part filePart = request.getPart("file");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            InputStream fileContent = filePart.getInputStream();
            byte[] fileBytes = fileContent.readAllBytes();

            FileModel fileModel = new FileModel(
                    fileName,
                    fileBytes,
                    filePart.getContentType(),2);

            FileDAO dao = new FileDAO();
            dao.persistFile(fileModel);

            String test = request.getParameter("test");
            out.print("Received file with name: "+fileModel.getName()+ "with the length of: "+fileModel.getContent().length+" bytes"+test);
        }
        catch(Exception ex)
        {
            out.print(ex.getMessage());
            writeFileUploadForm(out, ex.getMessage());

        }
        printFooter(out);
    }

    private void writeFileUploadForm(PrintWriter out, String errorMessage) {

        if(errorMessage!=null)
        {
            out.print("<h3>"+errorMessage+"</h3>");
        }
        out.print("<form action='toolPicture' method='POST' enctype='multipart/form-data'>");
        out.print("<label for='file'>Upload a file</label> ");
        out.print("<input type='file' name='file'/>");
        out.print("<label for='test'>test</label>");
        out.print("<input type='text' name='test'/>");
        out.print("<input type='submit' value='Upload file'/>");
        out.print("</form>");
    }

    private void printFooter(PrintWriter out){
        out.print("</body>");
        out.print("</html>");
    }

    private void printHeader(PrintWriter out){
        out.print("<html>");
        out.print("<head>");
        out.print("<title>FileUpload</title>");
        out.print("</head>");
        out.print("<body>");
    }
}

