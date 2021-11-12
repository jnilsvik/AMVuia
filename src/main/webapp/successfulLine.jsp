<%@ page import="java.util.ArrayList" %>
<%@ page import="bacit.web.a_models.ToolModel" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: Dilan
  Date: 11.11.2021
  Time: 11:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Tool List</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta charset="utf-8" />
    <link rel="stylesheet" href="css/tabelsort.css">
</head>
<body>
<jsp:include page="_head_nav.jsp"/>

<html>
<head>
    <title>Tool maintenance</title>
</head>
<style>
    table, th, td { border:1px solid black;}
</style>
<body>
<%
        String successfulLine = (String) request.getAttribute("successfulLine");
        out.print(successfulLine);
        request.removeAttribute("successfulLine");
%>
</body>
</html>

</body>
</html>
