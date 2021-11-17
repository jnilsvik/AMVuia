<%@ page import="java.sql.*" %><%--
  Created by IntelliJ IDEA.
  User: Joachim
  Date: 10.11.2021
  Time: 19:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Toollist</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta charset="utf-8" />
    <link rel="stylesheet" href="../../css/list.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="../PageElements/header.jsp"/>
<section class='featured-products'>
    <h1>${CAT}</h1>
    <%
        try {
            ResultSet rs2= (ResultSet) request.getAttribute("toolByCAT");
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
            out.print("</table></section></section></body></html>");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.removeAttribute("CAT");
    %>
</section>
</body>
</html>
