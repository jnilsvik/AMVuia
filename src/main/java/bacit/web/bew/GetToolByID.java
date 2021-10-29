package bacit.web.bew;

import bacit.web.utils.DBQ;
import bacit.web.models.ToolModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*  by Joachim (though its all copypaste pretty much :/ )

    gets a tool id from landing page and prints all information about it
*/
@WebServlet(name = "GetTool", value = "/GetTool")
public class GetToolByID extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String toolID = request.getParameter("toolID");
        PrintWriter out = response.getWriter();

        try {
            ToolModel tool = DBQ.getToolModelByID(toolID, out);//prints the information!!!!
            out.println(tool.getToolName());
            out.println(tool.getToolCategory());
            out.println(tool.getPriceFirst());
            out.println(tool.getPriceAfter());
            out.println(tool.getMaintenance());

            //
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
