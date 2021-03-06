<%@ page import="bacit.web.Modules.ToolModel" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Tool List</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta charset="utf-8" />
    <link rel="stylesheet" href="css/list.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="../PageElements/header.jsp"/>
<h1>${CAT}</h1>
<section class='featured-products'>
    <%
        ArrayList<ToolModel> model = (ArrayList<ToolModel>) request.getAttribute("toolALL1");
        if(model != null) {
            for (ToolModel tm : model){
                out.print("<FORM action='tooldetail' method='get'>");
                out.print("<div class='featured-product-item'>");
                out.print("<div class='featured-product-item-image' style='background-image: url(img/"+
                        tm.getPicturePath()
                                .replaceAll(" ","%20")
                                .replaceAll("æ","%C3%A6")
                                .replaceAll("ø","%C3%B8")
                                .replaceAll("å","%C3%A5") +
                        ");'></div>");
                out.print("<p class='title'>"+tm.getToolName().replaceAll("_"," ")+"</p>");
                out.print("<button name='toolID' type='submit' value='"+tm.getToolID()+"'>");
                out.print("View item");
                out.print("</button></div></FORM>");
            }
        }
    %>
</section>
</body>
</html>
