package bacit.web.toolPages2;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/*
by Joachim

prints all the tools NOW WITH IMAGES!
*/
@WebServlet(name = "tl", value = "/tl")
public class ToolAllListings extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(
                    "jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");
            printHead(out);
            printCategories(out,dbConnection);
            printTools(out,dbConnection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    void printHead(PrintWriter out){
        out.print("<!DOCTYPE html>");
        out.print("<head>");
        out.print("  <title>Toollist</title>"); // TODO: 09.11.2021 set the titel to a string or smth
        out.print("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />");
        out.print("  <meta charset=\"utf-8\" />");
        out.print("  <link rel=\"stylesheet\" href=\"CSS/list.css\">" );
        out.print("  <link rel=\"stylesheet\" href=\"CSS/style.css\">" );
        out.print("</head>");
        out.print("<body>");
    }
    void printCategories(PrintWriter out, Connection dbConnection){
        try {
            PreparedStatement ps = dbConnection.prepareStatement(
                    "SELECT toolCategory FROM Tool GROUP BY toolCategory");
            ResultSet rs1 = ps.executeQuery();

            out.print("<section class='categories' style='width: 70%'>");
            while (rs1.next()) {
                String category = rs1.getString("toolCategory");
                // TODO: 30.10.2021 find out how to filter 
                out.print("<FORM action='tc' method='get'");
                out.print("<div class='category-item' style='background-image: url(img/amv.png);'>");
                out.print("<div class='category-item-inner'>");
                out.print("<button class='list-btn' name='category' type='submit' value='"+ category +"'>"+ category.replaceAll("_"," ") +"</button></div></div></FORM>");
            }
            out.print("</section>");}
        catch (SQLException e) {
            e.printStackTrace();
            out.print("smth weith categories");
        }
    }
    void printTools(PrintWriter out,Connection dbConnection){
        out.print("<section class='featured-products' style='width: 70%'>");
        try {
            PreparedStatement ps = dbConnection.prepareStatement(
                    "select * from Tool order by toolID");
            ResultSet rs2 = ps.executeQuery();
            // TODO: 30.10.2021 migth put this in dbq to simplyfy code, mby also make collection 
            while (rs2.next()) {
                out.print("<FORM action='td' method='get'>");     //FORM open
                out.print("<div class='featured-product-item'>");         //div open
                out.print("    <div style='background-image: url(img/"+   //img open
                        rs2.getString("picturePath")                        //img path
                                .replaceAll(" ","%20")
                                .replaceAll("æ","%C3%A6")
                                .replaceAll("ø","%C3%B8")
                                .replaceAll("å","%C3%A5") +
                        ");' class='featured-product-item-image'>");
                out.print("    </div>");
                out.print("    <p class='title'>");
                out.print(rs2.getString("toolName").replaceAll("_"," "));
                out.print("    </p>");
                out.print("    <button class='list-btn' name='toolID' type='submit' value='"+rs2.getInt("toolID")+"'>");
                out.print("        View item");
                out.print("    </button>");
                out.print("</div></FORM>");
            }
            out.print("</table></section></section></body></html>");
        } catch (SQLException e) {
            e.printStackTrace();
            out.print("smth weith tools");
        }

    }
}