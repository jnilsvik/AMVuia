<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11.11.2021
  Time: 12:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Booking Cancellation</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="CSS/list.css">
    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
</head>
<body>
    <jsp:include page="__head_nav.html"></jsp:include>
    <%
       String result = (String)request.getAttribute("result");
       out.println("<h2>"+result+"</h2>");
    %>
</body>
</html>