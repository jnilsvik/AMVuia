package bacit.web.UpAndDownLoadFile;


import bacit.web.Modules.FileModel;

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

@WebServlet(name = "fileUpload", value = "/fileUpload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 5, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5)
public class UpLoadServlet extends HttpServlet {

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
                    filePart.getContentType());

            FileDAO dao = new FileDAO();
            dao.persistFile(fileModel, out);

            out.println("Received file with name: "+fileModel.getName()+ "with the length of: "+fileModel.getContents().length+" bytes");
        }
        catch(Exception ex)
        {
            out.println(ex.getMessage());
            writeFileUploadForm(out, ex.getMessage());

        }
        printFooter(out);
    }

    private void writeFileUploadForm(PrintWriter out, String errorMessage) {

        if(errorMessage!=null)
        {
            out.println("<h3>"+errorMessage+"</h3>");
        }
        out.println("<form action='fileUpload' method='POST' enctype='multipart/form-data'>");
        out.println("<label for='file'>Upload a file</label> ");
        out.println("<input type='file' name='file'/>");
        out.println("<input type='submit' value='Upload file'/>");
        out.println("</form>");
    }

    private void printFooter(PrintWriter out){
        out.println("</body>");
        out.println("</html>");
    }

    private void printHeader(PrintWriter out){
        out.println("<html>");
        out.println("<head>");
        out.println("<title>FileUpload</title>");
        out.println("</head>");
        out.println("<body>");
    }
}

