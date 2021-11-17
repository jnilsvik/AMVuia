<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 14.11.2021
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Help</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="css/list.css">
    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
</head>
<body>
<jsp:include page="../PageElements/header.jsp"/>
    <%
        String name = (String) request.getAttribute("name");
        String message = (String) request.getAttribute("name");
        String email = (String) request.getAttribute("name");
        out.print("<html>" +
                "<body>" +
                "<h1>" + name + "</h1>" +
                "<h4>" + message + "</h4>" +
                "<h4>" + email + "</h4>" +
                "</body>" +
                "</html>");
    %>
<jsp:include page="../PageElements/footer.jsp"/>
</body>
</html>
