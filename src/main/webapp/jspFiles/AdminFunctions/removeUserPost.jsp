<%@ page import="bacit.web.Modules.UserModel" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 14.11.2021
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="css/list.css">
    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
</head>
<body>
<jsp:include page="../PageElements/header.jsp"/>
    <%
        List<UserModel> users = (List<UserModel>) request.getAttribute("users");
        boolean success = (boolean) request.getAttribute("success");
        if(!success){
            out.println("<h1>No User found with this email. No user could be deleted</h1>");
        } else {
            out.println("<h1>User got deleted</h1>");
            out.println("<table>");
            for(UserModel user : users){
                out.println("<tr>" +
                        "<td>" + user.getEmail() + "</td>" +
                        "</tr>");
            }
            out.println("</table>");
        }
    %>
<jsp:include page="../PageElements/footer.jsp"/>
</body>
</html>
