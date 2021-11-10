package bacit.web.z_JSP_cleared.toolPages2;

import bacit.web.utils.DBUtils;

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

/*
by Joachim
prints all the tools NOW WITH IMAGES!
*/
@WebServlet(name = "tc", value = "/tc")
public class ToolCategory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Connection dbConnection = DBUtils.getNoErrorConnection(out);
        out.print("<!DOCTYPE html>");
        out.print("<head>");
        out.print("  <title>Toollist</title>"); // TODO: 09.11.2021 set the titel to a string or smth
        out.print("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />");
        out.print("  <meta charset=\"utf-8\" />");
        out.print("  <link rel=\"stylesheet\" href=\"css/list.css\">" );
        out.print("  <link rel=\"stylesheet\" href=\"css/style.css\">" );
        out.print("</head>");
        out.print("<body>");
        printTools(out,dbConnection,request);
    }

    void printTools(PrintWriter out,Connection dbConnection,HttpServletRequest category){
        out.print("<section class='featured-products'>");
        try {
            PreparedStatement ps = dbConnection.prepareStatement(
                "select * from Tool where toolCategory =?");
            ps.setString(1, String.valueOf(category.getParameter("toolCategory")));

            ResultSet rs2 = ps.executeQuery();
            // TODO: 30.10.2021 migth put this in dbq to simplyfy code, mby also make collection '
            //  - remove code dupe
            while (rs2.next()) {
                out.print("<FORM action='td' method='get'>");             //FORM open
                out.print("<div class='featured-product-item'>");         //div open
                out.print("    <div style='background-image: url(img/"+   //img open
                        rs2.getString("picturePath")                             //img path
                                .replaceAll(" ","%20")
                                .replaceAll("æ","%C3%A6")
                                .replaceAll("ø","%C3%B8")
                                .replaceAll("å","%C3%A5") +
                        ");' class='featured-product-item-image'>");         //img class specification
                out.print("    </div>");                                 //img close
                out.print("    <p class='title'>");                       //title open
                out.print(rs2.getString("toolName").replaceAll("_"," "));
                out.print("    </p>");                                    //title end
                out.print("    <button class='list-btn' type='submit' name='toolID' value='"+ rs2.getInt("toolID") +"'>");
                out.print("        View item");                           //button content
                out.print("    </button>");                               //button close
                out.print("</div></FORM>");                               //FORM close | div close
            }
            out.print("</table></section></section></body></html>");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}