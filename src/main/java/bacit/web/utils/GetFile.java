package bacit.web.utils;

import bacit.web.Modules.FileModel;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

@WebServlet(name = "GetFile", value = "/getFile")
public class GetFile extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/gif");
        //int picID = Integer.parseInt(request.getParameter("id"));
        try {
            Blob file = new FileDAO().getBlob(20);
            String imgString = transferToString(file);
            PrintWriter out = response.getWriter();
            out.println(imgString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String transferToString(Blob file) throws SQLException, IOException {
        InputStream inputStream = file.getBinaryStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        byte[] imageBytes = outputStream.toByteArray();
        String image = Base64.getEncoder().encodeToString(imageBytes);
        return image;
    }
}
