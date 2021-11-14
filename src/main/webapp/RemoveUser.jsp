<%@ page import="bacit.web.a_models.UserModel" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 14.11.2021
  Time: 13:01
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
    <jsp:include page="_head_nav.jsp"/>
    <h2>Please enter the email of the user you want to remove</h2>
    <form method='POST'>
    <h3>Email:</h3>
    <textarea id='input' name='input' rows='1' cols='50'></textarea><br><br>
    <input type = 'submit' value = 'Remove!'>
    </form>

    <table>
        <%
            List<UserModel> users = (List<UserModel>) request.getAttribute("users");
            for(UserModel user : users){
                out.println("<tr>" +
                        "<td>" + user.getEmail() + "</td>" +
                        "</tr>");
            }
        %>
    </table>
</body>
</html>
