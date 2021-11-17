<%@ page import="bacit.web.Modules.BookingModel" %>
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
<jsp:include page="_head_nav.jsp"/>
<%
    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    BookingModel booking = (BookingModel) request.getAttribute("booking");

    out.print("<h1> Tool has been booked. Here are your the order details: </h1>");
    out.print("<p>Tool: " + booking.getToolName() + "</p>");
    out.print("<p>Start Date: " + booking.getStartDate().format(formatters) + "</p>");
    out.print("<p>End Date: " + booking.getEndDate().format(formatters) + "</p>");
    out.print("<p>Total price: " + booking.getTotalPrice() + "</p>");

%>
</body>
</html>
