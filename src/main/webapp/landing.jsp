<%--
  Created by IntelliJ IDEA.
  User: Joachim
  Date: 10.11.2021
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"  %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="_head_nav.jsp"/>
<!--content goes here-->
<section class="main">
    <h1>Hello ${email}</h1>
    <h1>Select any of the temp links</h1>
    <br>
    <a href="fileDownload">Download a file</a>
    <br>
    <a href="fileUpload">Upload a file</a>
    <br>
    <a href="xtl">List of Equipment</a>
    <br>
    <a href="lt">List of Equipment (Admin Table)</a>
    <br>
    <a href="toolregister">Add equipment to list</a>
    <br>
</section>
</body>
</html>
