<%@ page import="java.sql.*" %><%--
  Created by IntelliJ IDEA.
  User: Joachim
  Date: 10.11.2021
  Time: 19:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Toollist</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta charset="utf-8" />
    <link rel="stylesheet" href="css/list.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<section class='featured-products'>
    <h1>${CAT}</h1>
    <%
        try {
            ResultSet rs2= (ResultSet) request.getAttribute("toolByCAT");
            while (rs2.next()) {
                out.print("<FORM action='xtd' method='get'>");             //FORM open
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
                out.print("    <button name='toolID' type='submit' value='"+rs2.getInt("toolID")+"'>");
                out.print("        View item");
                out.print("    </button>");
                out.print("</div></FORM>");
            }
            out.print("</table></section></section></body></html>");
        } catch (SQLException e) {
            e.printStackTrace();
            out.print("smth weith tools");
        }
        request.removeAttribute("CAT");

    %>
</section>
</body>
</html>
