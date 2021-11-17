<%--
  Created by IntelliJ IDEA.
  User: Joachim
  Date: 17.11.2021
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<jsp:include page="jspFiles/PageElements/header.jsp"/>
${feedback}
<%
    request.removeAttribute("feedback");
%>
<form>
    <input type="button" value="Go back!" onclick="history.back()">
</form>
</body>
</html>
