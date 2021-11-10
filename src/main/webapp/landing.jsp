<%--
  Created by IntelliJ IDEA.
  User: Joachim
  Date: 10.11.2021
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="__head_nav.html"/>
<!--content goes here-->
<section class="main">
    <h1>Hello ${email}</h1>
    <h1>Some stuff is missing here :)</h1>
    <br>
    <a href="login">Login </a>
    <br>
    <a href="register">Register</a>
    <br>
    <a href="toolcategories">tool display 1</a>
    <br>
    <a href="tl">tool display 2</a>
    <br>
    <a href="lt">tool list</a>
    <br>
    <a href="lu">user list</a>
    <br>
</section>
</body>
</html>
