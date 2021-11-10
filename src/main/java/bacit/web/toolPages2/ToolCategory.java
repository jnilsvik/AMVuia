package bacit.web.toolPages2;

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
        out.println("<!DOCTYPE html>");
        out.println("<head>");
        out.println("  <title>Toollist</title>"); // TODO: 09.11.2021 set the titel to a string or smth
        out.println("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />");
        out.println("  <meta charset=\"utf-8\" />");
        out.println("  <link rel=\"stylesheet\" href=\"CSS/list.css\">" );
        out.println("  <link rel=\"stylesheet\" href=\"CSS/style.css\">" );
        out.println("</head>");
        out.println("<body>");
        printTools(out,dbConnection,request);
        out.println();
    }

    void printTools(PrintWriter out,Connection dbConnection,HttpServletRequest category){
        out.println("<section class='featured-products'>");
        try {
            PreparedStatement ps = dbConnection.prepareStatement(
                "select * from Tool where toolCategory =?");
            ps.setString(1, String.valueOf(category.getParameter("toolCategory")));

            ResultSet rs2 = ps.executeQuery();
            // TODO: 30.10.2021 migth put this in dbq to simplyfy code, mby also make collection '
            //  - remove code dupe
            while (rs2.next()) {
                out.println("<FORM action='td' method='get'>");             //FORM open
                out.println("<div class='featured-product-item'>");         //div open
                out.println("    <div style='background-image: url(img/"+   //img open
                        rs2.getString("picturePath")                             //img path
                                .replaceAll(" ","%20")
                                .replaceAll("æ","%C3%A6")
                                .replaceAll("ø","%C3%B8")
                                .replaceAll("å","%C3%A5") +
                        ");' class='featured-product-item-image'>");         //img class specification
                out.println("    </div>");                                 //img close
                out.println("    <p class='title'>");                       //title open
                out.println(rs2.getString("toolName").replaceAll("_"," "));
                out.println("    </p>");                                    //title end
                out.println("    <button class='list-btn' type='submit' name='toolID' value='"+ rs2.getInt("toolID") +"'>");
                out.println("        View item");                           //button content
                out.println("    </button>");                               //button close
                out.println("</div></FORM>");                               //FORM close | div close
            }
            out.println("</table></section></section></body></html>");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}