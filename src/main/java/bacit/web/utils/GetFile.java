package bacit.web.utils;

import bacit.web.Modules.FileModel;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GetFile", value = "/getFile")
public class GetFile extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/gif");
        int picID = Integer.parseInt(request.getParameter("id"));
        try {
            FileModel file = new FileDAO().getFile(picID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
