<%@ page import="bacit.web.a_models.BookingModel" %>
<%@ page import="java.time.format.DateTimeFormatter" %><%--
  Created by IntelliJ IDEA.
  User: Joachim
  Date: 11.11.2021
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="__head_nav.html"/>
<%
    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    BookingModel bm = (BookingModel) request.getAttribute("bmodel");

    out.print("<h1> Tool has been booked. Here is your the order details:</h1>");
    out.print("<p>Tool: " + bm.getToolName() + "</p>");
    out.print("<p>Start Date: " + bm.getStartDate().format(formatters) + "</p>");
    out.print("<p>End Date: " + bm.getEndDate().format(formatters) + "</p>");
    out.print("<p>Total price: " + bm.getTotalPrice() + "</p>");

%>
</body>
</html>
