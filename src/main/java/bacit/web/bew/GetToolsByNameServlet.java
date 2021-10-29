package bacit.web.bew;

import bacit.web.DBQ;
import bacit.web.bacit_database.DBUtils;
import bacit.web.bacit_models.ToolModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*  by Joachim (though its all copypaste pretty much :/ )

    returns a list of tools with a name that correlates with what the user specified in the calling method
*/
@WebServlet(name = "GetTool2", value = "/GetTool2")
public class GetToolsByNameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String toolName = request.getParameter("toolName");
        PrintWriter out = response.getWriter();
        try {
            DBQ.getToolModel(toolName, out);//prints the information!!!!
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
