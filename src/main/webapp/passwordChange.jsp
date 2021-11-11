<%--
  Created by IntelliJ IDEA.
  User: Joachim
  Date: 11.11.2021
  Time: 17:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change password</title>
</head>
<body>
<h2>Change Password</h2>
<form action='changepassword' method='POST'>
    <label for='oldpass'>Old password: </label><br>
    <input type='text' name='oldpass' id="oldpass" required><br>
    <label for='newpass1'>New password: </label><br>
    <input type='text' name='newpass1'id="newpass1" required><br>
    <label for='newpass2'>Repeat new password: </label><br>
    <input type='text' name='newpass2'id="newpass2" required><br>
    <input type='submit' value='Change Password'>
</form>
</body>
</html>

