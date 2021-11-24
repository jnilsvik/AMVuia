<%@ page import="bacit.web.utils.DBUtils" %>
<%@ page import="java.sql.*" %>
<%@ page import="bacit.web.Modules.ToolModel" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Tool List</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta charset="utf-8" />
    <link rel="stylesheet" href="css/list.css">
</head>

<body>
<jsp:include page="../PageElements/header.jsp"/>
<h2>Categories</h2>
<section class='categories'>
    <%
        try {
            ArrayList<String> Categories = (ArrayList<String>) request.getAttribute("toolCAT");
                for (String c: Categories) {
                    // TODO: 30.10.2021 find out how to filter
                    out.print("<FORM action='xtc' method='get'");
                    out.print("<div class='category-item' style='background-image: url(img/amv.png);'>");
                    out.print("<div class='category-item-inner'>");
                    out.print("<button name='category' type='submit' value='"+ c +"'>"+ c.replaceAll("_"," ") +
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
        ArrayList<ToolModel> model = (ArrayList<ToolModel>) request.getAttribute("toolALL");
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
<jsp:include page="../PageElements/footer.jsp"/>
</body>
</html>
