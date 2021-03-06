<%@ page import="bacit.web.Modules.UserModel" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 14.11.2021
  Time: 13:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Remove User</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="css/list.css">
    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
    <style> table, th, td {border: 1px solid black;} th, td {padding: 0 5px;}</style>
</head>
<body>
<jsp:include page="../PageElements/header.jsp"/>
    <h2>Please enter the email of the user you want to remove</h2>
    <form method='POST'>
        <label for="input">Email: </label>
        <textarea id='input' name='input' rows='1' cols='50'></textarea><br><br>
        <input type = 'submit' value = 'Remove!'>
    </form>

    <table>
        <%
            List<UserModel> users = (List<UserModel>) request.getAttribute("users");
            for(UserModel user : users){
                out.print("<tr>" +
                        "<td>" + user.getEmail() + "</td>" +
                        "</tr>");
            }
        %>
    </table>
<jsp:include page="../PageElements/footer.jsp"/>
</body>
</html>
