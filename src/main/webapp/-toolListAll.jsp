<%@ page import="bacit.web.utils.DBUtils" %>
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
<h2>Categories</h2>
<section class='categories'>
    <%
        try {
            ResultSet rs1 = (ResultSet) request.getAttribute("toolCAT");
                while (rs1.next()) {
                    String category = rs1.getString("toolCategory");
                    // TODO: 30.10.2021 find out how to filter
                    out.print("<FORM action='xtc' method='get'");
                    out.print("<div class='category-item' style='background-image: url(img/amv.png);'>");
                    out.print("<div class='category-item-inner'>");
                    out.print("<button name='category' type='submit' value='"+ category +"'>"+ category.replaceAll("_"," ") +
                            "</button></div></div></FORM>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    %>
</section>

<h2>Tools</h2>
<section class='featured-products'>
    <%
        try {
            ResultSet rs2 = (ResultSet) request.getAttribute("toolALL");
            while (rs2.next()) {
                out.print("<FORM action='tooldetail' method='get'>");
                out.print("<div class='featured-product-item'>");
                out.print("<div class='featured-product-item-image' style='background-image: url(img/"+
                        rs2.getString("picturePath")
                        .replaceAll(" ","%20")
                        .replaceAll("æ","%C3%A6")
                        .replaceAll("ø","%C3%B8")
                        .replaceAll("å","%C3%A5") +
                        ");'>");
                out.print("</div>");
                out.print("<p class='title'>"+rs2.getString("toolName").replaceAll("_"," ")+"</p>");
                out.print("<button name='toolID' type='submit' value='"+rs2.getInt("toolID")+"'>");
                out.print("View item");
                out.print("</button></div></FORM>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    %>
</section>
</body>
</html>
