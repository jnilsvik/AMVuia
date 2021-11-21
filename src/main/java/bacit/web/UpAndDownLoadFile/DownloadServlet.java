package bacit.web.UpAndDownLoadFile;

import bacit.web.Modules.FileModel;
import bacit.web.utils.FileDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

@WebServlet(name = "fileDownload", value = "/fileDownload")
public class DownloadServlet extends HttpServlet {

    @SuppressWarnings("CanBeFinal")
    Logger logger = Logger.getLogger(String.valueOf(ToolPicture.class));
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        int id = 1;
        try{
            FileModel fileModel =  getFile(id);
            writeFileResult(response,fileModel);
        }
        catch(Exception ex)
        {
            logger.severe(ex.getMessage());
        }
    }

    protected FileModel getFile(int id) throws Exception
    {
        return new FileDAO().getFile(id);
    }

    protected void writeFileResult(HttpServletResponse response, FileModel model) throws IOException
    {
        response.setContentType(model.getContentType());
        response.setHeader("Content-Disposition", "attachment; filename="+model.getName());
        OutputStream outStream = response.getOutputStream();
        outStream.write(model.getContent());
    }
}
